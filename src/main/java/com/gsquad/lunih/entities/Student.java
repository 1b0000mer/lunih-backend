package com.gsquad.lunih.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {

    @Id
    @Column(length = 50)
    private String studentID;

    @OneToOne(
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private Account account;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String surName;

    private Date birthDay;

    @ApiModelProperty(value = "female : 1, male: 0")
    private Boolean gender;

    @Column(length = 18)
    private String phoneNumber;

    @ApiModelProperty(value = "approved by admin/university?")
    private Boolean approved = null;

    @Column(length = 100)
    @JsonIgnore
    private String reason;

//    @OneToMany
//    private List<Post> appliedPost = new ArrayList<>();

    @JsonIgnore
    @CreatedDate
    private Date createdDate;

    @JsonIgnore
    @CreatedBy
    private String createdBy;
}
