package com.proyecto.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.login.model.User;

public interface RepositoryUser extends JpaRepository<User, Long> {
    Optional <User> findByUsername(String username);
}
