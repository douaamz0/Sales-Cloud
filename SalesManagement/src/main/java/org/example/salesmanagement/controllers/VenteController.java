package org.example.salesmanagement.controllers;

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

@RestController
@RequestMapping("SalesManagement/api/ventes")
public class VenteController {

    @Autowired
    private VenteRepository venteRepository;

    // Get all sales for an authenticated client
    @GetMapping
    public List<Vente> getVentesByClient(@RequestBody Client client) {
        return venteRepository.findByClientId(client.getId());
    }

    // Create a new sale
    @PostMapping
    public ResponseEntity<Vente> createVente(@RequestBody Vente vente, @RequestBody Client client) {
        vente.setClient(client);
        vente.setDate(new Date());
        return ResponseEntity.ok(venteRepository.save(vente));
    }

    // Get a specific sale by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vente> getVenteById(@PathVariable Long id, @RequestBody Client client) {
        return venteRepository.findById(id)
                .filter(vente -> vente.getClient().getId().equals(client.getId()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a sale
    @PutMapping("/{id}")
    public ResponseEntity<Vente> updateVente(@PathVariable Long id, @RequestBody Vente venteDetails, @RequestBody Client client) {
        return venteRepository.findById(id)
                .filter(vente -> vente.getClient().getId().equals(client.getId()))
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
    public ResponseEntity<Object> deleteVente(@PathVariable Long id, @RequestBody Client client) {
        return venteRepository.findById(id)
                .filter(vente -> vente.getClient().getId().equals(client.getId()))
                .map(vente -> {
                    venteRepository.delete(vente);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}