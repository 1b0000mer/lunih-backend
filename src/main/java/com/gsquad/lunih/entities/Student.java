package com.gsquad.lunih.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class Student {

    @Id
    private String studentID;

    private String firstName;

    private String surName;

    @OneToOne
    @JoinColumn
    private Account account;
}
