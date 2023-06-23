package com.example.api.repository;

import com.example.api.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventsRepository extends JpaRepository<Events, Long>{
    
}
