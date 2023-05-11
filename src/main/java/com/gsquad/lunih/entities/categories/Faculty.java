package com.gsquad.lunih.entities.categories;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiModelProperty(name = "name of Faculty in English")
    @Column(nullable = false)
    private String nameEn;

    @ApiModelProperty(name = "name of Faculty in Latvian")
    @Column(nullable = false)
    private String nameLv;

    @ApiModelProperty(name = "soft delete")
    private Boolean status = true;
}
