package pe.edu.upeu.sales_api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class SaleResponseDTO {

    private UUID id;
    private LocalDateTime saleDate;
    private BigDecimal total;
    private ClientInfo client;
    private List<SaleLineResponseDTO> lines;

    @Data
    public static class ClientInfo {
        private UUID id;
        private String firstName;
        private String lastName;
    }
}