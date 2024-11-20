import { Component, OnInit } from '@angular/core';
import { StatisticService } from '../../services/statistic.service';
import { Statistique } from '../../models/statistique';
import {log} from "@angular-devkit/build-angular/src/builders/ssr-dev-server";  // Importer le modèle

@Component({
  selector: 'app-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.css']
})
export class StatisticComponent implements OnInit {

  // Déclaration des variables pour stocker les données des graphiques
  public topProducts: Statistique[] = [];
  public topClients: Statistique[] = [];

  // Initialisation du service
  constructor(private statisticService: StatisticService) { }

  ngOnInit(): void {
    // Charger les données dès le début
    this.loadTopProducts();
    this.loadTopClients();
  }

  // Méthodes pour récupérer les statistiques
  loadTopProducts(): void {
    this.statisticService.getTopProducts().subscribe(
      (data: any[]) => {
        console.log('Raw Top Products:', data); // Affichez les données originales pour vérification
        this.topProducts = data.map(item => ({
          name: item.productName, // Remappez "productName" en "name"
          value: item.sales       // Remappez "sales" en "value"
        }));
        console.log('Formatted Top Products:', this.topProducts); // Vérifiez les données après transformation
      },
      (error) => {
        console.error('Erreur lors du chargement des produits les plus vendus', error);
      }
    );
  }




  loadTopClients(): void {
    this.statisticService.getTopClients().subscribe(
      (data: any[]) => {
        console.log('Raw Top Clients:', data); // Vérifiez les données initiales
        this.topClients = data.map(item => ({
          name: item.clientName, // Remappez "clientName" en "name"
          value: item.purchases  // Remappez "purchases" en "value"
        }));
        console.log('Formatted Top Clients:', this.topClients); // Vérifiez les données après transformation
      },
      (error) => {
        console.error('Erreur lors du chargement des clients les plus acheteurs', error);
      }
    );
  }


}
