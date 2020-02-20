package com.booklibrary.libraryservice.repositories

import com.booklibrary.libraryservice.models.Books
import org.springframework.data.repository.CrudRepository

interface BooksRepository: CrudRepository<Books, Long>