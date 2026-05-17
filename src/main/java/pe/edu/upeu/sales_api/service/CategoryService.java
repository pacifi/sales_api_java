package pe.edu.upeu.sales_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.edu.upeu.sales_api.dto.CategoryRequestDTO;
import pe.edu.upeu.sales_api.dto.CategoryResponseDTO;
import java.util.List;
import java.util.UUID;

public interface CategoryService {

    Page<CategoryResponseDTO> findAll(String q, Pageable pageable);

    CategoryResponseDTO findById(UUID id);

    CategoryResponseDTO create(CategoryRequestDTO dto);

    CategoryResponseDTO update(UUID id, CategoryRequestDTO dto);

    void delete(UUID id);

    List<CategoryResponseDTO> search(String q);
}