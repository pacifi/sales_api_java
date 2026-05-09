package pe.edu.upeu.sales_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.sales_api.dto.SaleResponseDTO;
import pe.edu.upeu.sales_api.entity.Sale;

@Mapper(componentModel = "spring", uses = {SaleLineMapper.class})
public interface SaleMapper {

    @Mapping(target = "client.id", source = "client.id")
    @Mapping(target = "client.firstName", source = "client.firstName")
    @Mapping(target = "client.lastName", source = "client.lastName")
    @Mapping(target = "lines", source = "lines")
    SaleResponseDTO toResponseDTO(Sale sale);
}