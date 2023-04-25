package com.gsquad.lunih.dtos.accountDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDTO {
    private String oldPass;

    private String newPass;
}
