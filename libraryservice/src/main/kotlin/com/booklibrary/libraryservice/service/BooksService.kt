package com.booklibrary.libraryservice.service

import com.booklibrary.libraryservice.exception.EntityNotFoundException
import com.booklibrary.libraryservice.model.Book
import com.booklibrary.libraryservice.repository.BooksRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BooksService(@Autowired private val booksRepository: BooksRepository) {

    fun getAllBooks(): MutableList<Book> {
        val books = booksRepository.findAll() as MutableList<Book>
        if(books.isEmpty())
            throw EntityNotFoundException("No Books were found")

            return books
    }

    fun getBook(id: Long): ResponseEntity<Book> {
        val book = booksRepository.findByIdOrNull(id) ?: throw EntityNotFoundException("Book Not Found")
        return ResponseEntity(book, OK)
    }

    fun createBook(book: Book): ResponseEntity<Book> {
        val createdBook = booksRepository.save(book)
        return ResponseEntity(createdBook, CREATED)
    }

    fun editBook(id: Long, book: Book): ResponseEntity<Book> {
        val updatedBook = booksRepository.save(book.copy(id = id))
        return ResponseEntity(updatedBook, ACCEPTED)
    }

    fun deleteBook(id: Long): ResponseEntity<Any> {
        if (getBook(id).statusCode.isError) {
            throw EntityNotFoundException("Book Not Found")
        }

        booksRepository.deleteById(id)
        return ResponseEntity(NO_CONTENT)
    }
}