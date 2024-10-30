package com.merck.library_management_system.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.merck.library_management_system.entity.Book;
import com.merck.library_management_system.entity.Student;
import com.merck.library_management_system.repository.StudentRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Student Operations", description = "Operations related to student management")
@CrossOrigin("*")
@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	StudentRepository sp;
	
	@ApiOperation(tags="Student Operations", value = "Get All Student", notes = "Get all student details")
	@GetMapping("/get")
	public ResponseEntity<List<Student>> getAllStudents() {
	    List<Student> students = sp.findAll(); // Fetch all students
	    students.sort((a, b) -> a.getStudentName().compareTo(b.getStudentName())); // Sort by student name
	    if (students.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(students); // Return 204 No Content if no books found
	    }
	    return ResponseEntity.ok(students); // Return 200  Return the sorted list
	}
	
	@ApiOperation(tags="Student Operations", value = "Get Student by ID", notes = "Get student details by his ID")
	@GetMapping("/id/{myId}")
	public ResponseEntity<Student> getStudentById(@PathVariable String myId) {
		Optional<Student> studentOpt = sp.findById(myId);
		if(studentOpt.isPresent()) {
			return ResponseEntity.ok(studentOpt.get()); 
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	
	@ApiOperation(tags="Student Operations", value = "Add Student", notes = "Add a new Student")
	@PostMapping("/post")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public  ResponseEntity<String> createStudent(@RequestBody Student myEntry ) {
		String id = myEntry.getUsername();
		if(sp.existsById(id)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Student with this username already exists.");
		}
		else {
			sp.save(myEntry);
			return ResponseEntity.status(HttpStatus.CREATED).body("Student Registered successfully.");
		}
	}	
	
	@ApiOperation(tags="Student Operations", value = "Delete Student", notes = "Delete a Student")
	@DeleteMapping("/delete/{myId}")
	public ResponseEntity<String> deleteStudentById(@PathVariable String myId) {
		if(sp.existsById(myId)) {
			sp.deleteById(myId);
			return ResponseEntity.ok("Student removed successfully.");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no student with this username.");
		}
	}
	
	@ApiOperation(tags="Student Operations", value = "Update Student Details", notes = "Update a student Details")
	@PutMapping("/update")
	public ResponseEntity<String> updateStudentById(@RequestBody Student myEntry ) {
		String id = myEntry.getUsername();
		if(sp.existsById(id)) {
			sp.save(myEntry);
			return ResponseEntity.ok("Student details updated successfully.");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no student with this username.");
		}
	}
	
	@ApiOperation(tags="Student Operations", value = "Get Book Details", notes = "Get book Details taken by a student")
	@GetMapping("/book/{myId}")
	public  ResponseEntity<List<Book>> getStudentBookById(@PathVariable String myId) {
		if(sp.existsById(myId)) {
			 List<Book> books = sp.findById(myId).get().getBookTaken();
			 return ResponseEntity.ok(books);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
