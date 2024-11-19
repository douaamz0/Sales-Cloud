import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { VenteService } from '../../services/vente.service';
import { Vente } from '../../models/vente';

@Component({
  selector: 'app-vente',
  templateUrl: './vente.component.html',
  styleUrls: ['./vente.component.css']
})
export class VenteComponent implements OnInit {

  ventes: Vente[] = [];

  constructor(private venteService: VenteService, private router: Router) { }

  ngOnInit(): void {
    this.loadVentes();
  }

  // Load all sales
  loadVentes() {
    this.venteService.getVentes().subscribe(
      (data: Vente[]) => {
        this.ventes = data;
      },
      error => {
        console.error('Error loading sales', error);
      }
    );
  }

  // Delete a sale
  deleteVente(id: number) {
    this.venteService.deleteVente(id).subscribe(
      () => {
        this.loadVentes(); // Reload sales after deletion
      },
      error => {
        console.error('Error deleting sale', error);
      }
    );
  }

  // Redirect to add-edit-vente for editing
  editVente(id: number) {
    this.router.navigateByUrl('vente-edit/'+id); // Pass the sale ID as a route parameter
  }

  // Redirect to add-edit-vente for creating a new sale

}
