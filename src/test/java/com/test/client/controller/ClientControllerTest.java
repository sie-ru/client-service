package com.test.client.controller;

import com.test.client.client.OrderClient;
import com.test.client.dto.GetClientDTO;
import com.test.client.dto.SaveClientDTO;
import com.test.client.entity.Client;
import com.test.client.entity.ClientTypes;
import com.test.client.model.Category;
import com.test.client.model.Order;
import com.test.client.repository.ClientRepository;
import com.test.client.service.ClientService;
import liquibase.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderClient orderClient;

    @MockBean
    private ClientRepository repository;

    @MockBean
    private ClientService service;

    @Test
    void getClient() throws Exception {
        long clientId = 1L;
        List<Order> orders = List.of(
                new Order(1L, "Goods #1", Category.CATEGORY_1),
                new Order(2L, "Goods #2", Category.CATEGORY_2),
                new Order(3L, "Goods #3", Category.CATEGORY_3)
        );
        ClientTypes clientTypes = new ClientTypes(1L, "B2C", "Потребители", "b2c");

        GetClientDTO client = new GetClientDTO(
                1L,
                "ООО Компания #1",
                "Общество с ограниченной отвественностью Компания #1",
                clientTypes,
                "111111111111",
                "1111111111",
                orders,
                new Date(),
                new Date()
        );

        when(service.getClient(clientId)).thenReturn(client);
        when(orderClient.getOrdersByClientId(clientId)).thenReturn(orders);

        mvc.perform(get("/api/client/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(clientId))
                .andExpect(jsonPath("$.shortName").value("ООО Компания #1"))
                .andExpect(jsonPath("$.fullName").value("Общество с ограниченной отвественностью Компания #1"))
                .andExpect(jsonPath("$.clientTypes").value(clientTypes))
                .andExpect(jsonPath("$.inn").value("111111111111"))
                .andExpect(jsonPath("$.okpo").value("1111111111"));
    }

    @Test
    void addClient_ShouldAddNewClient_AndReturnClient() throws Exception {
        ClientTypes clientTypes = new ClientTypes();
        clientTypes.setId(1L);

        SaveClientDTO requestBody = new SaveClientDTO(
                "ООО Компания #1",
                "Общество с ограниченной отвественностью Компания #1",
                clientTypes,
                "111111111111",
                "1111111111"
        );

        Client savedClient = new Client(
                1L,
                "ООО Компания #1",
                "Общество с ограниченной отвественностью Компания #1",
                clientTypes,
                "111111111111",
                "1111111111",
                new Date(),
                new Date()
        );

        when(service.addClient(any(SaveClientDTO.class))).thenReturn(savedClient);

        mvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n" +
                                "    \"shortName\": \"ООО Компания #1\",\n" +
                                "    \"fullName\": \"Общество с ограниченной отвественностью Компания #1\",\n" +
                                "    \"clientTypes\": {\n" +
                                "        \"id\": 1\n" +
                                "    },\n" +
                                "    \"inn\": \"123412341234\",\n" +
                                "    \"okpo\": \"1234123412\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.shortName").value("ООО Компания #1"))
                .andExpect(jsonPath("$.fullName").value("Общество с ограниченной отвественностью Компания #1"));
    }

    @Test
    void addClient_ShouldReturnBadRequest() throws Exception {
        ClientTypes clientTypes = new ClientTypes();
        clientTypes.setId(1L);

        SaveClientDTO requestBody = new SaveClientDTO(
                "ООО Компания #1",
                "Общество с ограниченной отвественностью Компания #1",
                clientTypes,
                "111111111111",
                "1111111111"
        );

        Client savedClient = new Client(
                1L,
                "ООО Компания #1",
                "Общество с ограниченной отвественностью Компания #1",
                clientTypes,
                "111111111111",
                "1111111111",
                new Date(),
                new Date()
        );

        when(service.updateClient(any(Long.class), any(SaveClientDTO.class))).thenReturn(savedClient);

        mvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n" +
                                "    \"shortName\": " + StringUtil.repeat("a", 200) + ",\n" +
                                "    \"fullName\": \"Full test company\",\n" +
                                "    \"clientTypes\": {\n" +
                                "        \"id\": 1\n" +
                                "    },\n" +
                                "    \"inn\": \"123412341234\",\n" +
                                "    \"okpo\": \"1234123412\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateClientShouldUpdateClient_AndReturnClient() throws Exception {
        Long clientId = 1L;
        ClientTypes clientTypes = new ClientTypes();
        clientTypes.setId(1L);

        SaveClientDTO requestBody = new SaveClientDTO(
                "ООО Компания #1",
                "Общество с ограниченной отвественностью Компания #1",
                clientTypes,
                "111111111111",
                "1111111111"
        );

        Client updatedClient = new Client(
                1L,
                "ООО Компания #1",
                "Общество с ограниченной отвественностью Компания #1",
                clientTypes,
                "111111111111",
                "1111111111",
                new Date(),
                new Date()
        );

        when(service.updateClient(any(Long.class), any(SaveClientDTO.class))).thenReturn(updatedClient);

        mvc.perform(put("/api/client/{clientUd}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"shortName\": \"ООО Компания #1\",\n" +
                                "    \"fullName\": \"Общество с ограниченной отвественностью Компания #1\",\n" +
                                "    \"clientTypes\": {\n" +
                                "        \"id\": 1\n" +
                                "    },\n" +
                                "    \"inn\": \"123412341234\",\n" +
                                "    \"okpo\": \"1234123412\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortName").value("ООО Компания #1"))
                .andExpect(jsonPath("$.fullName").value("Общество с ограниченной отвественностью Компания #1"));
    }

    @Test
    void updateClientShouldReturnBadRequest() throws Exception {
        Long clientId = 1L;
        ClientTypes clientTypes = new ClientTypes();
        clientTypes.setId(1L);

        SaveClientDTO requestBody = new SaveClientDTO(
                "ООО Компания #1",
                "Общество с ограниченной отвественностью Компания #1",
                clientTypes,
                "111111111111",
                "1111111111"
        );

        Client updatedClient = new Client(
                1L,
                "ООО Компания #1",
                "Общество с ограниченной отвественностью Компания #1",
                clientTypes,
                "111111111111",
                "1111111111",
                new Date(),
                new Date()
        );

        when(service.updateClient(any(Long.class), any(SaveClientDTO.class))).thenReturn(updatedClient);

        mvc.perform(put("/api/client/{clientUd}", clientId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n" +
                                "    \"shortName\": " + StringUtil.repeat("a", 200) + ",\n" +
                                "    \"fullName\": \"Full test company\",\n" +
                                "    \"clientTypes\": {\n" +
                                "        \"id\": 1\n" +
                                "    },\n" +
                                "    \"inn\": \"123412341234\",\n" +
                                "    \"okpo\": \"1234123412\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkIfClientExists_ExistingClient() throws Exception {
        Long clientId = 1L;
        when(service.checkIfClientExists(clientId)).thenReturn(true);

        mvc.perform(get("/api/client/{clientId}/exist", clientId))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void checkIfClientExists_NotExistingClient() throws Exception {
        Long clientId = 999L;
        when(service.checkIfClientExists(clientId)).thenReturn(false);

        mvc.perform(get("/api/client/{clientId}/exist", clientId))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}