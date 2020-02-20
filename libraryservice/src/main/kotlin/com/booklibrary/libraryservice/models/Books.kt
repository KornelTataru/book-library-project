package com.booklibrary.libraryservice.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name = "books")
data class Books(
        @JsonProperty("name")
        @Column(name = "name", length = 200)
        val name: String = "TestName",

        @JsonProperty("description")
        @Column(name = "description", length = 1000)
        val description: String = "TestDescription",

        @Id
        @JsonProperty("id")
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0L
)