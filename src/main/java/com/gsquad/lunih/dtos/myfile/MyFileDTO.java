package com.gsquad.lunih.dtos.myfile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyFileDTO {
    private String fileName;

    private String path;

    private long size;

    private String fileType;
}
