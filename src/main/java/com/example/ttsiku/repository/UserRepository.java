package com.example.ttsiku.repository;

import com.example.ttsiku.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
