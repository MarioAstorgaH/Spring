package com.spring.crud.user;

import com.spring.crud.user.dto.CreateUserDTO;
import com.spring.crud.user.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponseDTO createUser(CreateUserDTO user){
        UserEntity entity=userMapper.toEntity(user);
        UserEntity savedUser=userRepository.save(entity);
        return  userMapper.toResponse(savedUser);
    }
    public Optional<UserEntity> getUserById(Long id){
        return userRepository.findById(id);
    }
}
