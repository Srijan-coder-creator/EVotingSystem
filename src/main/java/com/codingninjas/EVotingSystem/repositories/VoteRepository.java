package com.codingninjas.EVotingSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.codingninjas.EVotingSystem.entities.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByUserIdAndElectionId(Long userId, Long electionId);
    
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.election.name = :electionName")
    long countByElectionName(@Param("electionName") String electionName);
}