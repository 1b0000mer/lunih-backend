package com.gsquad.lunih.dtos.accountDTO;

import com.gsquad.lunih.entities.Account;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StudentAccountDTO {
    private String email;

    private String password;

    private String studentID;

    private String firstName;

    private String surName;

    private int program = -1;
    
    private Date birthDay;

    private String gender;

    private String phoneNumber;
}
