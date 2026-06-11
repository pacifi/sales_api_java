package pe.edu.upeu.sales_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRegisterResponseDTO {
    private String username;
    private String role;
}