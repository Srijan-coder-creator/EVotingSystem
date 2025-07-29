package com.codingninjas.EVotingSystem.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.codingninjas.EVotingSystem.entities.Election;
import com.codingninjas.EVotingSystem.entities.ElectionChoice;
import com.codingninjas.EVotingSystem.entities.User;
import com.codingninjas.EVotingSystem.entities.Vote;
import com.codingninjas.EVotingSystem.services.EVotingService;

@RestController

public class EVotingController {
    
    @Autowired
    EVotingService eVotingService;

    // User Endpoints
    @PostMapping("/add/user")
    public void addUser(@RequestBody User user) {
        eVotingService.addUser(user);
    }

    @GetMapping("/get/users")
    public List<User> getAllUsers(){
        return eVotingService.getAllUsers();
    }

    // Election Endpoints
    @PostMapping("/add/election")
    public void addElection(@RequestBody Election election) {
        eVotingService.addElection(election);
    }

    @GetMapping("/get/elections")
    public List<Election> getAllElections(){
        return eVotingService.getAllElections();
    }

    // ElectionChoice Endpoints
    @PostMapping("/add/electionChoice")
    public void addElectionChoice(@RequestBody ElectionChoice electionChoice) {
        eVotingService.addElectionChoice(electionChoice);
    }

    @GetMapping("/get/electionChoices")
    public List<ElectionChoice> getAllElectionChoices() {
        return eVotingService.getAllElectionChoices();
    }

    @GetMapping("/count/{electionId}")
    public long getCountByElectionId(@PathVariable Long electionId){
        return eVotingService.choicesByElection(electionId);
    }

    // Vote Endpoints
    @PostMapping("/add/vote")
    public void addVote(@RequestParam Long userId, 
                       @RequestParam Long electionId, 
                       @RequestParam Long electionChoiceId) {
        eVotingService.addVote(userId, electionId, electionChoiceId);
    }

    @GetMapping("/get/votes")
    public List<Vote> getAllVotes() {
        return eVotingService.getAllVotes();
    }

    @GetMapping("/count/votes")
    public long countTotalVotes() {
        return eVotingService.countTotalVotes();
    }

    @GetMapping("/count/votes/{electionName}")
    public long countVotesByElectionName(@PathVariable String electionName) {
        return eVotingService.countVotesByElectionName(electionName);
    }

    // Results Endpoints
    @GetMapping("/winner/election/{electionName}")
    public ElectionChoice getElectionWinner(@PathVariable String electionName) {
        return eVotingService.findElectionWinner(electionName);
    }
}