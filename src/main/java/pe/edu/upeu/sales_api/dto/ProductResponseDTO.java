package pe.edu.upeu.sales_api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductResponseDTO {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private String code;
    private Integer stock;
    private Boolean isActive;
    private CategoryInfo category;

    @Data
    public static class CategoryInfo {
        private UUID id;
        private String name;
    }
}