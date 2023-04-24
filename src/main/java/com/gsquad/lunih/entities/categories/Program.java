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

    @Column(nullable = false, length = 80)
    private String name;

    @ApiModelProperty(name = "get from EnumStudyLevel")
    private String studyLevel;

    @OneToOne
    private Faculty faculty;

    @OneToMany
    @ApiModelProperty(name = "field of the program?")
    private List<Spectrum> spectrumList = new ArrayList<>();

    private Boolean status = true;
}
