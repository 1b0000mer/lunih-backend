package com.gsquad.lunih.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Deliverable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiModelProperty(name = "name of Deliverable in English")
    @Column(nullable = false)
    private String nameEn;

    @ApiModelProperty(name = "name of Deliverable in Latvian")
    @Column(nullable = false)
    private String nameLv;

    @ApiModelProperty(value = "Id file")
    private String fileAttachment;

    @ApiModelProperty(value = "Set a deadline for deliverable, can be null")
    private Date deadLine;

    @JsonIgnore
    @CreatedDate
    private Date createdDate;

    @JsonIgnore
    @CreatedBy
    private String createdBy;

    @ApiModelProperty(value = "available/submitted/overdue")
    private String status;

//    @LastModifiedDate
//    @LastModifiedBy
}
