package com.gsquad.lunih;

import com.gsquad.lunih.dtos.accountDTO.StudentAccountDTO;
import com.gsquad.lunih.dtos.student.ApproveStudentDTO;
import com.gsquad.lunih.repos.AccountRepo;
import com.gsquad.lunih.services.account.AccountService;
import com.gsquad.lunih.services.student.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Date;

@SpringBootApplication
@EnableAsync
public class LunihApplication implements CommandLineRunner {

    private final AccountRepo accountRepo;

    private final AccountService accountService;

    private final StudentService studentService;

    public LunihApplication(AccountRepo accountRepo, AccountService accountService, StudentService studentService) {
        this.accountRepo = accountRepo;
        this.accountService = accountService;
        this.studentService = studentService;
    }

    public static void main(String[] args) {
        SpringApplication.run(LunihApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initData();
    }

    private void initData() {
        if (accountRepo.count() == 0) {
            Date bday = new Date();
            StudentAccountDTO dto = new StudentAccountDTO();
            dto.setEmail("thienquoc98@gmail.com");
            dto.setPassword("123456");
            dto.setStudentID("22021P");
            dto.setFirstName("Thien Quoc");
            dto.setSurName("Nguyen");
            dto.setGender(false);
            dto.setBirthDay(bday);
            dto.setPhoneNumber("+37199999999");
            accountService.createNewStudent(dto);
            ApproveStudentDTO approveStudentDTO = new ApproveStudentDTO(true, "");
            studentService.approveStudent("22021P", approveStudentDTO);

            dto.setEmail("test@gmail.com");
            dto.setPassword("123456");
            dto.setStudentID("22022P");
            dto.setFirstName("Test Quoc");
            dto.setSurName("Nguyen");
            dto.setGender(true);
            dto.setBirthDay(bday);
            dto.setPhoneNumber("+37199999999");
            accountService.createNewStudent(dto);
        }
    }
}
