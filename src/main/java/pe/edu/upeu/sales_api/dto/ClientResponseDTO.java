package pe.edu.upeu.sales_api.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ClientResponseDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Boolean isActive;
}