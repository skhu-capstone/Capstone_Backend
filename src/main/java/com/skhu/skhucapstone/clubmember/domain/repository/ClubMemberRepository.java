package com.skhu.skhucapstone.clubmember.domain.repository;

import com.skhu.skhucapstone.club.domain.Club;
import com.skhu.skhucapstone.clubmember.domain.ClubJoinStatus;
import com.skhu.skhucapstone.clubmember.domain.ClubMember;
import com.skhu.skhucapstone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    List<ClubMember> findByUserUserIdAndClubJoinStatus(Long userId, ClubJoinStatus clubJoinStatus);

    boolean existsByClubAndUser(Club club, User user);
}