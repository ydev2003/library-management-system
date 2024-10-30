package com.merck.library_management_system.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.merck.library_management_system.repository.BookRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name= "Book Operations", description = "Operations related to book management")
@CrossOrigin("*")
@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	BookRepository bp;
	
	@ApiOperation(tags="Book Operations", value = "All Book Details", notes = "Get all Book details")
	@GetMapping("/get")
	public ResponseEntity<List<Book>> getAllBooks() {
	    List<Book> books = bp.findAll(); 
	    books.sort((a, b) -> a.getBookName().compareTo(b.getBookName())); // Sort by book name
	    if (books.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(books); // Return 204 No Content if no books found
	    }
	    return ResponseEntity.ok(books); // Return 200 OK with the list of books
	}

	@ApiOperation(tags="Book Operations", value = "Book Details by Id", notes = "Get Book details by Book ID")
	@GetMapping("/id/{myId}")
	public ResponseEntity<Book> getBookById(@PathVariable Long myId) {
	    Optional<Book> bookOpt = bp.findById(myId); // Use Optional to avoid NoSuchElementException
	    if (bookOpt.isPresent()) {
	        return ResponseEntity.ok(bookOpt.get()); // Return 200 OK with the book
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 Not Found if the book is not found
	    }
	}
	
//	@ApiOperation(value = "Search Book", notes = "Search Book by id")
//	@GetMapping("/search")
//	public Book getBook(@RequestParam Long myId) {		
//		return bp.findById(myId).get();
//	}
	
	@ApiOperation(tags="Book Operations", value = "Create a Book", notes = "Add a new Book")
	@PostMapping("/post")
	public ResponseEntity<String> createBook(@RequestBody Book myEntry ) {
		Long id = myEntry.getId();
		if(bp.existsById(id)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Book with this ID already exists.");
		}
		else {
			bp.save(myEntry);
			return ResponseEntity.status(HttpStatus.CREATED).body("Book created successfully.");
		}
	}
	
	@ApiOperation(tags="Book Operations", value = "Delete a Book", notes = "Delete a Book by id")
	@DeleteMapping("/delete/{myId}")
	public ResponseEntity<String> deleteBookById(@PathVariable Long myId) {
		 if(bp.existsById(myId)) {
			 bp.deleteById(myId);
			 return ResponseEntity.ok("Book removed successfully.");
		 }
		 else {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no book with this ID.");
		 }
	}
	
	@ApiOperation(tags="Book Operations", value = "Update a Book", notes = "Update a Book details")
	@PutMapping("/update")
	public ResponseEntity<String> updateBookById(@RequestBody Book myEntry ) {
		Long id = myEntry.getId();
		if(bp.existsById(id)) {
			bp.deleteById(id);
			bp.save(myEntry);
			return ResponseEntity.ok("Book details updated successfully.");
		}
		else {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no book with this ID.");
		}
	}

}
