import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vente } from '../models/vente';

@Injectable({
  providedIn: 'root'
})
export class VenteService {

  private apiUrl = 'http://localhost:8080/api/ventes';

  constructor(private http: HttpClient) { }

  // Get all sales for the authenticated client
  getVentes(): Observable<Vente[]> {
    return this.http.get<Vente[]>(this.apiUrl);
  }

  // Create a new sale
  createVente(vente: Vente): Observable<Vente> {
    return this.http.post<Vente>(this.apiUrl, vente);
  }

  // Get a sale by ID
  getVenteById(id: number): Observable<Vente> {
    return this.http.get<Vente>(`${this.apiUrl}/${id}`);
  }

  // Update a sale
  updateVente(id: number, vente: Vente): Observable<Vente> {
    return this.http.put<Vente>(`${this.apiUrl}/${id}`, vente);
  }

  // Delete a sale
  deleteVente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
