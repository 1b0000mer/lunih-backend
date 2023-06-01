package com.gsquad.lunih.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsquad.lunih.entities.categories.Program;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Student {

    @Id
    @Column(length = 50)
    private String studentID;

    @OneToOne(
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private Account account;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String surName;

    private Date birthDay;

//    @ApiModelProperty(value = "female : 1, male: 0")
    private String gender;

    private String phoneNumber;

    @ApiModelProperty(value = "approved by admin/university?")
    private Boolean approved = null;

    @OneToOne
    private Program program;

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
