package org.example.storebackend.repository;

import org.example.storebackend.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends CrudRepository<Produit,Integer> {
/* l'entité à gérer
Integer :le type de la clé primaire id int ;>

les méthodes générique:
save(produit)
findById(id)
findAll()
finByNom(nom)
findByMarque(marque)
findByPrixAndMarque(prix,marque)
findByPrixOrMarque(prix,marque)
....
deleteById(id)
deleteAll()
...
*/
}