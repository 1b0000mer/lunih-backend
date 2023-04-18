package com.gsquad.lunih.dtos;

import com.gsquad.lunih.entities.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentAccountDTO {
    private String email;

    private String password;

    private int role_id;

    private String studentID;

    private String firstName;

    private String surName;
}
