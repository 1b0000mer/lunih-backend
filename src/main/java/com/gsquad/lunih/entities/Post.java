package com.gsquad.lunih.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsquad.lunih.entities.categories.PostType;
import com.gsquad.lunih.entities.categories.Industry;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private PostType postType;

    @Column(nullable = false)
    private String titleEn;

    @Column(nullable = false)
    private String titleLv;

    @Column(nullable = false, length = 500)
    private String descriptionEn;

    @Column(nullable = false, length = 500)
    private String descriptionLv;

    @ManyToMany
    private List<Industry> industryList = new ArrayList<>();

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    @ApiModelProperty(value = "number of slot are allowed. Job/Internship -> slot | Thesis/Research 1 -> individual, >= 2 -> team")
    private int numSlot;

    @ManyToMany
    @Column(nullable = true)
    private List<Student> studentList = new ArrayList<>();

    @ManyToMany
    @Column(nullable = true)
    private List<Student> queueList = new ArrayList<>();

    @OneToOne
    private Student leader;

    @ManyToMany
    private List<Deliverable> deliverables = new ArrayList<>();

    @OneToOne
    private Account author;

    private Boolean status = null;

    @JsonIgnore
    @CreatedDate
    private Date createdDate;

    @JsonIgnore
    @CreatedBy
    private String createdBy;

//    @LastModifiedDate
//    @LastModifiedBy
}
