package com.merck.library_management_system.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class Student {
	
	@Id
	private String username;
	private String password;
	private String studentName;
	private String fatherName;
	private long age;
	private String mobileNumber;
	private List<Book> bookTaken=new ArrayList<>();
	

	public String getUsername() {
		return username;
	}
	public void setId(String username) {
		this.username = username;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public long getAge() {
		return age;
	}
	public void setAge(long age) {
		this.age = age;
	}
	public List<Book> getBookTaken() {
		return bookTaken;
	}
	public void setBookTaken(List<Book> bookTaken) {
		this.bookTaken = bookTaken;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
