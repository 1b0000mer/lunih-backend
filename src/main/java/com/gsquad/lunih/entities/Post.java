package com.gsquad.lunih.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsquad.lunih.entities.categories.PostType;
import com.gsquad.lunih.entities.categories.Industry;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Industry> industryList = new ArrayList<>();

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    @ApiModelProperty(value = "number of slot are allowed. Job/Internship -> slot | Thesis/Research 1 -> individual, >= 2 -> team")
    private int numSlot;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_student_list")
    @Fetch(FetchMode.SUBSELECT)
    private List<Student> studentList = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_queue_list")
    @Fetch(FetchMode.SUBSELECT)
    private List<Student> queueList = new ArrayList<>();

    @ManyToOne
    private Student leader;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Deliverable> deliverables = new ArrayList<>();

    @ManyToOne
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
