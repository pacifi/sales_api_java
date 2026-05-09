package pe.edu.upeu.sales_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.edu.upeu.sales_api.dto.SaleReportDTO;
import pe.edu.upeu.sales_api.dto.SaleRequestDTO;
import pe.edu.upeu.sales_api.dto.SaleResponseDTO;
import java.util.UUID;

public interface SaleService {

    SaleResponseDTO create(SaleRequestDTO dto);

    Page<SaleResponseDTO> findAll(Pageable pageable);

    SaleResponseDTO findById(UUID id);

    SaleReportDTO getReport();
}