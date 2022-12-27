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

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Books findAll() {
        List<Book> books = bookService.findAll();
        return Books.of(books);
    }

    @PostMapping
    public long create(@RequestParam("title") String title) {
        return bookService.create(title);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") long id) {
        Optional<Book> book = bookService.findById(id);
        return ResponseEntity.of(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable("id") long id, @RequestParam("title") String title) {
        Optional<Book> book = bookService.update(id, title);
        return ResponseEntity.of(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        bookService.delete(id);
        return ResponseEntity.ok().build();
    }






}
