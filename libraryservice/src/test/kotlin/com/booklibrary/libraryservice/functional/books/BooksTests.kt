package com.booklibrary.libraryservice.functional.books

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@ExtendWith(SpringExtension::class)
class BooksTests {
    private val baseUrl = "http://localhost:8080/books/"
    private val jsonContentType = MediaType(MediaType.APPLICATION_JSON.type, MediaType.APPLICATION_JSON.subtype)
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var webAppContext: WebApplicationContext

    @BeforeEach
    fun init(){
        mockMvc = webAppContextSetup(webAppContext).build()
    }

    @Test
    fun `add new book`() {
        val passedJson =
                """ {
                    "name": "Kotlin in Action", 
                    "description": "Learn Kotlin language and develop apps"
                    }
                """.trimIndent()

        val  request = post(baseUrl)
                .contentType(jsonContentType)
                .content(passedJson)

        val resultJson =
                """ {
                    "name": "Kotlin in Action",
                    "description": "Learn Kotlin language and develop apps",
                    "id": 1
                    }
                """.trimIndent()

        mockMvc.perform(request)
                .andExpect(status().isCreated)
                .andExpect(content().json(resultJson, true))

    }

    @Test
    fun `getAllBooks`() {
        TODO()
    }

}