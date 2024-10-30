package com.merck.library_management_system.controller;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.merck.library_management_system.entity.Book;
import com.merck.library_management_system.entity.Student;
import com.merck.library_management_system.repository.AdminRepository;
import com.merck.library_management_system.repository.BookRepository;
import com.merck.library_management_system.repository.StudentRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Operations", description = "Operations related to user management")
@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	BookRepository bp;
	
	@Autowired
	StudentRepository sp;
	
	@Autowired
	AdminRepository ap;
	
	 
	 @ApiOperation(tags="User Operations", value = "All Book Details", notes = "Get all Book details")
		@GetMapping("/book/get")
		public ResponseEntity<List<Book>> getAllBooks() {
		    List<Book> books = bp.findAll(); 
		    books.sort((a, b) -> a.getBookName().compareTo(b.getBookName())); // Sort by book name
		    if (books.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(books); // Return 204 No Content if no books found
		    }
		    return ResponseEntity.ok(books); // Return 200 OK with the list of books
		}

		@ApiOperation(tags="User Operations", value = "Book Details by Id", notes = "Get Book details by Book ID")
		@GetMapping("/book/id/{myId}")
		public ResponseEntity<Book> getBookById(@PathVariable Long myId) {
		    Optional<Book> bookOpt = bp.findById(myId); // Use Optional to avoid NoSuchElementException
		    if (bookOpt.isPresent()) {
		        return ResponseEntity.ok(bookOpt.get()); // Return 200 OK with the book
		    } else {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 Not Found if the book is not found
		    }
		}
	
	 @ApiOperation(tags="User Operations", value = "Issue Book", notes = "Issue a book for yourself")
	@PutMapping("/issue")
	@Transactional
	public ResponseEntity<String> issue(@RequestParam Long bookId) {
		 
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String studentId = user.getUsername();	
	    
	    Student student=null;
 		
 		
 		if(bp.findById(bookId).isPresent()) {
 			Book book = bp.findById(bookId).get();
 			if(book.isAvailable()) {
 				if(sp.findById(studentId).isPresent()){
 					book.setAvailable(false);
 				
 				
 					book.setIssueDate(LocalDate.now());
 					book.setDueDate(LocalDate.now().plusDays(7));
 				
 					student = sp.findById(studentId).get();
 					student.getBookTaken().add(book);
 					bp.save(book);
 					sp.save(student);
 					return ResponseEntity.ok("Book issued successfully.");
 				}
 				else {
 					 return ResponseEntity.status(HttpStatus.CONFLICT).body("There is no student with this username.");
 				}
 			}
 			else {
 				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book is not available.");
 			}
 		}
 		else {
 			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no book with this ID.");
 		}
 	}
 	
	 @ApiOperation(tags="User Operations", value = "Submit Book", notes = "Submit book taken by you")
 	@PutMapping("/submit")
 	@Transactional
 	public ResponseEntity<String> submit(@RequestParam Long bookId) {
 		
		 User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 String studentId = user.getUsername();	
 		
	    Student student=null;
 		if(bp.findById(bookId).isPresent()) {
 			
 			Book book = bp.findById(bookId).get();
 			
 			if(!book.isAvailable()) {
 				if(sp.findById(studentId).isPresent()){
 					student = sp.findById(studentId).get();
 					if(student.getBookTaken().contains(book)) {
 						book.setAvailable(true);
 						book.setIssueDate(null);
 						book.setDueDate(null);
 						student.getBookTaken().remove(book);
 						bp.save(book);
 	 					sp.save(student);
 	 					return ResponseEntity.ok("Book submited successfully.");
 	 				}
 					else {
 						 return ResponseEntity.status(HttpStatus.FORBIDDEN).body("There is not issued by this student.");
 					}
 				}
 	 			else {
 	 					 return ResponseEntity.status(HttpStatus.CONFLICT).body("There is no student with this username.");
 	 			}
 			}
 	 		else {
 	 			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This book is not issued to anyone.");
 	 		}
 		}
 	 	else {
 	 			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no book with this ID.");
 	 	}
 	}
 	
	 @ApiOperation(tags="User Operations", value = "Book Details", notes = "Get book details taken by you")
	@GetMapping("/book")
	public ResponseEntity<List<Book>> getStudentBookById() {
		 User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 String studentId = user.getUsername();	
		
	    if(sp.existsById(studentId)) {
			 List<Book> books = sp.findById(studentId).get().getBookTaken();
			 return ResponseEntity.ok(books);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	 
	 @ApiOperation(tags="User Operations", value = "Change Password", notes = "Change your password")
	@PutMapping("/update")
	@Transactional
	public ResponseEntity<String> updateStudentById(@RequestParam String currentPassword, @RequestParam String newPassword, HttpServletRequest request ) {
		
		 User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 String studentId = user.getUsername();	
	
	    Optional<Student> studentOpt = sp.findById(studentId);
	    if (studentOpt.isPresent()) {
	        Student student = studentOpt.get();
	        if (student.getPassword().equals(currentPassword)) {
	            student.setPassword(newPassword); // Update the password
	            sp.save(student); // Save the updated student back to the repository
	            return ResponseEntity.ok("Your password changed successfully.");
	    	}
	    	else {
	    		 return ResponseEntity.status(HttpStatus.CONFLICT).body("Please enter your correct current password.");
	    	}
	    }
	    else {
	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no student with this username.");
	    }
	    	
	}
}

