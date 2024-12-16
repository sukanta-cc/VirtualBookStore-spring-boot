package com.virtualbookstore.VirtualBookStore.repositories;

import com.virtualbookstore.VirtualBookStore.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepositories extends MongoRepository<Book, String> {
    Optional<Book> findByTitle(String title);

    Optional<Iterable<Book>> findAllByCreator(String creator);

    Optional<Book> findByIdAndEnabled(String bookId, boolean enabled);

    Optional<Book> findByTitleAndEnabled(String title, boolean enabled);
}
