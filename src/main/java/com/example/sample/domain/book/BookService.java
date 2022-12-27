package com.example.sample.domain.book;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    List<Book> findAll() {
        return bookRepository.findAll();
    }

    long create(String title) {
        Book book = new Book(title);
        Book savedBook = bookRepository.save(book);
        return savedBook.getId();
    }

    Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    Optional<Book> update(long id, String title) {
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(x -> {
            x.setTitle(title);
            bookRepository.save(x);
        });
        return book;
    }

    void delete(long id) {
        bookRepository.deleteById(id);
    }

}
