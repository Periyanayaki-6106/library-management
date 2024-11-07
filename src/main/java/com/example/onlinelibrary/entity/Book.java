package com.example.onlinelibrary.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name= "books")



public class Book {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String title;
 
    private Date publishedDate;
 
    @ManyToOne
    @JoinColumn(name = "author_id",nullable=false)
    private Author author;
 
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable=false)
    private Publisher publisher;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getpublishedDate() {
		return publishedDate;
	}

	public void setpublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Book(Long id, String title, Date publishedDate, Author author, Publisher publisher) {
		super();
		this.id = id;
		this.title = title;
		this.publishedDate = publishedDate;
		this.author = author;
		this.publisher = publisher;
	}

	public Book() {
		super();
	}
	
    
    

}
