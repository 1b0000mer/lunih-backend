package com.gsquad.lunih.dtos;

import com.gsquad.lunih.entities.Account;
import com.gsquad.lunih.entities.Deliverable;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.entities.categories.PostType;
import com.gsquad.lunih.entities.categories.Spectrum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostDTO {

    private List<String> studentList = new ArrayList<>();

    private int postType;

    private String title;

    private String description;

    private List<Spectrum> spectrumList = new ArrayList<>();

    private  Date startDate;

    private Date endDate;

    private int numSlot;

    private List<Student> queueList = new ArrayList<>();

    private Student leader;

    private List<Deliverable> deliverables = new ArrayList<>();

    private Account author;
}
