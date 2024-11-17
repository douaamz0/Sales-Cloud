import { Component, OnInit } from '@angular/core';
import { VenteService } from '../../services/vente.service';
import { Vente } from '../../models/vente';
import { Router, ActivatedRoute } from '@angular/router';
import {ProduitService} from "../../services/produit.service";


@Component({
  selector: 'app-vente-form',
  templateUrl: './add-edit-vente.component.html',
  styleUrls: ['./add-edit-vente.component.css']
})
export class VenteFormComponent implements OnInit {

 public  vente: Vente = new Vente(); // The sale being created/edited
 public produits: any[] = []; // List of products for selection in the form
 public editMode: boolean = false; // Flag to distinguish between add and edit modes

  constructor(
    private venteService: VenteService,
    private produitService: ProduitService,
    protected router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.loadProduits(); // Load available products for the sale form

    // Check if we're in edit mode (if there's an ID in the route params)
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editMode = true;
      this.venteService.getVenteById(Number(id)).subscribe(
        (data: Vente) => {
          this.vente = data;
        },
        error => {
          console.error('Error loading sale', error);
        }
      );
    }
  }

  // Load available products to populate the dropdown
  loadProduits() {
    this.produitService.getProduits().subscribe(
      (data: any[]) => {
        this.produits = data;
      },
      error => {
        console.error('Error loading products', error);
      }
    );
  }

  // Save the sale (either creating or updating)
  saveVente() {
    if (this.editMode) {
      this.venteService.updateVente(this.vente.id, this.vente).subscribe(
        () => {
          this.router.navigate(['/ventes']); // Redirect to sales list after successful save
        },
        error => {
          console.error('Error updating sale', error);
        }
      );
    } else {
      this.venteService.createVente(this.vente).subscribe(
        () => {
          this.router.navigate(['/ventes']); // Redirect to sales list after successful save
        },
        error => {
          console.error('Error creating sale', error);
        }
      );
    }
  }
}
