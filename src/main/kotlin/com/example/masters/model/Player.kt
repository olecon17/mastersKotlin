package com.example.masters.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
        data class Player (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        val player: String = "",

        val thru: String = "",
        val today: String = "",
        val to_par: String = "",

        val pos: String = "0",
        val tot: String = "0",

        val r1: String = "",
        val r2: String = "",
        val r3: String = "",
        val r4: String = "",

        val isCut: Boolean = true

)




//"r4": "69", "to_par": "-9", "r1": "71", "r2": "69", "r3": "70",
// "pos": "1", "tot": "279", "player": "Sergio Garcia", "thru": "F",
// "today": "-3"}