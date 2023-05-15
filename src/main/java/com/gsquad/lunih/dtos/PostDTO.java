package com.gsquad.lunih.dtos;

import com.gsquad.lunih.entities.Deliverable;
import com.gsquad.lunih.entities.Student;
import com.gsquad.lunih.entities.categories.Industry;
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

    private int postType;

    private String titleEn;

    private String titleLv;

    private String descriptionEn;

    private String descriptionLv;

    private List<Integer> industryList = new ArrayList<>();

    private Date startDate;

    private Date endDate;

    @ApiModelProperty(value = "number of slot are allowed. Job/Internship -> slot | Thesis/Research 1 -> individual, >= 2 -> team")
    private int numSlot;

    private List<String> studentList = new ArrayList<>();

    private List<String> queueList = new ArrayList<>();

    @ApiModelProperty(value = "studentID")
    private String leader;

    private int author = -1;

    private List<Integer> deliverables = new ArrayList<>();

}
