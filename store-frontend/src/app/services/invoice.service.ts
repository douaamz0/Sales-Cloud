import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Invoice} from "../models/Invoice";

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  private baseUrl = 'http://localhost:8085/SalesManagement/api/invoices'; // Your Spring Boot API base URL

  constructor(private http: HttpClient) { }

  getInvoice(clientId: number): Observable<Invoice> {
    return this.http.get<Invoice>(`${this.baseUrl}/client/${clientId}`);
  }



}
