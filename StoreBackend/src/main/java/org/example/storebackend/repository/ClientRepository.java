package org.example.storebackend.repository;

import org.example.storebackend.entities.Client;
import org.example.storebackend.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

   public List findByNom(String nom);
}