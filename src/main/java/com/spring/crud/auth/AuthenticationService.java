package com.spring.crud.auth;

import com.spring.crud.auth.dto.AuthResponse;
import com.spring.crud.auth.dto.LoginRequest;
import com.spring.crud.config.JwtService;
import com.spring.crud.user.Role;
import com.spring.crud.user.UserEntity;
import com.spring.crud.user.UserRepository;
import com.spring.crud.user.dto.CreateUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(CreateUserDTO request) {
        // 1. Construir el usuario con la contraseña ENCRIPTADA
        var user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // <--- OJO AQUÍ
                .birthDate(request.getBirthDate())
                .role(Role.USER) // Por defecto, todos son USER
                .build();

        // 2. Guardar en BD
        userRepository.save(user);

        // 3. Generar Token
        var jwtToken = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse authenticate(LoginRequest request) {
        // 1. Autenticar con el Manager (Esto verifica usuario y contraseña automáticamente)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Si pasa el paso anterior, el usuario es correcto. Buscamos sus datos.
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(); // Aquí ya sabemos que existe porque autenticó bien

        // 3. Generar Token
        var jwtToken = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}