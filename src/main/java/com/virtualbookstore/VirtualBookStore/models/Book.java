package com.virtualbookstore.VirtualBookStore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String id;
    private String title;
    private String description;
    private String author;
    private String genre = "General";
    @DBRef(lazy = true)
    private User creator;
    private boolean enabled = true;
    private Date created_at = new Date();
    private Date updated_at = new Date();

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", enabled=" + enabled +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
