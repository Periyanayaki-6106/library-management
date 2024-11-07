package com.example.onlinelibrary.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "books")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Book {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String title;
 
    private Date publicationDate;
 
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
 
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

}
