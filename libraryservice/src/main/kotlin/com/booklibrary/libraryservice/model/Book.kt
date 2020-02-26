package com.booklibrary.libraryservice.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "book")
data class Book(
        @Id
        @JsonProperty("id")
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L,

        @JsonProperty("name")
        @get: NotBlank
        @get: Length(min = 2, max = 100)
        @Column(name = "name", nullable = false)
        val name: String,

        @JsonProperty("description")
        @get: NotBlank
        @get: Length(min = 2, max = 1000)
        @Column(name = "description")
        val description: String)