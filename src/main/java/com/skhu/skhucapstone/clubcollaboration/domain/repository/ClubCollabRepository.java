package com.skhu.skhucapstone.clubcollaboration.domain.repository;

import com.skhu.skhucapstone.clubcollaboration.domain.ClubCollaboration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubCollabRepository extends JpaRepository<ClubCollaboration, Long> {

    Page<ClubCollaboration> findAllByOrderByCreatedAtDesc(Pageable pageable);
}