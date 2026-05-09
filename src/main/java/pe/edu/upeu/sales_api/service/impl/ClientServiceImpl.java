package pe.edu.upeu.sales_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sales_api.dto.ClientRequestDTO;
import pe.edu.upeu.sales_api.dto.ClientResponseDTO;
import pe.edu.upeu.sales_api.entity.Client;
import pe.edu.upeu.sales_api.mapper.ClientMapper;
import pe.edu.upeu.sales_api.repository.ClientRepository;
import pe.edu.upeu.sales_api.service.ClientService;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable)
                .map(clientMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDTO findById(UUID id) {
        return clientRepository.findById(id)
                .map(clientMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Client not found: " + id));
    }

    @Override
    @Transactional
    public ClientResponseDTO create(ClientRequestDTO dto) {
        Client client = clientMapper.toEntity(dto);
        return clientMapper.toResponseDTO(clientRepository.save(client));
    }

    @Override
    @Transactional
    public ClientResponseDTO update(UUID id, ClientRequestDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found: " + id));
        clientMapper.updateEntity(dto, client);
        return clientMapper.toResponseDTO(clientRepository.save(client));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found: " + id));
        clientRepository.delete(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponseDTO> search(String q) {
        return clientRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(q, q)
                .stream()
                .map(clientMapper::toResponseDTO)
                .toList();
    }
}