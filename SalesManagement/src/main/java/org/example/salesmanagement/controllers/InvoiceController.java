package org.example.salesmanagement.controllers;

import org.example.salesmanagement.dto.InvoiceResponse;
import org.example.salesmanagement.entity.Vente;
import org.example.salesmanagement.repository.VenteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("SalesManagement/api/invoices")
public class InvoiceController {

    private final VenteRepository venteRepository;

    public InvoiceController(VenteRepository venteRepository) {
        this.venteRepository = venteRepository;
    }

    @GetMapping("/client/{clientId}")
    public InvoiceResponse generateInvoice(@PathVariable Long clientId) {
        // Récupérer les ventes du client
        List<Vente> sales = venteRepository.findByClientId(clientId);

        // Filtrer les ventes marquées comme à facturer
        List<Vente> salesToInvoice = sales.stream()
                .filter(vente -> !vente.isInvoiced())  // Inclure seulement les ventes qui ne sont pas encore facturées
                .collect(Collectors.toList());

        // Vérifier si la liste des ventes à facturer est vide
        if (salesToInvoice.isEmpty()) {
            // Si aucune vente à facturer, lancer l'exception personnalisée
            throw new NoSalesToInvoiceException("Aucune vente à facturer pour ce client.");
        }

        // Calculer le total
        double totalAmount = salesToInvoice.stream()
                .mapToDouble(Vente::getTotalPrice)
                .sum();

        // Construire la réponse
        InvoiceResponse response = new InvoiceResponse();
        response.setClientName(salesToInvoice.get(0).getClient().getNom());
        response.setClientPrenom(salesToInvoice.get(0).getClient().getPrenom());
        response.setSales(salesToInvoice);
        response.setTotalAmount(totalAmount);

        for (Vente vente : sales) {
            vente.setInvoiced(true);  // Marquer la vente comme facturée
        }
        venteRepository.saveAll(sales);
        // Mettre à jour les ventes après la facturation


        return response;
    }


    // Exception personnalisée pour l'absence de ventes à facturer
    public static class NoSalesToInvoiceException extends RuntimeException {
        public NoSalesToInvoiceException(String message) {
            super(message);
        }
    }

    // Gestionnaire d'exception pour l'exception NoSalesToInvoiceException
    @ExceptionHandler(NoSalesToInvoiceException.class)
    public ResponseEntity<String> handleNoSalesToInvoiceException(NoSalesToInvoiceException ex) {
        // Retourner une réponse d'erreur avec un code HTTP 404 Not Found et le message d'exception
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
