import { Component, OnInit } from '@angular/core';

import { Client } from '../../models/client';
import { ClientService } from '../../services/client.service';
import {Router} from "@angular/router";
@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  clients: Client[] = [];

  constructor(private clientService: ClientService,private router:Router) {}

  ngOnInit(): void {
    this.clientService.getClients().subscribe(data => {
      this.clients = data;
    });
  }

  deleteClient(id: number): void {
    this.clientService.deleteClient(id).subscribe(() => {
      this.clients = this.clients.filter(client => client.id !== id);
    });
  }

  editClient(id:number):void {
    this.router.navigateByUrl("/edit-client/"+id);
  }

  generateInvoice(id: number) {
    this.router.navigate(['/invoice', id]);
  }
}

