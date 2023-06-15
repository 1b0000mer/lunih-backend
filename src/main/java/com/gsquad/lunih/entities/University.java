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
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private Account account;

    @Column(nullable = false)
    @ApiModelProperty(value = "full name")
    private String name;

    private String address;

    private String phoneNumber;

    @ManyToMany
    @ApiModelProperty(value = "list of program")
    private List<Program> programList;

//    @ApiModelProperty(value = "id file for approve")
//    private String fileCertification;
//    @ApiModelProperty(value = "approved by admin/university?")
//    private Boolean approved = null;
//    private String reason;

    @JsonIgnore
    @CreatedDate
    private Date createdDate;

    @JsonIgnore
    @CreatedBy
    private String createdBy;
}
