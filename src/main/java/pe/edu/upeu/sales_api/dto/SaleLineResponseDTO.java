package pe.edu.upeu.sales_api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class SaleLineResponseDTO {

    private UUID id;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;
    private ProductInfo product;

    @Data
    public static class ProductInfo {
        private UUID id;
        private String name;
        private String code;
    }
}