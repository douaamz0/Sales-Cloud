package org.example.storebackend.entities;

import jakarta.persistence.*;
import lombok.*;

/*Entity :permet de créer la table dans la base de donnnées */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
/*Table:personnalier le nom de la table sinon la table sera crée avec le meme nom que la classe*/
@Table(name="produits")
public class Produit {
    //Id:primary key de la table produits
    @Id
//Column:si vous voullez personnaliser le nom d'une colonne
    @Column(name="id")
//la Valeur de la colonne id est auto_increment
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    //libelle sera le nom de la colonne qui correspond le nom dans la classe produit
    @Column(name="libelle")
    private String nom;
    //une colonne marque sera crée dans la table produits
    private String marque;
    //la colonne prix
    private float prix;
    //la colonne qteStock
    private int qteStock;

}