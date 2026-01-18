package com.spring.crud.user.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
}
