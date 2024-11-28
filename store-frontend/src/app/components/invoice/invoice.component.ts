import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { InvoiceService } from "../../services/invoice.service";
import { jsPDF } from "jspdf";
import {Invoice} from "../../models/Invoice";
import {Vente} from "../../models/vente";

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css']
})
export class InvoiceComponent implements OnInit {
  invoice!: Invoice;
  noSalesMessage: string = '';

  constructor(
    private invoiceService: InvoiceService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const clientId = this.route.snapshot.params['id'];
    this.invoiceService.getInvoice(clientId).subscribe(
      (data) => {
        this.invoice = data;
        if (!this.invoice.sales || this.invoice.sales.length === 0) {
          this.noSalesMessage = 'Aucune facture à générer pour ce client.';
        }
        console.log('Invoice:', this.invoice);
      },
      error => {
        if (error.status === 404) {
          this.noSalesMessage = error.error;  // Message d'erreur venant du backend
        }
      }
    );
  }

  // Fonction pour générer le PDF de la facture
  generatePDF(): void {
    const doc = new jsPDF();
    const today = new Date();

// Formate la date (par exemple : JJ/MM/AAAA)
    const formattedDate = today.toLocaleDateString('fr-FR');

    // Ajouter un titre
    doc.setFontSize(18);


    // Ajouter les détails de la facture
    doc.setFontSize(12);
    doc.text("Facture", 105, 20, { align: "center" }); // Centrer le titre
    doc.text(`Client: ${this.invoice.clientName} ${this.invoice.clientPrenom}`, 20, 40);
    doc.text(`Date: ${formattedDate}`, 20, 50);

// Détails des ventes
    doc.text("Détails des ventes :", 20, 60);
    let yPosition = 70; // Position de départ pour les lignes des ventes
    this.invoice.sales.forEach((sale:Vente) => {
      doc.text(` Produit: ${sale.produit.nom}, Quantité: ${sale.quantite}, Prix: ${sale.produit.prix} MAD`, 20, yPosition);
      yPosition += 10; // Espacement entre les lignes
    });

// Total de la facture
    doc.text(`Montant total : ${this.invoice.totalAmount} MAD`, 20, yPosition + 10);

    // Ajouter d'autres informations de la facture ici...

    // Télécharger le PDF
    doc.save("invoice.pdf");
  }
}
