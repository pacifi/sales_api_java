package pe.edu.upeu.sales_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class SaleRequestDTO {

    @NotNull(message = "Client is required")
    private UUID clientId;

    @NotEmpty(message = "Sale must have at least one line")
    @Valid
    private List<SaleLineRequestDTO> lines;
}