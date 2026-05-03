package com.skhu.skhucapstone.club.api;

import com.skhu.skhucapstone.club.api.dto.Response.ClubResponse;
import com.skhu.skhucapstone.club.application.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 여기 컨트롤러는 관리자용임
@RestController
@RequestMapping("/api/admin/clubs")
@RequiredArgsConstructor
public class AdminClubController {

    private final ClubService clubService;

    @GetMapping
    public List<ClubResponse> getUnapprovedClubs() {
        return clubService.getUnapprovedClubs();
    }
}