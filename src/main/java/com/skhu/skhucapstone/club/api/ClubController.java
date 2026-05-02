package com.skhu.skhucapstone.club.api;

import com.skhu.skhucapstone.club.api.dto.Request.ClubCreateRequest;
import com.skhu.skhucapstone.club.api.dto.Response.ClubResponse;
import com.skhu.skhucapstone.club.application.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping
    public ClubResponse createClub(@RequestBody ClubCreateRequest request) {
        return clubService.createClub(request);
    }

    @GetMapping
    public List<ClubResponse> getClubs() {
        return clubService.getClubs();
    }

    @GetMapping("/{clubId}")
    public ClubResponse getClub(@PathVariable Long clubId) {
        return clubService.getClub(clubId);
    }
}