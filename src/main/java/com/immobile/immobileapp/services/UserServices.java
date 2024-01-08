package com.immobile.immobileapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.immobile.immobileapp.doa.entities.User;

public interface UserServices {
    UserDetailsService userDetailsService();

    public Optional<User> getUserById(Long id);

    public Optional<User> findUserById(Long id);

    public List<User> findAllUsers();

    // register new user
    public Optional<User> getUsersByImmeubleId(User client);

    public void saveUser(User user);

    User getUserByUsername(String name);
}
