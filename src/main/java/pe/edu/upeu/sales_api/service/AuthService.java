package pe.edu.upeu.sales_api.service;

import pe.edu.upeu.sales_api.dto.AuthRegisterResponseDTO;
import pe.edu.upeu.sales_api.dto.AuthRequestDTO;
import pe.edu.upeu.sales_api.dto.AuthResponseDTO;

public interface AuthService {

    AuthRegisterResponseDTO register(AuthRequestDTO dto);

    AuthResponseDTO login(AuthRequestDTO dto);

    AuthRegisterResponseDTO promote(String username);
}