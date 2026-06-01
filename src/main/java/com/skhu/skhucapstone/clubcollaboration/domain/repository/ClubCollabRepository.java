package com.skhu.skhucapstone.clubcollaboration.domain.repository;

import com.skhu.skhucapstone.clubcollaboration.domain.ClubCollaboration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubCollabRepository extends JpaRepository<ClubCollaboration, Long> {

    Page<ClubCollaboration> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("""
        SELECT c FROM ClubCollaboration c
        WHERE (:keyword IS NULL
            OR c.title LIKE CONCAT('%', :keyword, '%')
            OR c.content LIKE CONCAT('%', :keyword, '%')
            OR c.contestName LIKE CONCAT('%', :keyword, '%')
            OR c.club.clubName LIKE CONCAT('%', :keyword, '%'))
        ORDER BY c.createdAt DESC
        """)
    Page<ClubCollaboration> searchCollabs(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}