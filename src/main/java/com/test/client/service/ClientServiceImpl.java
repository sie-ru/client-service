package com.test.client.service;

import com.test.client.client.OrderClient;
import com.test.client.dto.GetClientDTO;
import com.test.client.dto.SaveClientDTO;
import com.test.client.entity.Client;
import com.test.client.exception.ClientNotFoundException;
import com.test.client.model.Order;
import com.test.client.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ModelMapper mapper;

    private final OrderClient orderClient;

    private final ClientRepository clientRepository;

    Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    public ClientServiceImpl(ModelMapper mapper, OrderClient orderClient, ClientRepository clientRepository) {
        this.mapper = mapper;
        this.orderClient = orderClient;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getClientList() {
        return clientRepository.findAll();
    }

    @Override
    public GetClientDTO getClient(Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);

        if(client.isEmpty()) {
            throw new ClientNotFoundException("Client with id: " + clientId + " not found");
        }

        Client existingClient = client.get();
        List<Order> orders = orderClient.getOrdersByClientId(clientId);

        return new GetClientDTO(
                existingClient.getId(),
                existingClient.getShortName(),
                existingClient.getFullName(),
                existingClient.getClientType(),
                existingClient.getInn(),
                existingClient.getOkpo(),
                orders,
                existingClient.getCreationDate(),
                existingClient.getModificationDate()
        );
    }

    @Override
    public Client addClient(SaveClientDTO dto) {
        Client newClient = new Client();
        newClient.setShortName(dto.getShortName());
        newClient.setFullName(dto.getFullName());
        newClient.setClientType(dto.getClientTypes());
        newClient.setInn(dto.getInn());
        newClient.setOkpo(dto.getOkpo());
        try {
            return clientRepository.save(newClient);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Transactional
    @Override
    public Client updateClient(Long clientId, SaveClientDTO dto) {
        Optional<Client> existingClient = clientRepository.findById(clientId);

        if(existingClient.isPresent()) {
            Client clientToUpdate = existingClient.get();

            clientToUpdate.setShortName(dto.getShortName());
            clientToUpdate.setFullName(dto.getFullName());
            clientToUpdate.setInn(dto.getInn());
            clientToUpdate.setOkpo(dto.getOkpo());
            clientToUpdate.setClientType(dto.getClientTypes());

            return clientRepository.save(clientToUpdate);
        }

        throw new ClientNotFoundException("Client with id: " + clientId + " not found");
    }

    @Override
    public void deleteClient(Long clientId) {
        clientRepository.deleteClientById(clientId);
    }

    @Override
    public Boolean checkIfClientExists(Long clientId) {
        return clientRepository.existsById(clientId);
    }
}
