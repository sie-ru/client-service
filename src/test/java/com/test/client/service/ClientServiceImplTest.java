package com.test.client.service;

import com.test.client.client.OrderClient;
import com.test.client.dto.GetClientDTO;
import com.test.client.dto.SaveClientDTO;
import com.test.client.entity.Client;
import com.test.client.entity.ClientTypes;
import com.test.client.exception.ClientNotFoundException;
import com.test.client.model.Category;
import com.test.client.model.Order;
import com.test.client.repository.ClientRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClientServiceImplTest {

    @Autowired
    private ClientServiceImpl clientService;

    @MockBean
    private ClientRepository repository;

    @MockBean
    private OrderClient orderClient;

    private ClientTypes clientTypes;
    private Client client;
    private List<Order> orders;
    private GetClientDTO getClientDTO;

    @BeforeEach
    public void setUp() {
        clientTypes = new ClientTypes(1L, "B2C", "Потребители", "b2c");

        client = new Client(
                1L,
                "ООО Компания #1",
                "Общество с ограниченной отвественностью Компания #1",
                clientTypes,
                "111111111111",
                "1111111111",
                new Date(),
                new Date()
        );

        orders = List.of(
                new Order(1L, "Goods #1", Category.CATEGORY_1),
                new Order(2L, "Goods #2", Category.CATEGORY_2),
                new Order(3L, "Goods #3", Category.CATEGORY_3)
        );

        getClientDTO = new GetClientDTO(
                1L,
                "ООО Компания #1",
                "Общество с ограниченной отвественностью Компания #1",
                this.clientTypes,
                "111111111111",
                "1111111111",
                this.orders,
                new Date(),
                new Date()
        );
    }

    @Test
    void getClient_ShouldReturnValidData() {
        Long clientId = 1L;
        when(repository.findById(clientId)).thenReturn(Optional.of(client));
        when(orderClient.getOrdersByClientId(clientId)).thenReturn(orders);

        GetClientDTO actual = clientService.getClient(clientId);

        System.out.println(this.getClientDTO);
        System.out.println(actual);

        assertNotNull(actual);
        assertEquals(this.getClientDTO, actual);

        verify(repository, times(1)).findById(clientId);
        verify(orderClient, times(1)).getOrdersByClientId(clientId);
    }

    @Test
    void getClient_ShouldThrowException() {
        Long clientId = 1L;
        when(repository.findById(clientId)).thenReturn(Optional.empty());
        when(orderClient.getOrdersByClientId(clientId)).thenReturn(orders);

        assertThrows(ClientNotFoundException.class, () -> clientService.getClient(clientId));

        verify(repository, times(1)).findById(clientId);
    }

    @Test
    void addClient_ShouldAddNewClient_AndReturnClient() {
        SaveClientDTO dto = new SaveClientDTO();
        dto.setShortName("Test Company");
        dto.setFullName("Test Company");
        dto.setClientTypes(this.clientTypes);
        dto.setInn("1234567890");
        dto.setOkpo("123456789");

        Client savedClient = new Client();
        savedClient.setId(1L);
        savedClient.setShortName(dto.getShortName());
        savedClient.setFullName(dto.getFullName());
        savedClient.setClientType(dto.getClientTypes());
        savedClient.setInn(dto.getInn());
        savedClient.setOkpo(dto.getOkpo());

        when(repository.save(any(Client.class))).thenReturn(savedClient);

        Client result = clientService.addClient(dto);

        verify(repository).save(argThat(client -> client.getShortName().equals("Test Company") &&
                client.getFullName().equals("Test Company") &&
                client.getClientType().equals(this.clientTypes) &&
                client.getInn().equals("1234567890") &&
                client.getOkpo().equals("123456789"))
        );

        assertEquals(savedClient, result);
    }

    @Test
    void updateClient_ShouldUpdateClient_AndReturnClient() {
        Long clientId = 1L;
        SaveClientDTO dto = new SaveClientDTO();
        dto.setShortName("New Short Name");
        dto.setFullName("New Full Name");
        dto.setInn("123412341234");
        dto.setOkpo("1234123412");
        dto.setClientTypes(this.clientTypes);

        Client existingClient = new Client();
        existingClient.setId(clientId);
        existingClient.setShortName("Old Short Name");
        existingClient.setFullName("Old Full Name");
        existingClient.setInn("123412341234");
        existingClient.setOkpo("1234123412");
        existingClient.setClientType(this.clientTypes);

        when(repository.findById(clientId)).thenReturn(Optional.of(existingClient));
        when(repository.save(any(Client.class))).thenReturn(existingClient);

        Client updatedClient = clientService.updateClient(clientId, dto);

        assertNotNull(updatedClient);
        assertEquals(dto.getShortName(), updatedClient.getShortName());
        assertEquals(dto.getFullName(), updatedClient.getFullName());
        assertEquals(dto.getInn(), updatedClient.getInn());
        assertEquals(dto.getOkpo(), updatedClient.getOkpo());
        assertEquals(dto.getClientTypes(), updatedClient.getClientType());
    }

    @Test
    void updateClient_ShouldThrowsException_IfClientNotFound() {
        Long clientId = 1L;
        SaveClientDTO dto = new SaveClientDTO();

        when(repository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.updateClient(clientId, dto));
    }

    @Test
    void deleteClient() {
        Long clientId = 1L;

        clientService.deleteClient(clientId);

        verify(repository).deleteClientById(clientId);
    }

    @Test
    void checkIfClientExists_ShouldReturnTrue_IfClientExists() {
        Long clientId = 1L;
        when(repository.existsById(clientId)).thenReturn(true);

        Boolean result = clientService.checkIfClientExists(clientId);

        assertEquals(true, result);
    }

    @Test
    void checkIfClientExists_ShouldReturnFalse_IfClientDoesNotExist() {
        Long clientId = 1L;
        when(repository.existsById(clientId)).thenReturn(false);

        Boolean result = clientService.checkIfClientExists(clientId);

        assertEquals(false, result);
    }
}