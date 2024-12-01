package com.principal.pruebaspringbootweb.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AuthUserDto {

    private String email;
    private String password;
    private List<Long> roleIds;
    private boolean enabled;

}
