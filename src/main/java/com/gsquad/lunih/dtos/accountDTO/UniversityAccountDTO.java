package com.gsquad.lunih.dtos.accountDTO;

import com.gsquad.lunih.entities.categories.Program;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UniversityAccountDTO {
    private String email;

    private String password;

    @ApiModelProperty(value = "full name")
    private String name;

    private String address;

    private String phoneNumber;

    private List<Integer> programList;

}
