package org.example.salesmanagement.controllers;

import org.example.salesmanagement.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @GetMapping("/{nom}")
    public String getClientsByNom(@PathVariable String nom) {
        List<ServiceInstance> instances = discoveryClient.getInstances("StoreBackend");
        if (instances != null && !instances.isEmpty()) {
            String clientAUrl = instances.get(0).getUri().toString() + "/StoreBackend/api/clients/"+nom;
            return restTemplate.getForObject(clientAUrl, String.class);
        }
        return "Client A not available";
    }

    @GetMapping("/getClients/{id}")
    public ResponseEntity<?> getClient(@PathVariable long id) {
        // Récupération des instances du service StoreBackend
        List<ServiceInstance> instances = discoveryClient.getInstances("StoreBackend");

        if (instances != null && !instances.isEmpty()) {
            // Construction de l'URL du service StoreBackend
            String clientAUrl = instances.get(0).getUri().toString() + "/StoreBackend/api/clients/" + id;

            try {
                // Appel du service StoreBackend pour récupérer le client
                Client client = restTemplate.getForObject(clientAUrl, Client.class);
                return ResponseEntity.ok(client);
            } catch (HttpClientErrorException | HttpServerErrorException ex) {
                // Gestion des erreurs retournées par l'API distante
                return ResponseEntity.status(ex.getStatusCode())
                        .body("Erreur API distante : " + ex.getResponseBodyAsString());
            } catch (Exception ex) {
                // Gestion des autres erreurs
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erreur interne : " + ex.getMessage());
            }
        }

        // Cas où aucune instance du service StoreBackend n'est trouvée
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("StoreBackend service not available");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        // Récupération des instances du service StoreBackend
        List<ServiceInstance> instances = discoveryClient.getInstances("StoreBackend");

        if (instances != null && !instances.isEmpty()) {
            // Construction de l'URL du service StoreBackend pour l'appel POST
            String clientAUrl = instances.get(0).getUri().toString() + "/StoreBackend/api/clients";

            // Utilisation de RestTemplate pour envoyer les données avec POST
            // On attend une réponse de type Client (ou toute autre classe de réponse que vous attendez)
            Client savedClient = restTemplate.postForObject(clientAUrl, client, Client.class);

            // Retourner l'objet créé avec le statut HTTP 200
            return ResponseEntity.ok(savedClient);
        }

        // Retourner une erreur 503 (Service Unavailable) si StoreBackend n'est pas disponible
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("StoreBackend service not available");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody Client client) {
        client.setId(id);

        List<ServiceInstance> instances = discoveryClient.getInstances("StoreBackend");

        if (instances != null && !instances.isEmpty()) {
            String clientAUrl = instances.get(0).getUri().toString() + "/StoreBackend/api/clients/" + id;

            try {
                restTemplate.put(clientAUrl, client);

                // Retourner une réponse JSON
                Map<String, String> response = new HashMap<>();
                response.put("message", "Client updated successfully");
                return ResponseEntity.ok(response);
            } catch (HttpClientErrorException | HttpServerErrorException ex) {
                return ResponseEntity.status(ex.getStatusCode())
                        .body(Map.of("error", ex.getResponseBodyAsString()));
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", ex.getMessage()));
            }
        }

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("error", "StoreBackend service not available"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        // Récupération des instances du service StoreBackend
        List<ServiceInstance> instances = discoveryClient.getInstances("StoreBackend");

        if (instances != null && !instances.isEmpty()) {
            // Construction de l'URL du service StoreBackend pour l'appel DELETE
            String clientAUrl = instances.get(0).getUri().toString() + "/StoreBackend/api/clients/" + id;

            try {
                // Utilisation de RestTemplate pour envoyer la requête DELETE
                restTemplate.delete(clientAUrl);

                // Retourner une réponse JSON
                Map<String, String> response = new HashMap<>();
                response.put("message", "Client deleted successfully");
                return ResponseEntity.ok(response);
            } catch (HttpClientErrorException | HttpServerErrorException ex) {
                // Gestion des erreurs HTTP
                return ResponseEntity.status(ex.getStatusCode())
                        .body(Map.of("error", ex.getResponseBodyAsString()));
            } catch (Exception ex) {
                // Gestion des erreurs générales
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", ex.getMessage()));
            }
        }

        // Retourner une erreur 503 si le service n'est pas disponible
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("error", "StoreBackend service not available"));
    }




}
