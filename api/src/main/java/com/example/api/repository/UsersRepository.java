package com.example.api.repository;

import com.example.api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long>{
    Users findByUsername(String username);
}
