package com.example.ttsiku.repository;

import com.example.ttsiku.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @Query("select distinct u from User u left join fetch u.roles where u.username = :username")
    Optional<User> findByUsernameWithRoles(@Param("username") String username);
}