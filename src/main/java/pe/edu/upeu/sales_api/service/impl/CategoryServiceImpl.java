package pe.edu.upeu.sales_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sales_api.dto.CategoryRequestDTO;
import pe.edu.upeu.sales_api.dto.CategoryResponseDTO;
import pe.edu.upeu.sales_api.entity.Category;
import pe.edu.upeu.sales_api.mapper.CategoryMapper;
import pe.edu.upeu.sales_api.repository.CategoryRepository;
import pe.edu.upeu.sales_api.service.CategoryService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
@Transactional(readOnly = true)
public Page<CategoryResponseDTO> findAll(String q, Pageable pageable) {
    Page<Category> page = (q != null && !q.isBlank())
            ? categoryRepository.findByNameContainingIgnoreCase(q, pageable)
            : categoryRepository.findAll(pageable);
    return page.map(categoryMapper::toResponseDTO);
}

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO findById(UUID id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));
    }

    @Override
    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        Category category = categoryMapper.toEntity(dto);
        return categoryMapper.toResponseDTO(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponseDTO update(UUID id, CategoryRequestDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));
        categoryMapper.updateEntity(dto, category);
        return categoryMapper.toResponseDTO(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));
        categoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> search(String q) {
        return categoryRepository.findByNameContainingIgnoreCase(q)
                .stream()
                .map(categoryMapper::toResponseDTO)
                .toList();
    }
}