package com.virtualbookstore.VirtualBookStore.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtualbookstore.VirtualBookStore.Dtos.Book.UpdateBookDto;
import com.virtualbookstore.VirtualBookStore.config.ApiResponse;
import com.virtualbookstore.VirtualBookStore.models.Book;
import com.virtualbookstore.VirtualBookStore.services.BookService;

@RestController
@RequestMapping(value = "/api/books", produces = "application/json", consumes = "application/json")
public class BooksController {

    private final BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    // Register book
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Book>> create(@RequestBody Book book) {
        ApiResponse<Book> bookApiResponse = bookService.create(book);

        if (bookApiResponse.isError()) {
            return new ResponseEntity<>(bookApiResponse, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(bookApiResponse, HttpStatus.OK);
        }
    }

    // Get all books

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<Iterable<Book>>> getAllBooks() {
        ApiResponse<Iterable<Book>> bookApiResponse = bookService.getAllBooks();

        if (bookApiResponse.isError()) {
            return new ResponseEntity<>(bookApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(bookApiResponse, HttpStatus.OK);
        }
    }

    // Get a particular book based on title or description or author

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Book>> getMethodName(@PathVariable String bookId) {
        ApiResponse<Book> apiResponse = bookService.getBookById(bookId);

        if (apiResponse.isError()) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok().body(apiResponse);
        }
    }

    // Update a book

    @PutMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Book>> putMethodName(
            @PathVariable String bookId,
            @RequestBody UpdateBookDto updateBookDto) {

        return ResponseEntity.ok().build();
    }

    // Delete book from server

    // Delete book permanently

}
