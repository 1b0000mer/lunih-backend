package com.gsquad.lunih.dtos.student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveStudentDTO {

    private Boolean isApproved = null;

    private String reason;

    public ApproveStudentDTO(Boolean isApproved, String reason) {
        this.isApproved = isApproved;
        this.reason = reason;
    }

}
