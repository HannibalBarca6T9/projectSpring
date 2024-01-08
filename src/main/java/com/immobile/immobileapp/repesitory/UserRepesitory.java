package com.immobile.immobileapp.repesitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.immobile.immobileapp.doa.entities.User;

public interface UserRepesitory extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findUserById(Long id);

    User findByUsername(String name);
}
