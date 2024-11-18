package org.example.salesmanagement.controllers;

import org.example.salesmanagement.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("SalesManagement/api/ventes/clients")
public class ClientController {
    @Autowired
    private DiscoveryClient discoveryClient;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/getClients")
    public String getClients() {
        List<ServiceInstance> instances = discoveryClient.getInstances("StoreBackend");
        if (instances != null && !instances.isEmpty()) {
            String clientAUrl = instances.get(0).getUri().toString() + "/StoreBackend/api/clients";
            return restTemplate.getForObject(clientAUrl, String.class);
        }
        return "Client A not available";
    }
    @PostMapping("/create")
    public String createClient(@RequestBody Client client) {
        // Récupération des instances du service StoreBackend
        List<ServiceInstance> instances = discoveryClient.getInstances("StoreBackend");

        if (instances != null && !instances.isEmpty()) {
            // Construction de l'URL du service StoreBackend pour l'appel POST
            String clientAUrl = instances.get(0).getUri().toString() + "/StoreBackend/api/clients";

            // Utilisation de RestTemplate pour envoyer les données avec POST
            // On attend une réponse de type Client (ou toute autre classe de réponse que vous attendez)
            Client savedClient = restTemplate.postForObject(clientAUrl, client, Client.class);

            // Retourner un message ou l'objet retourné par le service
            return "Client created: " + savedClient.toString();
        }
        return "StoreBackend service not available";
    }

    @PutMapping("/{id}")
    public String updateClient(@PathVariable Long id, @RequestBody Client client) {
        // Définir l'ID du client dans l'objet client (au cas où il n'est pas déjà défini)
        client.setId(id);

        // Récupération des instances du service StoreBackend
        List<ServiceInstance> instances = discoveryClient.getInstances("StoreBackend");

        if (instances != null && !instances.isEmpty()) {
            // Construction de l'URL du service StoreBackend pour l'appel PUT
            String clientAUrl = instances.get(0).getUri().toString() + "/StoreBackend/api/clients/" + id;

            // Utilisation de RestTemplate pour envoyer les données avec PUT
            // On envoie l'objet client et aucune réponse ne sera attendue
            restTemplate.put(clientAUrl, client);

            // Retourner un message de confirmation
            return "Client updated successfully";
        }
        return "StoreBackend service not available";
    }


}
