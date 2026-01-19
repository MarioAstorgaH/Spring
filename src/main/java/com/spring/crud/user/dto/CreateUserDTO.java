package com.spring.crud.user.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CreateUserDTO {
    @NotBlank(message = "firstName needs to be provided")
    private String firstName;
    @NotBlank(message = "lastName needs to be provided")
    private String lastName;
    @Email(message = "Provide a valid email format")
    @NotBlank(message = "email needs to be provided")
    private String email;
    @Size(min = 8,message = "Password needs to be 8 characters at least")
    @NotBlank(message = "password needs to be provided")
    private String password;
    @Past(message = "birthdate needs to be before today")
    @NotNull(message = "birthdate needs to be provided")
    private LocalDate birthDate;
}
