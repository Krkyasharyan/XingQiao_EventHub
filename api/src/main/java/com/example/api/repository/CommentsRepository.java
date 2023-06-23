package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.model.Comments;

public interface CommentsRepository  extends JpaRepository<Comments, Long>{
    
}
