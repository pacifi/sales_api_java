package pe.edu.upeu.sales_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sales_api.dto.ProductRequestDTO;
import pe.edu.upeu.sales_api.dto.ProductResponseDTO;
import pe.edu.upeu.sales_api.entity.Product;
import pe.edu.upeu.sales_api.mapper.ProductMapper;
import pe.edu.upeu.sales_api.repository.CategoryRepository;
import pe.edu.upeu.sales_api.repository.ProductRepository;
import pe.edu.upeu.sales_api.service.ProductService;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO findById(UUID id) {
        return productRepository.findById(id)
                .map(productMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }

    @Override
    @Transactional
    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = productMapper.toEntity(dto);
        product.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + dto.getCategoryId())));
        return productMapper.toResponseDTO(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponseDTO update(UUID id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        productMapper.updateEntity(dto, product);
        product.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + dto.getCategoryId())));
        return productMapper.toResponseDTO(productRepository.save(product));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        productRepository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> search(String q) {
        return productRepository
                .findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(q, q)
                .stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }
}