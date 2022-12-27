package com.example.sample.domain.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public Books findAll() {
        List<Book> books = bookRepository.findAll();
        return Books.of(books);
    }

    @PostMapping
    public long create(@RequestParam("title") String title) {
        Book book = new Book(title);
        Book savedBook = bookRepository.save(book);
        return savedBook.getId();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") long id) {
        Optional<Book> book = bookRepository.findById(id);
        return ResponseEntity.of(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable("id") long id, @RequestParam("title") String title) {
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(x -> {
            x.setTitle(title);
            bookRepository.save(x);
        });
        return ResponseEntity.of(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        bookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }






}
