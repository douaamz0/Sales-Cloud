package org.example.storebackend.controller;

import org.example.storebackend.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.storebackend.services.ClientService;

import java.util.List;


@RestController
@RequestMapping("StoreBackend/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @GetMapping("/{nom}")
    public List getClientByNom(@PathVariable String nom){
        return clientService.getClientByName(nom);
    }

    @PostMapping
    public Client saveClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        client.setId(id);
        return clientService.saveClient(client);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}