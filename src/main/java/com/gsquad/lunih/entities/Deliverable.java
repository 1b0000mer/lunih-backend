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

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Deliverable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 80)
    private String name;

    @ApiModelProperty(value = "Id file")
    private String fileAttachment;

    @JsonIgnore
    @CreatedDate
    private Date createdDate;

    @JsonIgnore
    @CreatedBy
    private String createdBy;

//    @LastModifiedDate
//    @LastModifiedBy
}
