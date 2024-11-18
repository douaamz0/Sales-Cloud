import { Component } from '@angular/core';
import {Client} from "../../models/client";
import {Produit} from "../../models/produit";
import {ProduitService} from "../../services/produit.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-produit',
  templateUrl: './add-produit.component.html',
  styleUrl: './add-produit.component.css'
})
export class AddProduitComponent {
  produit: Produit = new Produit();

  constructor(private produitService:ProduitService,private router:Router) {
  }
  addClient() {
    this.produitService.createProduit(this.produit).subscribe(() => {
      this.router.navigateByUrl("/produits");
    });
  }
}
