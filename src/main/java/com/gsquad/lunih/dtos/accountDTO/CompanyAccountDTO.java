package com.gsquad.lunih.dtos.accountDTO;

import com.gsquad.lunih.entities.categories.Industry;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
public class CompanyAccountDTO {
    private String email;

    private String password;

    private String companyName;

    private String companyDescription;

    @ApiModelProperty(value = "get from enumCompanyType")
    private String companyType;

    private List<Integer> industryList;

    private String companyAddress;

    private String companyWebsite;

    @ApiModelProperty(value = "id image file")
    private int companyLogo;

    @ApiModelProperty(value = "mr/mrs/ms...")
    private String companyContactPersonTitle;

    private String companyContactPersonName;

    private String companyContactPersonDepartment;

    @ApiModelProperty(value = "leave empty if same as registered email")
    private String companyContactPersonEmail;

    private String companyContactPersonPhoneNumber;

}
