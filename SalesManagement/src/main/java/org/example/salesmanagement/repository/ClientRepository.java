package org.example.salesmanagement.repository;

import org.example.salesmanagement.entity.Client;
import org.example.salesmanagement.entity.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
