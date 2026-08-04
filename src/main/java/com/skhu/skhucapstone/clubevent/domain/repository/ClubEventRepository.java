package com.skhu.skhucapstone.clubevent.domain.repository;

import com.skhu.skhucapstone.club.domain.Club;
import com.skhu.skhucapstone.clubevent.domain.ClubEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClubEventRepository extends JpaRepository<ClubEvent, Long> {

    Optional<ClubEvent> findByIdAndClub(Long eventId, Club club);

    List<ClubEvent> findByClubAndStartAtGreaterThanEqualAndStartAtLessThanOrderByStartAtAsc(Club club, LocalDateTime startAt, LocalDateTime endAt);
}