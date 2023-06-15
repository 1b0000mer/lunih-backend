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
import java.util.Objects;

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

    @ApiModelProperty(value = "id file for approve")
    private int fileCertification;

    @ApiModelProperty(value = "approved by admin/university?")
    private Boolean approved = null;

    @ManyToOne
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentID.equals(student.studentID) && Objects.equals(account, student.account) && firstName.equals(student.firstName) && surName.equals(student.surName) && Objects.equals(birthDay, student.birthDay) && Objects.equals(gender, student.gender) && Objects.equals(phoneNumber, student.phoneNumber) && Objects.equals(approved, student.approved) && Objects.equals(program, student.program) && Objects.equals(reason, student.reason) && Objects.equals(createdDate, student.createdDate) && Objects.equals(createdBy, student.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentID, account, firstName, surName, birthDay, gender, phoneNumber, approved, program, reason, createdDate, createdBy);
    }
}
