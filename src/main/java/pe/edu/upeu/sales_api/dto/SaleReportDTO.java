package pe.edu.upeu.sales_api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SaleReportDTO {

    private Long totalSales;
    private BigDecimal totalRevenue;
    private List<SaleResponseDTO> sales;
}