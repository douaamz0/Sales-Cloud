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
@RequestMapping("StoreBackend/api/produits")
public class ProduitController {
    /*le repository de l'entité produit pour gérer les transaction dans la base de données*/
    @Autowired
    private ProduitRepository produitRepository;


    @PostMapping("/add")
    public Produit ajouter(@RequestBody Produit produit) {
        return produitRepository.save(produit);
    }

    @GetMapping("/")
    public List<Produit> produits() {
        return (List<Produit>) produitRepository.findAll();
    }

    //Details
    @GetMapping("/{id}")
    /*@PathVariable("id"):recupérer l'id passé en paramètre*/
    public Produit detail(@PathVariable("id") int id) {
        /*chercher dans la table produit avec l'id passé en paramètre*/
        Optional<Produit> pp = produitRepository.findById(id);
        /*s'il existe un produit on l'envoi avec le status 200*/
        if (pp.isPresent()) {
            Produit p = pp.get();
            return p;
        }

        return null;
    }

    //Supprimer
    @DeleteMapping("/{id}")
    public void supprimer(@PathVariable("id") int id) {
        try {
            /*Supprimer le produit de la table produit*/
            produitRepository.deleteById(id);
            /*retourne le status 200*/

        } catch (Exception e) {


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
    public Produit modifier(@PathVariable("id") int id,
                            @RequestBody Produit produit) {

        produit.setId(id);
        return produitRepository.save(produit);
    }

}


