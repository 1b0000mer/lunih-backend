package com.gsquad.lunih.dtos.accountDTO;

import com.gsquad.lunih.entities.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyAccountDTO {
    private String email;

    private String password;

    private String companyName;

    private String companyPersonName;

    private String address;

    private String phoneNumber;

}
