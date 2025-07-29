package com.codingninjas.EVotingSystem.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codingninjas.EVotingSystem.entities.Election;
import com.codingninjas.EVotingSystem.entities.ElectionChoice;
import com.codingninjas.EVotingSystem.entities.User;
import com.codingninjas.EVotingSystem.entities.Vote;
import com.codingninjas.EVotingSystem.repositories.ElectionChoiceRepository;
import com.codingninjas.EVotingSystem.repositories.ElectionRepository;
import com.codingninjas.EVotingSystem.repositories.UserRepository;
import com.codingninjas.EVotingSystem.repositories.VoteRepository;

@Service
public class EVotingService {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ElectionRepository electionRepository;

    @Autowired
    ElectionChoiceRepository electionChoiceRepository;

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    public void addUser(User user) {
    	
         userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addVote(Long userId, Long electionId, Long electionChoiceId) {
        if(AlreadyGivenVote(userId, electionId)) {
            throw new RuntimeException("You have already given your vote");
        }
        
        Vote vote = new Vote();
        vote.setUser(userRepository.findById(userId).orElseThrow());
        vote.setElection(electionRepository.findById(electionId).orElseThrow());
        vote.setElectionChoice(electionChoiceRepository.findById(electionChoiceId).orElseThrow());
        
        voteRepository.save(vote);
    }

    public void addElection(Election election) {
        electionRepository.save(election);
    }

    public boolean AlreadyGivenVote(Long userId, Long electionId) {
        return voteRepository.existsByUserIdAndElectionId(userId, electionId);
    }

    public List<Election> getAllElections() {
        return electionRepository.findAll();
    }

    public void addElectionChoice(ElectionChoice electionChoice) {
    	Election managedElection = electionRepository.findById(electionChoice.getElection().getId())
    	        .orElseThrow(() -> new RuntimeException("Election not found"));
    	    
    	    // Set the managed election to the choice
    	    electionChoice.setElection(managedElection);
    	    
    	    electionChoiceRepository.save(electionChoice);
    }

    public List<ElectionChoice> getAllElectionChoices() {
        return electionChoiceRepository.findAll();
    }

    public Election findElectionByName(String electionName) {
        return electionRepository.findByName(electionName);
    }

    public long countTotalVotes() {
        return voteRepository.count();
    }

    public long countVotesByElectionName(String electionName) {
        return voteRepository.countByElectionName(electionName);
    }

    public long choicesByElection(Long electionId) {
        return electionChoiceRepository.countByElectionId(electionId);
    }

    public ElectionChoice findElectionWinner(String electionName) {
        Election election = electionRepository.findByName(electionName);
        return electionChoiceRepository.findElectionChoiceWithMaxVotes(election.getId());
    }
}