package org.example.salesmanagement.controllers;

import org.example.salesmanagement.entity.Produit;
import org.example.salesmanagement.services.StatisticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("SalesManagement/api/statistics")
public class StatistiqueController {

    private final StatisticService statistiqueService;

    public StatistiqueController(StatisticService statistiqueService) {
        this.statistiqueService = statistiqueService;
    }

    // Endpoint pour les produits les plus vendus
    @GetMapping("/top-products")
    public List<Object> getTopSellingProducts() {
        return statistiqueService.getTopSellingProducts();
    }

    // Endpoint pour les clients qui achètent le plus
    @GetMapping("/top-clients")
    public List<Object> getTopBuyingClients() {
        return statistiqueService.getTopBuyingClients();
    }

    @GetMapping("/top-stock")
   public Produit getTopStock(){
        return statistiqueService.getTopStock();
    }
    @GetMapping("/ventes")
    public Long getventes(){
        return statistiqueService.countUninvoicedSales();
    }
}
