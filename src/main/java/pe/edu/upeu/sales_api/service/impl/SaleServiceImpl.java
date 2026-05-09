package pe.edu.upeu.sales_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sales_api.dto.SaleLineRequestDTO;
import pe.edu.upeu.sales_api.dto.SaleReportDTO;
import pe.edu.upeu.sales_api.dto.SaleRequestDTO;
import pe.edu.upeu.sales_api.dto.SaleResponseDTO;
import pe.edu.upeu.sales_api.entity.Product;
import pe.edu.upeu.sales_api.entity.Sale;
import pe.edu.upeu.sales_api.entity.SaleLine;
import pe.edu.upeu.sales_api.mapper.SaleMapper;
import pe.edu.upeu.sales_api.repository.ClientRepository;
import pe.edu.upeu.sales_api.repository.ProductRepository;
import pe.edu.upeu.sales_api.repository.SaleRepository;
import pe.edu.upeu.sales_api.service.SaleService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final SaleMapper saleMapper;

    @Override
    @Transactional
    public SaleResponseDTO create(SaleRequestDTO dto) {

        // 1. Validar cliente
        Sale sale = new Sale();
        sale.setClient(clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found: " + dto.getClientId())));
        sale.setSaleDate(LocalDateTime.now());

        // 2. Procesar cada línea
        BigDecimal total = BigDecimal.ZERO;

        for (SaleLineRequestDTO lineDTO : dto.getLines()) {

            // 3. Validar producto
            Product product = productRepository.findById(lineDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + lineDTO.getProductId()));

            // 4. Validar stock
            if (product.getStock() < lineDTO.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName()
                        + ". Available: " + product.getStock()
                        + ", Requested: " + lineDTO.getQuantity());
            }

            // 5. Calcular valores
            BigDecimal unitPrice = product.getPrice();
            BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(lineDTO.getQuantity()));

            // 6. Descontar stock
            product.setStock(product.getStock() - lineDTO.getQuantity());
            productRepository.save(product);

            // 7. Crear línea
            SaleLine line = new SaleLine();
            line.setProduct(product);
            line.setQuantity(lineDTO.getQuantity());
            line.setUnitPrice(unitPrice);
            line.setSubTotal(subTotal);

            sale.addLine(line);
            total = total.add(subTotal);
        }

        // 8. Asignar total y persistir
        sale.setTotal(total);
        return saleMapper.toResponseDTO(saleRepository.save(sale));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaleResponseDTO> findAll(Pageable pageable) {
        return saleRepository.findAll(pageable)
                .map(saleMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponseDTO findById(UUID id) {
        return saleRepository.findById(id)
                .map(saleMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Sale not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public SaleReportDTO getReport() {
        SaleReportDTO report = new SaleReportDTO();
        report.setTotalSales(saleRepository.count());
        report.setTotalRevenue(saleRepository.sumTotalRevenue());
        report.setSales(saleRepository.findAll()
                .stream()
                .map(saleMapper::toResponseDTO)
                .toList());
        return report;
    }
}