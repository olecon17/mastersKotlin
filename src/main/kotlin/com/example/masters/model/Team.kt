package com.example.masters.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
        data class Team (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @get: NotBlank
        val teamName: String = "",

        @get: NotBlank
        val teamOwner: String = "",

        val playerOne: Long = 0,
        val playerTwo: Long = 0,
        val playerThree: Long = 0,
        val playerFour: Long = 0,
        val playerFive: Long = 0
)

