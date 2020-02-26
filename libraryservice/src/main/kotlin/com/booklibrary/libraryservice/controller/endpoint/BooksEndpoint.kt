package com.booklibrary.libraryservice.controller.endpoint

import com.booklibrary.libraryservice.model.Book
import com.booklibrary.libraryservice.service.BooksService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["books"], produces = [APPLICATION_JSON_VALUE])
class BooksEndpoint(@Autowired private val booksService: BooksService) {

    @GetMapping
    fun getAllBooks() = booksService.getAllBooks()

    @GetMapping("{id}")
    @ResponseStatus(OK)
    fun getBook(@PathVariable id: Long) = booksService.getBook(id)

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    @ResponseStatus(CREATED)
    fun createBook(@RequestBody @Valid book: Book) = booksService.createBook(book)

    @PutMapping("{id}", consumes = [APPLICATION_JSON_VALUE])
    @ResponseStatus(OK)
    fun updateBook(@PathVariable id: Long, book: Book) = booksService.editBook(id, book)

    @DeleteMapping("{id}")
    @ResponseStatus(ACCEPTED)
    fun deleteBook(@PathVariable id: Long) = booksService.deleteBook(id)
}