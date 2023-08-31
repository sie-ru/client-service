package com.test.client.service;

import com.test.client.dto.GetClientDTO;
import com.test.client.dto.SaveClientDTO;
import com.test.client.entity.Client;

import java.util.List;

public interface ClientService {

    List<Client> getClientList();

    GetClientDTO getClient(Long clientId);

    Client addClient(SaveClientDTO client);

    Client updateClient(Long clientId, SaveClientDTO client);

    void deleteClient(Long clientId);

    Boolean checkIfClientExists(Long clientId);
}
