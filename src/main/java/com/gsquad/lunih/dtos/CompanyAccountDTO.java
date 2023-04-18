package com.gsquad.lunih.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyAccountDTO {
    private String email;

    private String password;

    private int role_id;

    private String name;

    private String address;

    private String phoneNumber;

    private int account_id;
}
