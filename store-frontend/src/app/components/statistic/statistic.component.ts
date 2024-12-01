import { Component, OnInit } from '@angular/core';
import { StatisticService } from '../../services/statistic.service';
import {Produit} from "../../models/produit";

@Component({
  selector: 'app-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.css']
})
export class StatisticComponent implements OnInit {

  // Déclaration des variables pour stocker les données des graphiques
  public topProducts: { name: string, value: number }[] = [];
  public uninvoicedSalesCount: number = 0;
  public topClients: { name: string, value: number }[] = [];
  public topStockProducts!: Produit ;

  constructor(private statisticService: StatisticService) {}

  ngOnInit(): void {
    // Charger les statistiques au démarrage
    this.loadTopProducts();
    this.loadUninvoicedSales();
    this.loadTopClients();
    this.loadTopStock();

  }

  // Méthode pour récupérer les produits avec le stock le plus élevé
  loadTopProducts(): void {
    this.statisticService.getTopProducts().subscribe(
      (data: any[]) => {
        this.topProducts = data.map(item => ({
          name: item.productName, // Remappez "productName" en "name"
          value: item.sales       // Remappez "sales" en "value"
        }));
        console.log('Produits les plus stockés:', this.topProducts);
      },
      (error) => {
        console.error('Erreur lors du chargement des produits les plus stockés', error);
      }
    );
  }

  // Méthode pour récupérer le nombre de ventes non facturées
  loadUninvoicedSales(): void {
    this.statisticService.getVente().subscribe(
      (data: number) => {

        this.uninvoicedSalesCount = data;
        console.log('Nombre de ventes non facturées:', this.uninvoicedSalesCount);
      },
      (error) => {
        console.error('Erreur lors du chargement des ventes non facturées', error);
      }
    );
  }

  // Méthode pour récupérer les meilleurs clients
  loadTopClients(): void {
    this.statisticService.getTopClients().subscribe(
      (data: any[]) => {
        this.topClients = data.map(item => ({
          name: item.clientName, // Remappez "clientName" en "name"
          value: item.purchases  // Remappez "purchases" en "value"
        }));
        console.log('Clients les plus fidèles:', this.topClients);
      },
      (error) => {
        console.error('Erreur lors du chargement des clients', error);
      }
    );
  }

  // Méthode pour récupérer les produits les plus vendus


  private loadTopStock() {
    this.statisticService.getTopStock().subscribe(
      (data:Produit)=> {
        this.topStockProducts=data;

      },
    (error) => {
      console.error('Erreur lors du chargement des produits avec le grand stock', error);
    }
    )
  }
}
