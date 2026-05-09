package pe.edu.upeu.sales_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sales_api.dto.SaleReportDTO;
import pe.edu.upeu.sales_api.dto.SaleRequestDTO;
import pe.edu.upeu.sales_api.dto.SaleResponseDTO;
import pe.edu.upeu.sales_api.service.SaleService;
import java.util.UUID;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleResponseDTO> create(@Valid @RequestBody SaleRequestDTO dto) {
        return ResponseEntity.status(201).body(saleService.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<SaleResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(saleService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(saleService.findById(id));
    }

    @GetMapping("/report")
    public ResponseEntity<SaleReportDTO> getReport() {
        return ResponseEntity.ok(saleService.getReport());
    }
}