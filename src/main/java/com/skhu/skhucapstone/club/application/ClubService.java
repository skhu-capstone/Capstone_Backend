package com.skhu.skhucapstone.club.application;

import com.skhu.skhucapstone.club.api.dto.Request.ClubCreateRequest;
import com.skhu.skhucapstone.club.api.dto.Response.ClubResponse;
import com.skhu.skhucapstone.club.domain.Club;
import com.skhu.skhucapstone.club.domain.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubService {

    private final ClubRepository clubRepository;

    @Transactional
    public ClubResponse createClub(ClubCreateRequest request) {
        Club club = Club.builder()
                .clubName(request.clubName())
                .category(request.category())
                .shortDescription(request.shortDescription())
                .detailDescription(request.detailDescription())
                .imageUrl(request.imageUrl())
                .regularMeetingTime(request.regularMeetingTime())
                .activityLocation(request.activityLocation())
                .contact(request.contact())
                .build();

        Club savedClub = clubRepository.save(club);

        return ClubResponse.from(savedClub);
    }

    public List<ClubResponse> getClubs() {
        return clubRepository.findByApprovedTrue()
                .stream()
                .map(ClubResponse::from)
                .toList();
    }

    public ClubResponse getClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 동아리를 찾을 수 없습니다. clubId = " + clubId));

        return ClubResponse.from(club);
    }

    @Transactional
    public ClubResponse approveClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 동아리를 찾을 수 없습니다. clubId = " + clubId));

        club.approve();

        return ClubResponse.from(club);
    }

    @Transactional
    public ClubResponse rejectClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 동아리를 찾을 수 없습니다. clubId = " + clubId));

        club.reject();

        return ClubResponse.from(club);
    }

    public List<ClubResponse> getPendingClubs() {
        return clubRepository.findByApprovedFalse()
                .stream()
                .map(ClubResponse::from)
                .toList();
    }
}