package com.example.masters.controller

import com.example.masters.model.Team
import com.example.masters.repository.TeamRepository
import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
        class TeamController(private val teamRepository: TeamRepository) {

    @GetMapping("/teams")
    fun getAllTeams(): List<Team> = teamRepository.findAll()

    @PostMapping("/teams")
    fun addTeam(@Valid @RequestBody newTeam: Map<String, String>) : Team {
        println(newTeam)
        println("fuck")

        var createdTeam = Team(
                teamName = newTeam.get("teamName")!!,
                teamOwner = newTeam.get("teamOwner")!!,
                playerOne = newTeam.get("playerOne")!!.toLong(),
                playerTwo = newTeam.get("playerTwo")!!.toLong(),
                playerThree = newTeam.get("playerThree")!!.toLong(),
                playerFour = newTeam.get("playerFour")!!.toLong(),
                playerFive = newTeam.get("playerFive")!!.toLong()
        )
        println("team")
        println(createdTeam)
        return teamRepository.save(createdTeam)
    }

    @GetMapping("/teams/{id}")
    fun getTeamById(@PathVariable(value = "id") teamId: Long): ResponseEntity<Team> {
        return teamRepository.findById(teamId).map { team ->
            ResponseEntity.ok(team)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/players/{id}")
    fun getTeamPlayersById(@PathVariable(value = "id") teamId: Long) : ResponseEntity<ArrayList<Long>> {

        return teamRepository.findById(teamId).map { team ->
            var playerList = ArrayList<Long>()

            playerList.add(team.playerOne)
            playerList.add(team.playerTwo)
            playerList.add(team.playerThree)
            playerList.add(team.playerFour)
            playerList.add(team.playerFive)
            ResponseEntity.ok(playerList)
        }.orElse((ResponseEntity.notFound().build()))

    }

    @PutMapping("/teams/{id}")
    fun editTeamById(@PathVariable(value = "id") id : Long, @Valid @RequestBody newTeam : Team) : ResponseEntity<Team> {
        return teamRepository.findById(id).map { existingTeam ->
            val updatedTeam: Team = existingTeam
                    .copy(teamName = newTeam.teamName, teamOwner = newTeam.teamOwner)
            ResponseEntity.ok().body(teamRepository.save(updatedTeam))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/teams/{id}")
    fun deleteTeamById(@PathVariable(value = "id") id : Long) : ResponseEntity<Void> {
        return teamRepository.findById(id).map { existingTeam ->
            teamRepository.delete(existingTeam)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}
