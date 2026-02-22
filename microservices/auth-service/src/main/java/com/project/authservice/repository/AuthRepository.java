package com.project.authservice.repository;

import com.project.authservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on the {@link User} entity.
 * Extends {@link JpaRepository} to provide basic persistence functionality.
 */
@Repository
public interface AuthRepository extends JpaRepository<User, Long> {

    /**
     * Finds a {@link User} entity by its username.
     *
     * @param username The username of the user to be found.
     * @return An {@link Optional} containing the found user, or empty if no user exists with the given username.
     */
    Optional<User> findByUsername(String username);
}
