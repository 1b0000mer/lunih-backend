package com.gsquad.lunih.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsquad.lunih.entities.categories.Industry;
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
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private Account account;

    @Column(nullable = false)
    private String companyName;

    @Column(length = 500)
    private String companyDescription;

    @ApiModelProperty(value = "get from enumCompanyType")
    private String companyType;

    @OneToMany
    private List<Industry> industryList;

    private String companyAddress;

    private String companyWebsite;

    @ApiModelProperty(value = "id image file")
    private String companyLogo;

//    @ApiModelProperty(value = "id file for approve")
//    private String fileCertification;
//    @ApiModelProperty(value = "approved by admin/university?")
//    private Boolean approved = null;
//    private String reason;

    @Column(nullable = false)
    private String companyContactPersonName;

    @ApiModelProperty(value = "position")
    private String companyContactPersonTitle;

    @ApiModelProperty(value = "leave empty if same as registered email")
    private String companyContactPersonEmail;

    private String companyContactPersonPhoneNumber;

    @JsonIgnore
    @CreatedDate
    private Date createdDate;

    @JsonIgnore
    @CreatedBy
    private String createdBy;

    @ApiModelProperty(value = "approved by admin/university?")
    private Boolean approved = null;

    @Column(length = 100)
    @JsonIgnore
    private String reason;
}
