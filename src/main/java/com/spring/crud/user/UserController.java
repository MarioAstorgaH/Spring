package com.spring.crud.user;

import com.spring.crud.user.dto.CreateUserDTO;
import com.spring.crud.user.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<UserResponseDTO> getAllUsers(){
        return this.userService.getAllUsers();
    }
    @PostMapping()
    public UserResponseDTO saveUser(@RequestBody CreateUserDTO user){
        return this.userService.createUser(user);
    }

    @GetMapping(path="/{id}")
    public Optional<UserEntity> getUserById(@PathVariable Long id){
        return this.userService.getUserById(id);
    }

}
