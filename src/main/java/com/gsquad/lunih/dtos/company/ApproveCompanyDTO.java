package com.gsquad.lunih.dtos.company;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveCompanyDTO {

    private Boolean isApproved = null;

    private String reason;

    public ApproveCompanyDTO(Boolean isApproved, String reason) {
        this.isApproved = isApproved;
        this.reason = reason;
    }
}
