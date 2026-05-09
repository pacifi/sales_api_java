package pe.edu.upeu.sales_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.sales_api.dto.SaleLineResponseDTO;
import pe.edu.upeu.sales_api.entity.SaleLine;

@Mapper(componentModel = "spring")
public interface SaleLineMapper {

    @Mapping(target = "product.id", source = "product.id")
    @Mapping(target = "product.name", source = "product.name")
    @Mapping(target = "product.code", source = "product.code")
    SaleLineResponseDTO toResponseDTO(SaleLine saleLine);
}