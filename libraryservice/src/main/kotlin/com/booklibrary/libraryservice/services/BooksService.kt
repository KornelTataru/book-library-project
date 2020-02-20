package com.booklibrary.libraryservice.services

import com.booklibrary.libraryservice.models.Books
import com.booklibrary.libraryservice.repositories.BooksRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BooksService(private val booksRepository: BooksRepository) {

    fun all(): Iterable<Books> = booksRepository.findAll()

    fun get(id: Long): Books? = booksRepository.findByIdOrNull(id)

    fun add(books: Books): Books = booksRepository.save(books)

    fun edit(id: Long, books: Books): Books = booksRepository.save(books.copy(id = id))

    fun remove(id: Long) = booksRepository.deleteById(id)
}