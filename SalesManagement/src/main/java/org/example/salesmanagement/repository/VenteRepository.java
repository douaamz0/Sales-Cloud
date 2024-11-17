package org.example.salesmanagement.repository;

import org.example.salesmanagement.entity.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {
    List<Vente> findByClientId(Long clientId); // Find sales by client ID
}
