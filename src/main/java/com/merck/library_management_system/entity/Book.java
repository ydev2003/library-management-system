package com.merck.library_management_system.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Book {
	
	@Id
	private long id;
	private String bookName;
	private String authorName;
	private double price;
	private boolean available;
	private LocalDate issueDate;
	private LocalDate dueDate;
	
	
	public LocalDate getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	   @Override
	    public int hashCode(){
	        final int prime  = 31;
	        int result = 1;
	        result  = prime * result +(int)id;
	        return result;
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if(this == obj) {
	            return true;
	        }
	        if(obj == null) {
	            return false;
	        }
	        if(this.getClass() != obj.getClass()) {
	            return false;
	        }
	        Book other = (Book) obj;
	        if(id != other.id) {
	            return false;
	        }
//	        if(physics != other.physics) {
//	            return false;
//	        }
	        return true;
	    }
}
