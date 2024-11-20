import { Component, OnInit } from '@angular/core';
import { StatisticService } from '../../services/statistic.service';
import { Statistique } from '../../models/statistique';  // Importer le modèle

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
      (data: Statistique[]) => {
        console.log('Top Products:', data); // Vérifiez les données dans la console
        this.topProducts = data.map(item => ({
          name: item.name, // Le nom de l'élément
          value: item.value // La valeur numérique associée
        }));
      },
      (error) => {
        console.error('Erreur lors du chargement des produits les plus vendus', error);
      }
    );
  }



  loadTopClients(): void {
    this.statisticService.getTopClients().subscribe(
      (data: any[]) => {
        // Reformater les données pour ngx-charts
        this.topClients = data.map(item => ({
          name: item.clientName, // Nom du client
          value: item.purchases  // Nombre d'achats
        }));
      },
      (error) => {
        console.error('Erreur lors du chargement des clients les plus acheteurs', error);
      }
    );
  }


}
