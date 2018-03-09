package com.example.masters.controller

import com.example.masters.model.Player
import com.example.masters.model.Refresh
import com.example.masters.repository.PlayerRepository
import com.google.gson.Gson
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

@RestController
        @RequestMapping("/players")
        class PlayerController(private val playerRepository: PlayerRepository) {
    @GetMapping("/all")
    fun getAllPlayers(): List<Player> = playerRepository.findAll()

    @PostMapping("/seed")
    fun seedData(): List<Player> {
        val connection = URL("http://samsandberg.com/themasters").openConnection() as HttpURLConnection

        connection.connect()
        println(connection.responseCode)
        println(connection.getHeaderField("Content-Type"))
        val json = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
//        println(text)
        val gson = Gson()

        println("=== List from JSON ===")
        println(json)

        val update : Refresh = gson.fromJson(json, Refresh::class.java)

        update.players.forEach {
            val newPlayer = Player(
                    player = it.player,
                    thru = it.thru,
                    today = it.today,
                    r1 = it.r1,
                    r2 = it.r2,
                    r3 = it.r3,
                    r4 = it.r4,
                    to_par = it.to_par,
                    pos = it.pos,
                    tot = it.tot,
                    isCut = (it.thru == ("CUT"))
            )

            println("Creating new player ${it.player}")
            println(newPlayer)
            playerRepository.save(newPlayer)

        }
        return playerRepository.findAll()
    }

    @PostMapping("/refresh")
    fun refreshPlayers(): List<Player> {

        val connection = URL("http://samsandberg.com/themasters").openConnection() as HttpURLConnection

        connection.connect()
        println(connection.responseCode)
        println(connection.getHeaderField("Content-Type"))
        val json = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
//        println(text)
        val gson = Gson()

        println("=== List from JSON ===")
        println(json)

        val update : Refresh = gson.fromJson(json, Refresh::class.java)

        update.players.forEach {
            val existingPlayer: Optional<Player> = playerRepository.findByplayer(it.player)

            if (existingPlayer.isPresent) {
                val updatedPlayer = existingPlayer.get().copy(
                        thru = it.thru,
                        today = it.today,
                        r1 = it.r1,
                        r2 = it.r2,
                        r3 = it.r3,
                        r4 = it.r4,
                        to_par = it.to_par,
                        pos = it.pos,
                        tot = it.tot
                )
                playerRepository.save(updatedPlayer)

            }
        }

        return playerRepository.findAll()

    }
}