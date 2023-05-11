package com.gsquad.lunih.securities;

import com.gsquad.lunih.entities.Account;
import com.gsquad.lunih.entities.Company;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.entities.University;
import com.gsquad.lunih.repos.AccountRepo;
import com.gsquad.lunih.repos.CompanyRepo;
import com.gsquad.lunih.repos.StudentRepo;
import com.gsquad.lunih.repos.UniversityRepo;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class AccountDetailsService implements UserDetailsService {

    private final MessageSource messageSource;
    private final AccountRepo accountRepo;
    private final StudentRepo studentRepo;
    private final CompanyRepo companyRepo;
    private final UniversityRepo universityRepo;

    public AccountDetailsService(MessageSource messageSource, AccountRepo accountRepo, StudentRepo studentRepo, CompanyRepo companyRepo, UniversityRepo universityRepo) {
        this.messageSource = messageSource;
        this.accountRepo = accountRepo;
        this.studentRepo = studentRepo;
        this.companyRepo = companyRepo;
        this.universityRepo = universityRepo;
    }

    @Override
    public JwtUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();

        Account account = accountRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(messageSource.getMessage("error.account.emailuser-notfound", null, locale), email)));

        return getUserDetails(account, locale);
    }

    private JwtUserDetails getUserDetails(Account account, Locale locale) {
        List<String> roles = new ArrayList<>();
        roles.add(account.getRole());

        switch (account.getRole()) {
            case "ROLE_ADMIN": {

                return new JwtUserDetails(
                        account.getEmail(),
                        account.getEmail(),
                        account.getPassword(),
                        roles.stream().map(
                                SimpleGrantedAuthority::new).collect(Collectors.toList()),
                        account.getStatus()
                );
            }

            case "ROLE_STUDENT": {
                Student student = studentRepo.findByAccount_Id(account.getId()).orElseThrow(() -> new UsernameNotFoundException(String.format(messageSource.getMessage("error.account.emailuser-notfound", null, locale), account.getEmail())));
                String fullName = student.getFirstName() + " " + student.getSurName();

                return new JwtUserDetails(
                        fullName,
                        account.getEmail(),
                        account.getPassword(),
                        roles.stream().map(
                                SimpleGrantedAuthority::new).collect(Collectors.toList()),
                        account.getStatus()
                );
            }

            case "ROLE_UNIVERSITY": {
                University university = universityRepo.findByAccount_Id(account.getId()).orElseThrow(() -> new UsernameNotFoundException(String.format(messageSource.getMessage("error.account.emailuser-notfound", null, locale), account.getEmail())));

                return new JwtUserDetails(
                        university.getName(),
                        account.getEmail(),
                        account.getPassword(),
                        roles.stream().map(
                                SimpleGrantedAuthority::new).collect(Collectors.toList()),
                        account.getStatus()
                );
            }

            case "ROLE_COMPANY": {
                Company company = companyRepo.findByAccount_Id(account.getId()).orElseThrow(() -> new UsernameNotFoundException(String.format(messageSource.getMessage("error.account.emailuser-notfound", null, locale), account.getEmail())));

                return new JwtUserDetails(
                        company.getCompanyName(),
                        account.getEmail(),
                        account.getPassword(),
                        roles.stream().map(
                                SimpleGrantedAuthority::new).collect(Collectors.toList()),
                        account.getStatus()
                );
            }
        }

        return null;
    }
}
