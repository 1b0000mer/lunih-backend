package com.gsquad.lunih.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsquad.lunih.entities.categories.PostType;
import com.gsquad.lunih.entities.categories.Spectrum;
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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private PostType postType;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    @OneToMany
    private List<Spectrum> spectrumList = new ArrayList<>();

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    @ApiModelProperty(value = "number of slot are allowed. Job/Internship -> slot | Thesis/Research 1 -> individual, >= 2 -> team")
    private int numSlot;

    @OneToMany
    private List<Student> studentList = new ArrayList<>();

    @OneToMany
    private List<Student> queueList = new ArrayList<>();

    @OneToOne
    private Student leader;

    @OneToMany
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
}
