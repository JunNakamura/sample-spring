package com.example.sample.domain.book;

import java.util.Collections;
import java.util.List;

public class Books {

    private final List<Book> books;

    public static Books empty() {
        return of(Collections.emptyList());
    }

    public static Books of(List<Book> books) {
        return new Books(books);
    }

    Books(List<Book> books) {
        this.books = Collections.unmodifiableList(books);
    }

    public List<Book> getBooks() {
        return books;
    }
}
