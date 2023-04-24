package com.gsquad.lunih.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "user_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String email;

    @JsonIgnore
    @Column(nullable = false, length = 32)
    private String password;

    @ApiModelProperty(name = "get from EnumRole")
    private String role = null;

    @ApiModelProperty(value = "activate/deactivate account")
    private Boolean status = true;
}
