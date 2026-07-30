package com.skhu.skhucapstone.club.domain.repository;

import com.skhu.skhucapstone.club.domain.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query("""
        SELECT c FROM Club c
        WHERE (:keyword IS NULL
            OR c.clubName LIKE CONCAT('%', :keyword, '%'))
        AND (:category IS NULL
            OR c.category = :category)
        ORDER BY c.createdAt DESC
        """)
    Page<Club> searchClubs(@Param("keyword") String keyword, @Param("category") String category, Pageable pageable
    );
}