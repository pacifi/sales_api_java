package pe.edu.upeu.sales_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.edu.upeu.sales_api.dto.ClientRequestDTO;
import pe.edu.upeu.sales_api.dto.ClientResponseDTO;
import java.util.List;
import java.util.UUID;

public interface ClientService {

    Page<ClientResponseDTO> findAll(Pageable pageable);

    ClientResponseDTO findById(UUID id);

    ClientResponseDTO create(ClientRequestDTO dto);

    ClientResponseDTO update(UUID id, ClientRequestDTO dto);

    void delete(UUID id);

    List<ClientResponseDTO> search(String q);
}