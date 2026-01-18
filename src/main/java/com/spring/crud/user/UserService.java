package com.spring.crud.user;

import com.spring.crud.exception.UserAlreadyExistsException;
import com.spring.crud.exception.UserNotFoundException;
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
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException("Email: "+user.getEmail()+" is taken");
        }
        UserEntity entity=userMapper.toEntity(user);
        UserEntity savedUser=userRepository.save(entity);
        return  userMapper.toResponse(savedUser);
    }
    public UserResponseDTO getUserById(Long id){
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(()->new UserNotFoundException("User: "+id+" not found"));
    }

    public UserResponseDTO updateUser(Long id,CreateUserDTO userDTO){
        UserEntity user=userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User: "+id+" not found"));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        UserEntity updatedUser=userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }
}
