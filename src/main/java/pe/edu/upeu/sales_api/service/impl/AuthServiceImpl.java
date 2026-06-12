package pe.edu.upeu.sales_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sales_api.dto.AuthRegisterResponseDTO;
import pe.edu.upeu.sales_api.dto.AuthRequestDTO;
import pe.edu.upeu.sales_api.dto.AuthResponseDTO;
import pe.edu.upeu.sales_api.entity.User;
import pe.edu.upeu.sales_api.exception.AppException;
import pe.edu.upeu.sales_api.repository.UserRepository;
import pe.edu.upeu.sales_api.security.JwtUtil;
import pe.edu.upeu.sales_api.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthRegisterResponseDTO register(AuthRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        return new AuthRegisterResponseDTO(user.getUsername(), user.getRole());
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow();
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        return new AuthResponseDTO(token, user.getUsername(), user.getRole());
    }

    @Override
    public AuthRegisterResponseDTO promote(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User not found: " + username));
        user.setRole("ADMIN");
        userRepository.save(user);
        return new AuthRegisterResponseDTO(user.getUsername(), user.getRole());
    }
}