package pe.edu.upeu.sales_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sales_api.dto.AuthRegisterResponseDTO;
import pe.edu.upeu.sales_api.dto.AuthRequestDTO;
import pe.edu.upeu.sales_api.dto.AuthResponseDTO;
import pe.edu.upeu.sales_api.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthRegisterResponseDTO> register(@Valid @RequestBody AuthRequestDTO dto) {
        return ResponseEntity.status(201).body(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/promote/{username}")
    public ResponseEntity<AuthRegisterResponseDTO> promote(@PathVariable String username) {
        return ResponseEntity.ok(authService.promote(username));
    }
}