import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Vente} from "../models/vente";

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  private baseUrl = 'http://localhost:8085/SalesManagement/api/invoices'; // Your Spring Boot API base URL

  constructor(private http: HttpClient) { }

  getInvoice(clientId: number): Observable<Vente[]> {
    return this.http.get<Vente[]>(`${this.baseUrl}/client/${clientId}`);
  }



}
