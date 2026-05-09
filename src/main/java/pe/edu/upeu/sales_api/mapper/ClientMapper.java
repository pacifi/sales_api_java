package pe.edu.upeu.sales_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.sales_api.dto.ClientRequestDTO;
import pe.edu.upeu.sales_api.dto.ClientResponseDTO;
import pe.edu.upeu.sales_api.entity.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    Client toEntity(ClientRequestDTO dto);

    ClientResponseDTO toResponseDTO(Client client);

    @Mapping(target = "id", ignore = true)
    void updateEntity(ClientRequestDTO dto, @MappingTarget Client client);
}