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
                .description(request.description())
                .build();

        Club savedClub = clubRepository.save(club);

        return ClubResponse.from(savedClub);
    }

    public List<ClubResponse> getClubs() {
        return clubRepository.findAll()
                .stream()
                .map(ClubResponse::from)
                .toList();
    }

    public ClubResponse getClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 동아리입니다."));

        return ClubResponse.from(club);
    }
}