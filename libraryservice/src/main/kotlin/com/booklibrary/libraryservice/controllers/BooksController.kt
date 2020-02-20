package com.booklibrary.libraryservice.controllers

import com.booklibrary.libraryservice.models.Books
import com.booklibrary.libraryservice.services.BooksService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("books")
class BooksController(@Autowired private val booksService: BooksService) {

    @GetMapping
    fun getAllBooks() = booksService.all()

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getBook(@PathVariable id: Long) = booksService.get(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@RequestBody books: Books) = booksService.add(books)

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateBook(@PathVariable id: Long,  books: Books) = booksService.edit(id, books)

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun deleteBook(@PathVariable id: Long) = booksService.remove(id)
}