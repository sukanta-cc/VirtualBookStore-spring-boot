package com.virtualbookstore.VirtualBookStore.Dtos.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    private String name;
    private String email;
    private String password;
}


