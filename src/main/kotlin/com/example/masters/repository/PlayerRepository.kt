package com.example.masters.repository

import com.example.masters.model.Player
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PlayerRepository : JpaRepository<Player, Long> {
    fun findByplayer(player: String): Optional<Player>
}