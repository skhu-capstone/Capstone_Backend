package com.skhu.skhucapstone.clubcollaboration.domain.repository;

import com.skhu.skhucapstone.clubcollaboration.domain.ClubCollaboration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubCollabRepository extends JpaRepository<ClubCollaboration, Long> {

    List<ClubCollaboration> findAllByOrderByCreatedAtDesc();
}