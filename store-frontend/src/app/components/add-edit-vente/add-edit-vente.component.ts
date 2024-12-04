import { Component, OnInit } from '@angular/core';
import { VenteService } from '../../services/vente.service';
import { ProduitService } from '../../services/produit.service';
import { ClientService } from '../../services/client.service';
import { Vente } from '../../models/vente';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-vente-form',
  templateUrl: './add-edit-vente.component.html',
  styleUrls: ['./add-edit-vente.component.css']
})
export class AddEditVenteComponent implements OnInit {
  public vente: Vente = new Vente(); // The sale being created/edited
  public produits: any[] = []; // List of products for selection in the form
  public clients: any[] = []; // List of clients for selection in the form
  public editMode: boolean = false; // Flag to distinguish between add and edit mode

  constructor(
    private venteService: VenteService,
    private produitService: ProduitService,
    private clientService: ClientService,
    public router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadProduits(); // Load available products for the sale form
    this.loadClients(); // Load available clients for the sale form

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editMode = true;
      this.venteService.getVenteById(Number(id)).subscribe(
        (data: Vente) => {
          this.vente = data;
          this.vente.produit=data.produit;
          this.vente.client=data.client;
        },
        error => {
          console.error('Error loading sale', error);
        }
      );
    }
  }

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

  loadClients() {
    this.clientService.getClients().subscribe(
      (data: any[]) => {
        this.clients = data;
      },
      error => {
        console.error('Error loading clients', error);
      }
    );
  }

  saveVente() {
    if (this.editMode) {
      this.venteService.updateVente(this.vente.id, this.vente).subscribe(
        () => this.router.navigate(['/ventes']),
        error => console.error('Error updating sale', error)
      );
    } else {
      const venteRequest = {
        vente: this.vente,
        client: this.vente.client,
      };

      this.venteService.createVente(venteRequest).subscribe(
        () => this.router.navigate(['/ventes']),
        error => console.error('Error creating sale', error)
      );
    }
  }
}
