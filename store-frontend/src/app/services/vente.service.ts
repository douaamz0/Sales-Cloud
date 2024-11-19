import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vente } from '../models/vente';
import {Client} from "../models/client";

@Injectable({
  providedIn: 'root'
})
export class VenteService {

  private apiUrl = 'http://localhost:8085/SalesManagement/api/ventes';

  constructor(private http: HttpClient) { }

  // Get all sales for the authenticated client
  getVentes(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }

  // Create a new sale
  createVente(venteRequest:any): Observable<any> {
    return this.http.post<any>(this.apiUrl, venteRequest);
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
