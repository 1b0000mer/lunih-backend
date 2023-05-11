package com.gsquad.lunih.entities.categories;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiModelProperty(name = "name of Program in English")
    @Column(nullable = false)
    private String nameEn;

    @ApiModelProperty(name = "name of Program in Latvian")
    @Column(nullable = false)
    private String nameLv;

    @ApiModelProperty(name = "get from EnumStudyLevel")
    private String studyLevel;

    @OneToOne
    private Faculty faculty;

    @OneToMany
    @ApiModelProperty(name = "field of the program?")
    private List<Industry> industryList = new ArrayList<>();

    private Boolean status = true;
}
