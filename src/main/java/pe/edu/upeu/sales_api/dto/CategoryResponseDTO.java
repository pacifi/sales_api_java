package pe.edu.upeu.sales_api.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CategoryResponseDTO {

    private UUID id;
    private String name;
    private String description;
    private Boolean isActive;
}