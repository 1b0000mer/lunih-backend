package com.gsquad.lunih.dtos.accountDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDetails {

    private String fullName;

    private String email;

    private String token;

    private String avatar;

    private long expired;

    private List<String> roles = new ArrayList<>();

}
