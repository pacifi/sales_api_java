package pe.edu.upeu.sales_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.edu.upeu.sales_api.dto.ProductRequestDTO;
import pe.edu.upeu.sales_api.dto.ProductResponseDTO;
import java.util.List;
import java.util.UUID;

public interface ProductService {

    Page<ProductResponseDTO> findAll(Pageable pageable);

    ProductResponseDTO findById(UUID id);

    ProductResponseDTO create(ProductRequestDTO dto);

    ProductResponseDTO update(UUID id, ProductRequestDTO dto);

    void delete(UUID id);

    List<ProductResponseDTO> search(String q);
}