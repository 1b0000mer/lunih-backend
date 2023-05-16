package com.gsquad.lunih.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class DeliverableDTO {
    private String nameEn;
    private String nameLv;
    private String fileAttachment;
    private Date createdDate;
    private String createdBy;
}
