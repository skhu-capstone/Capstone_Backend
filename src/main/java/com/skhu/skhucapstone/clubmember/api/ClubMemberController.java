package com.skhu.skhucapstone.clubmember.api;

import com.skhu.skhucapstone.clubmember.api.dto.request.ClubMemberRequest;
import com.skhu.skhucapstone.clubmember.api.dto.response.ClubMemberResponse;
import com.skhu.skhucapstone.clubmember.application.ClubMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clubs/{clubId}/members")
@RequiredArgsConstructor
public class ClubMemberController {

    private final ClubMemberService clubMemberService;

    @PostMapping
    public ClubMemberResponse registerMembers(
            @PathVariable Long clubId,
            @Valid @RequestBody ClubMemberRequest request
    ) {
        return clubMemberService.registerMembers(clubId, request);
    }
}