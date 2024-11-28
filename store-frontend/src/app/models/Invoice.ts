import {Vente} from "./vente";

export class Invoice {
  clientName!: string;
  clientPrenom!: string;
  sales!: Vente[];
  totalAmount!: number;
}
