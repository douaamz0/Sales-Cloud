import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Produit } from '../models/produit';
@Injectable({
  providedIn: 'root'
})
export class ProduitService {

  private baseUrl = 'http://localhost:8085/SalesManagement/api/ventes/produits'; // Your Spring Boot API base URL

  constructor(private http: HttpClient) { }

  // Fetch all clients
  getProduits(): Observable<any> {
    return this.http.get(this.baseUrl+"/getProduits");
  }

  // Fetch a single client by ID
  getProduit(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/getProduits/${id}`);
  }

  // Create a new client
  createProduit(produit: Produit): Observable<any> {
    return this.http.post(this.baseUrl+"/create", produit);
  }

  // Update a client
  updateProduit(id: number, produit: Produit): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, produit);
  }

  // Delete a client
  deleteProduit(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
