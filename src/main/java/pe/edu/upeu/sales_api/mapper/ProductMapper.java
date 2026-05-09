package pe.edu.upeu.sales_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.sales_api.dto.ProductRequestDTO;
import pe.edu.upeu.sales_api.dto.ProductResponseDTO;
import pe.edu.upeu.sales_api.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductRequestDTO dto);

    @Mapping(target = "category.id", source = "category.id")
    @Mapping(target = "category.name", source = "category.name")
    ProductResponseDTO toResponseDTO(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateEntity(ProductRequestDTO dto, @MappingTarget Product product);
}