package org.example.salesmanagement.repository;

import org.example.salesmanagement.entity.Produit;
import org.example.salesmanagement.entity.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

}
