package org.example.authservice.repository;

import org.example.authservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}