package org.example.storebackend.controller;

import java.util.List;
import java.util.Optional;

import org.example.storebackend.entities.Produit;
import org.example.storebackend.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





/*Création du controller de type RestController*/
@RestController
/*la route principale pour consommer l'api du controlleur*/
@RequestMapping("api/produits/")
public class ProduitController {
    /*le repository de l'entité produit pour gérer les transaction dans la base de données*/
    @Autowired
    private ProduitRepository produitRepository;


    /*@RequestBody :permet de récupéer un objet json puis le convertir à un objet de type produit
    {
      "id":"5",
      "nom":"TV",
      "marque":"Sumsung",
      "prix":1000,
      "qteStock":5
      }
  */
    @PostMapping("/add")
    public ResponseEntity ajouter(@RequestBody Produit produit) {
        try {
            /*Sauvegarder le produit dans la table produits*/
            produitRepository.save(produit);
            /*status:201*/
            return new ResponseEntity<>(produit, HttpStatus.CREATED);
        } catch (Exception e) {
            /*Erreur 500*/
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Produit>> produits() {
        List<Produit> listeProduits= (List<Produit>) produitRepository.findAll();
        return new ResponseEntity<>(listeProduits, HttpStatus.OK);
    }

    //Details
    @GetMapping("/{id}")
    /*@PathVariable("id"):recupérer l'id passé en paramètre*/
    public ResponseEntity<Produit> detail(@PathVariable("id") int id) {
        /*chercher dans la table produit avec l'id passé en paramètre*/
        Optional<Produit>pp=produitRepository.findById(id);
        /*s'il existe un produit on l'envoi avec le status 200*/
        if(pp.isPresent())
        {
            Produit p=pp.get();
            return new ResponseEntity<>(p, HttpStatus.OK);
        }
        /*s'il n'existe pas un produit avec cet id on envoi 404 */
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Supprimer
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> supprimer(@PathVariable("id") int id) {
        try {
            /*Supprimer le produit de la table produit*/
            produitRepository.deleteById(id);
            /*retourne le status 200*/
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
//s'il le produit n'existe pas retourne status=204
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //vider la table Produit
    @DeleteMapping("/vider")
    public ResponseEntity<HttpStatus> vider() {
        try {
            /*truncate produit*/
            produitRepository.deleteAll();
            /*retourne status :200*/
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            /*s'il erreur retourne status 500*/
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //modifier
    @PutMapping("/{id}")
    public ResponseEntity<Produit> modifier(@PathVariable("id") int id,
                                            @RequestBody Produit produit) {
/*afin d'appeler modifier on doit envoyer:
1.{id} :l'id du produit à modifier
2.@RequestBody Produit produit :les nouvelle valeur à insérer
 {
"id": 5,
"nom": "Nouveau Nom",
"marque": "Nouvelle marque",
"prix": 100.0,
"qteStock": 4
}
  */
        if(produitRepository.save(produit)!=null)
        {
            /*si le produit est modifié avec succés*/
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            /*si il y a erreur de modification*/
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


