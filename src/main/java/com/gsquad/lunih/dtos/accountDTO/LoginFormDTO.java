package com.gsquad.lunih.dtos.accountDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginFormDTO {

    private String email;

    private String password;
}
