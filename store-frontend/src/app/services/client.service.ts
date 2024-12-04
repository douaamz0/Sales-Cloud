import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Client } from '../models/client';
@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private baseUrl = 'http://localhost:8085/SalesManagement/api/ventes/clients'; // Your Spring Boot API base URL

  constructor(private http: HttpClient) { }

  // Fetch all clients
  getClients(): Observable<any> {
    return this.http.get(this.baseUrl+"/getClients");
  }
  searchClientsByName(nom: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${nom}`);
  }

  // Fetch a single client by ID
  getClient(id: number): Observable<Client> {
    return this.http.get<Client>(`${this.baseUrl}/getClients/${id}`);
  }

  // Create a new client
  createClient(client: Client): Observable<any> {
    return this.http.post(this.baseUrl+"/create", client);
  }

  // Update a client
  updateClient(id: number, client: Client): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, client);
  }

  // Delete a client
  deleteClient(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
