package org.example.salesmanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.salesmanagement.entity.Client;
import org.example.salesmanagement.entity.Vente;
import org.example.salesmanagement.repository.ClientRepository;
import org.example.salesmanagement.repository.ProduitRepository;
import org.example.salesmanagement.repository.VenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("SalesManagement/api/ventes")
public class VenteController {

    @Autowired
    private VenteRepository venteRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProduitRepository produitRepository;

    // Get all sales for an authenticated client
    @GetMapping("/by-client")
    public List<Vente> getVentesByClient(@RequestBody Client client) {
        return venteRepository.findByClientId(client.getId());
    }

    @GetMapping
    public List<Vente> getVentes() {
        return venteRepository.findAll();
    }
    // Create a new sale
    @PostMapping
    public ResponseEntity<Vente> createVente(@RequestBody Map<String, Object> requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        Vente vente = objectMapper.convertValue(requestBody.get("vente"), Vente.class);
        Client client = objectMapper.convertValue(requestBody.get("client"), Client.class);
        vente.setClient(client);
        vente.setDate(new Date());
        return ResponseEntity.ok(venteRepository.save(vente));
    }


    // Get a specific sale by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vente> getVenteById(@PathVariable Long id) {
        return venteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a sale
    @PutMapping("/{id}")
    public ResponseEntity<Vente> updateVente(@PathVariable Long id, @RequestBody Vente venteDetails) {
        return venteRepository.findById(id)
                .map(vente -> {
                    vente.setProduit(venteDetails.getProduit());
                    vente.setQuantite(venteDetails.getQuantite());
                    vente.setDate(venteDetails.getDate());
                    return ResponseEntity.ok(venteRepository.save(vente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a sale
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVente(@PathVariable Long id) {
        return venteRepository.findById(id)
                .map(vente -> {
                    venteRepository.delete(vente);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}