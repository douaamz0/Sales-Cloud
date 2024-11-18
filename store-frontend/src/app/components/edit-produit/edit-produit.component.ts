import {Component, OnInit} from '@angular/core';
import {Produit} from "../../models/produit";
import {ClientService} from "../../services/client.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ProduitService} from "../../services/produit.service";

@Component({
  selector: 'app-edit-produit',
  templateUrl: './edit-produit.component.html',
  styleUrl: './edit-produit.component.css'
})
export class EditProduitComponent implements OnInit {
  produit: Produit = new Produit();

  constructor(
    private produitService: ProduitService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  updateClient() {
    const id = this.route.snapshot.params['id'];
    this.produitService.updateProduit(id, this.produit).subscribe(() => {
      this.router.navigateByUrl("/produits");
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    this.produitService.getProduit(id).subscribe(data => {
      this.produit = data;
    });
  }
}
