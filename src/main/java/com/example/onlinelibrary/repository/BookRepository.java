package com.example.onlinelibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.onlinelibrary.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	
}
