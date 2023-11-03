package com.usco.ingenieria.software.repositories;

import java.util.Optional;

import com.usco.ingenieria.software.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username); 
}
