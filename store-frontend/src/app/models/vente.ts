import {Client} from "./client";
import {Produit} from "./produit";

export class Vente {
  id!: number;
  client!: Client;
  produit!: Produit;
  date!: Date;
  quantite!: number;
  isInvoiced!:boolean;

}
