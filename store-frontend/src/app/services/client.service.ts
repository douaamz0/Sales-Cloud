import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Client } from '../models/client';
@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private baseUrl = 'http://localhost:8085/StoreBackend/api/clients'; // Your Spring Boot API base URL

  constructor(private http: HttpClient) { }

  // Fetch all clients
  getClients(): Observable<any> {
    return this.http.get(this.baseUrl);
  }

  // Fetch a single client by ID
  getClient(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  // Create a new client
  createClient(client: Client): Observable<any> {
    return this.http.post(this.baseUrl, client);
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
