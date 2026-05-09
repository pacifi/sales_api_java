package pe.edu.upeu.sales_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.sales_api.dto.CategoryRequestDTO;
import pe.edu.upeu.sales_api.dto.CategoryResponseDTO;
import pe.edu.upeu.sales_api.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryRequestDTO dto);

    CategoryResponseDTO toResponseDTO(Category category);

    @Mapping(target = "id", ignore = true)
    void updateEntity(CategoryRequestDTO dto, @MappingTarget Category category);
}