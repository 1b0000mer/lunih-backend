package com.gsquad.lunih.controllers;

import com.gsquad.lunih.dtos.accountDTO.LoginFormDTO;
import com.gsquad.lunih.dtos.accountDTO.TokenDetails;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.UserNotFoundAuthenticationException;
import com.gsquad.lunih.securities.AccountDetailsService;
import com.gsquad.lunih.securities.JwtTokenUtils;
import com.gsquad.lunih.securities.JwtUserDetails;
import com.gsquad.lunih.securities.providers.AccountAuthenticationToken;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Locale;

@RestController
@RequestMapping("/rest/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountDetailsService accountDetailsService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private MessageSource messageSource;

    @Value("${google.verifyUrl}")
    private String googleVerifyUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @ApiOperation(value = "User login by form (email, password), avatar null")
    @PostMapping
    public ResponseEntity<TokenDetails> loginForm(@Valid @RequestBody LoginFormDTO dto) {
        AccountAuthenticationToken authenticationToken = new AccountAuthenticationToken(
                dto.getEmail(),
                dto.getPassword(),
                true
        );
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (UserNotFoundAuthenticationException | BadCredentialsException e) {
            throw new InvalidException(e.getMessage());
        }
        final JwtUserDetails userDetails = accountDetailsService.loadUserByUsername(dto.getEmail());
        final TokenDetails result = jwtTokenUtils.getTokenDetails(userDetails, null);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "User login by google (Token Id), avatar from google")
    @PostMapping("/google")
    public ResponseEntity<TokenDetails> loginGoogle(@RequestHeader(name = "idToken") String googleToken) {
        Locale locale = LocaleContextHolder.getLocale();

        String urlRequest = googleVerifyUrl + googleToken;
        String email;
        String avatar;
        try {
            ResponseEntity<HashMap> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, null, HashMap.class);
            HashMap<String, String> map = responseEntity.getBody();
            email = map.get("email");
            avatar = map.get("picture");
        } catch (Exception e) {
            throw new InvalidException(messageSource.getMessage("error.token-invalid", null, locale));
        }

        AccountAuthenticationToken authenticationToken = new AccountAuthenticationToken(
                email,
                null,
                false
        );
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (UserNotFoundAuthenticationException | BadCredentialsException e) {
            throw new InvalidException(e.getMessage());
        }
        final JwtUserDetails userDetails = accountDetailsService.loadUserByUsername(email);
        final TokenDetails result = jwtTokenUtils.getTokenDetails(userDetails, avatar);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
