package com.skhu.skhucapstone.club.domain.repository;

import com.skhu.skhucapstone.club.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {

    List<Club> findByApprovedTrue(); //승인된 동아리 목록 띄우는거

    List<Club> findByApprovedFalse(); // 승인 대기랑 반려 동아리 띄우는거
}