package org.example.salesmanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.salesmanagement.entity.Client;
import org.example.salesmanagement.entity.Produit;
import org.example.salesmanagement.entity.Vente;
import org.example.salesmanagement.repository.ClientRepository;
import org.example.salesmanagement.repository.ProduitRepository;
import org.example.salesmanagement.repository.VenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Convertir les données de la requête en objets Vente et Client
            Vente vente = objectMapper.convertValue(requestBody.get("vente"), Vente.class);
            Client client = objectMapper.convertValue(requestBody.get("client"), Client.class);

            Produit produit = vente.getProduit();

            // Vérifier si le produit existe et si le stock est suffisant
            if (produit == null || produit.getQteStock() < vente.getQuantite()) {
                return ResponseEntity.badRequest().body(null);
            }

            // Réduire la quantité en stock du produit
            produit.setQteStock(produit.getQteStock() - vente.getQuantite());
            produitRepository.save(produit);

            // Associer le client à la vente et définir la date
            vente.setClient(client);
            vente.setDate(new Date());

            // Sauvegarder la vente
            Vente savedVente = venteRepository.save(vente);

            return ResponseEntity.ok(savedVente);
        } catch (Exception e) {
            // Gérer les erreurs inattendues
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
                    Produit produit = vente.getProduit();

                    // Récupérer la quantité initiale de la vente
                    int ancienneQuantite = vente.getQuantite();

                    // Mettre à jour la vente
                    vente.setProduit(venteDetails.getProduit());
                    vente.setQuantite(venteDetails.getQuantite());
                    vente.setDate(venteDetails.getDate());

                    // Calculer la différence de quantité
                    int differenceQuantite = venteDetails.getQuantite() - ancienneQuantite;

                    // Adapter la quantité en stock du produit
                    produit.setQteStock(produit.getQteStock() - differenceQuantite);
                    produitRepository.save(produit);

                    // Sauvegarder la vente mise à jour
                    return ResponseEntity.ok(venteRepository.save(vente));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    // Delete a sale
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVente(@PathVariable Long id) {
        return venteRepository.findById(id)
                .map(vente -> {
                    Produit produit = vente.getProduit();

                    // Ajouter la quantité de la vente supprimée au stock
                    produit.setQteStock(produit.getQteStock() + vente.getQuantite());

                    // Sauvegarder les modifications du produit
                    produitRepository.save(produit);

                    // Supprimer la vente
                    venteRepository.delete(vente);

                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}