import {Component, OnInit} from '@angular/core';
import {Client} from "../../models/client";
import {Produit} from "../../models/produit";
import {ClientService} from "../../services/client.service";
import {Router} from "@angular/router";
import {ProduitService} from "../../services/produit.service";

@Component({
  selector: 'app-produit',
  templateUrl: './produit.component.html',
  styleUrl: './produit.component.css'
})
export class ProduitComponent implements OnInit{
  produits: Produit[] = [];

  constructor(private produitService: ProduitService,private router:Router) {}

  ngOnInit(): void {
    this.produitService.getProduits().subscribe(data => {
      this.produits = data;
    });
  }

  editProduit(id: number) {
    this.router.navigateByUrl("/edit-produit/"+id);
  }

  deleteProduit(id: number) {
    this.produitService.deleteProduit(id).subscribe(() => {
      this.produits = this.produits.filter(client => client.id !== id);
    });
  }
}
