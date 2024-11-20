import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { InvoiceService } from "../../services/invoice.service";
import { jsPDF } from "jspdf";

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css']
})
export class InvoiceComponent implements OnInit {
  invoice: any;
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

    // Ajouter un titre
    doc.setFontSize(18);
    doc.text("Invoice", 20, 20);

    // Ajouter les détails de la facture
    doc.setFontSize(12);
    doc.text(`Client: ${this.invoice.clientName}`, 20, 40);
    doc.text(`Date: ${this.invoice.sales.date}`, 20, 50);
    doc.text(`Total Amount: ${this.invoice.totalAmount} EUR`, 20, 60);

    // Ajouter d'autres informations de la facture ici...

    // Télécharger le PDF
    doc.save("invoice.pdf");
  }
}
