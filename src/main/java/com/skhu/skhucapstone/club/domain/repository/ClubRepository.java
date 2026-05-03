package com.skhu.skhucapstone.club.domain.repository;

import com.skhu.skhucapstone.club.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {

    List<Club> findByApprovedTrue();
}