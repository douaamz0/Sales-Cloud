package org.example.salesmanagement.services;
import org.example.salesmanagement.entity.Produit;
import org.example.salesmanagement.repository.ProduitRepository;
import org.example.salesmanagement.repository.VenteRepository;
import org.springframework.stereotype.Service;
import org.example.salesmanagement.entity.Vente;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
public class StatisticService {
    private final VenteRepository venteRepository;
    private ProduitRepository produitRepository;


    public StatisticService(VenteRepository venteRepository, ProduitRepository produitRepository) {
        this.venteRepository = venteRepository;
        this.produitRepository=produitRepository;
    }

    // Produits les plus vendus
    public List<Object> getTopSellingProducts() {
        return venteRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        vente -> vente.getProduit().getNom(),
                        Collectors.summingInt(Vente::getQuantite)
                ))
                .entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue())) // Trier par quantité décroissante
                .map(entry -> new Object() {
                    public String productName = entry.getKey();
                    public Integer sales = entry.getValue();
                })
                .collect(Collectors.toList());
    }

    // Clients qui achètent le plus
    public List<Object> getTopBuyingClients() {
        return venteRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        vente -> vente.getClient().getNom()+" "+vente.getClient().getPrenom(),
                        Collectors.summingInt(Vente::getQuantite)
                ))
                .entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue())) // Trier par quantité décroissante
                .map(entry -> new Object() {
                    public String clientName = entry.getKey();
                    public Integer purchases = entry.getValue();
                })
                .collect(Collectors.toList());
    }

    public Produit getTopStock() {
        List<Produit> produits = produitRepository.findAll();

        // Vérifier si la liste est vide
        if (produits == null || produits.isEmpty()) {
            return null; // ou lever une exception selon votre besoin
        }

        // Trouver le produit avec le stock le plus élevé
        Produit topProduit = produits.stream()
                .max(Comparator.comparingInt(Produit::getQteStock))
                .orElse(null);

        return topProduit;
    }

    public long countUninvoicedSales() {
        List<Vente> ventes = venteRepository.findAll();

        if (ventes == null || ventes.isEmpty()) {
            return 0; // Si aucune vente n'existe
        }

        return ventes.stream()
                .filter(vente -> !vente.isInvoiced()) // Filtrer les ventes où isInvoiced est false
                .count(); // Compter les ventes correspondantes
    }


}
