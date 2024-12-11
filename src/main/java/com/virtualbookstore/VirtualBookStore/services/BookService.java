package com.virtualbookstore.VirtualBookStore.services;

import com.virtualbookstore.VirtualBookStore.config.ApiResponse;
import com.virtualbookstore.VirtualBookStore.models.Book;
import com.virtualbookstore.VirtualBookStore.models.User;
import com.virtualbookstore.VirtualBookStore.repositories.BookRepositories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class BookService {

    private final BookRepositories bookRepositories;

    public BookService(BookRepositories bookRepositories) {
        this.bookRepositories = bookRepositories;
    }

    public ApiResponse<Book> create(Book book) {
        try {
            ApiResponse<Book> response = new ApiResponse<>();

            // Check if the same titled book is already existed by this name or not by the same author.
            Book existingBook = bookRepositories.findByTitle(book.getTitle()).orElse(null);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) auth.getPrincipal();

            if (existingBook != null) {
                response.setError(true);
                response.setCode("BOOK_ALREADY_EXIST");
                response.setMessage("Book already exist");
            } else {
                Book newBook = new Book();
                newBook.setTitle(book.getTitle());
                newBook.setDescription(book.getDescription());
                newBook.setAuthor(book.getAuthor());
                newBook.setGenre(book.getGenre());
                newBook.setCreator(currentUser);

                Book savedBook = bookRepositories.save(newBook);

                response.setError(false);
                response.setCode("BOOK_ADDED");
                response.setMessage("Book is added successfully");
                response.setData(savedBook);
            }

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ApiResponse<Iterable<Book>> getAllBooks() {
        try {
            ApiResponse<Iterable<Book>> response = new ApiResponse<>();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) auth.getPrincipal();

            Iterable<Book> allBooks;
            if (currentUser.getRole() != null && currentUser.getRole().equals("ADMIN")) {
                allBooks = bookRepositories.findAll(Sort.by(Sort.Order.asc("created_at")));
            } else {
                allBooks = bookRepositories.findAllByCreator(currentUser.getId()).orElse(new ArrayList<>());
            }



            response.setError(false);
            response.setCode("ALL_BOOKS_FETCHED");
            response.setMessage("All books fetched successfully");
            response.setData(allBooks);

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
