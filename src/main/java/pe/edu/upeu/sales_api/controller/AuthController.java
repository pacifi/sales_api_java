package pe.edu.upeu.sales_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sales_api.dto.AuthRequestDTO;
import pe.edu.upeu.sales_api.dto.AuthResponseDTO;
import pe.edu.upeu.sales_api.dto.AuthRegisterResponseDTO;
import pe.edu.upeu.sales_api.entity.User;
import pe.edu.upeu.sales_api.exception.AppException;
import pe.edu.upeu.sales_api.repository.UserRepository;
import pe.edu.upeu.sales_api.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<AuthRegisterResponseDTO> register(@Valid @RequestBody AuthRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        return ResponseEntity.status(201).body(new AuthRegisterResponseDTO(user.getUsername(), user.getRole()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow();
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        return ResponseEntity.ok(new AuthResponseDTO(token, user.getUsername(), user.getRole()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/promote/{username}")
    public ResponseEntity<AuthRegisterResponseDTO> promote(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User not found: " + username));
        user.setRole("ADMIN");
        userRepository.save(user);
        return ResponseEntity.ok(new AuthRegisterResponseDTO(user.getUsername(), user.getRole()));
    }


}