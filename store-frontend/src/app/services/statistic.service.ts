import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Exemple de structure des données pour les statistiques
interface Statistique {
  name: string;
  value: number;
}

@Injectable({
  providedIn: 'root'
})
export class StatisticService {

  private apiUrl = 'http://localhost:8085/SalesManagement/api/statistics'; // Remplacez par l'URL de votre API backend

  constructor(private http: HttpClient) { }

  // Récupérer les produits les plus vendus
  getTopProducts(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/top-products`);
  }

  // Récupérer les clients qui achètent le plus
  getTopClients(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/top-clients`);
  }
}
