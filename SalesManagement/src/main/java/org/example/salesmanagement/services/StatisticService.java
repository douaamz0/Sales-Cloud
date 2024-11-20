package org.example.salesmanagement.services;
import org.example.salesmanagement.repository.VenteRepository;
import org.springframework.stereotype.Service;
import org.example.salesmanagement.entity.Vente;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticService {
    private final VenteRepository venteRepository;


    public StatisticService(VenteRepository venteRepository) {
        this.venteRepository = venteRepository;
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
}
