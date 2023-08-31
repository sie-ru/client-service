package com.test.client.controller;

import com.test.client.dto.GetClientDTO;
import com.test.client.dto.SaveClientDTO;
import com.test.client.entity.Client;
import com.test.client.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Client", description = "For working with clients")
@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Get client info")
    @GetMapping(value = "/{clientId}")
    public ResponseEntity<GetClientDTO> getClient(@PathVariable Long clientId) {
        GetClientDTO client = clientService.getClient(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

    @Operation(summary = "Add new client")
    @PostMapping
    public ResponseEntity<Client> addClient(@Valid @RequestBody SaveClientDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.addClient(body));
    }

    @Operation(summary = "Update client")
    @PutMapping("/{clientId}")
    public ResponseEntity<Client> updateClient(@Valid @RequestBody SaveClientDTO body, @PathVariable Long clientId) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.updateClient(clientId, body));
    }

    @Operation(summary = "Check if client exists")
    @GetMapping("/{clientId}/exist")
    public ResponseEntity<Boolean> checkIfClientExists(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.checkIfClientExists(clientId));
    }

}
