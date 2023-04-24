package com.gsquad.lunih.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostDTO {

    private List<String> studentList = new ArrayList<>();
}
