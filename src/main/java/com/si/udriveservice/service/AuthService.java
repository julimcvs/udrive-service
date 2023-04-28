package com.si.udriveservice.service;

import com.si.udriveservice.configuration.security.JwtService;
import com.si.udriveservice.model.dto.AuthDTO;
import com.si.udriveservice.model.dto.LoginDTO;
import com.si.udriveservice.model.dto.PasswordDTO;
import com.si.udriveservice.model.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthDTO authenticate(LoginDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        var user = userService.loadUserByUsername(dto.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return new AuthDTO(jwtToken);
    }

    public UserDTO register(UserDTO dto) {
        return userService.save(dto);
    }

    public UserDTO savePassword(PasswordDTO dto) {
        return userService.savePassword(dto);
    }
}
