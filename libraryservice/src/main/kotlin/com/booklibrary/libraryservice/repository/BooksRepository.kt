package com.booklibrary.libraryservice.repository

import com.booklibrary.libraryservice.model.Book
import org.springframework.data.repository.CrudRepository

interface BooksRepository: CrudRepository<Book, Long>