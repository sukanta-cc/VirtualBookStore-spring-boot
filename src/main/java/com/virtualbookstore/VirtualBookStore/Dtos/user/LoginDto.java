package com.virtualbookstore.VirtualBookStore.Dtos.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String email;
    private String password;
}
