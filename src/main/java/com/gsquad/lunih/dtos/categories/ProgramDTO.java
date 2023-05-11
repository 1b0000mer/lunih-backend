package com.gsquad.lunih.dtos.categories;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ProgramDTO {
    private String nameEn;

    private String nameLv;

    private String studyLevel;
    private int facultyID;
    private List<Integer> industryList = new ArrayList<>();

}
