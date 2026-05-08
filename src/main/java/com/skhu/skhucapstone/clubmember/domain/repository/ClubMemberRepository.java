package com.skhu.skhucapstone.clubmember.domain.repository;

import com.skhu.skhucapstone.clubmember.domain.ClubJoinStatus;
import com.skhu.skhucapstone.clubmember.domain.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    List<ClubMember> findByUserUserIdAndClubJoinStatus(Long userId, ClubJoinStatus clubJoinStatus);
}
