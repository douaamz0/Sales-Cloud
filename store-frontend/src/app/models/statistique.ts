export class Statistique {
  name: string;   // Nom de l'élément statistique, par exemple, le nom du produit ou du client
  value: number;  // Valeur associée à cet élément, par exemple, le nombre de ventes ou le montant total acheté

  constructor(name: string, value: number) {
    this.name = name;
    this.value = value;
  }
}
