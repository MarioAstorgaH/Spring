package com.spring.crud.user;

import com.spring.crud.user.dto.CreateUserDTO;
import com.spring.crud.user.dto.UserResponseDTO;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    public UserEntity toEntity(CreateUserDTO dto){
        UserEntity entity= new UserEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setBirthDate(dto.getBirthDate());
        return entity;
    }
    public UserResponseDTO toResponse(UserEntity entity){
        UserResponseDTO dto= new UserResponseDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setBirthDate(entity.getBirthDate());
        return dto;
    }
}
