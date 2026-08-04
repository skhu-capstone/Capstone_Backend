package com.skhu.skhucapstone.clubevent.api;

import com.skhu.skhucapstone.clubevent.api.dto.request.ClubEventCreateRequest;
import com.skhu.skhucapstone.clubevent.api.dto.response.ClubEventResponse;
import com.skhu.skhucapstone.clubevent.application.ClubEventService;
import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import com.skhu.skhucapstone.clubevent.api.dto.response.ClubEventListResponse;
import com.skhu.skhucapstone.clubevent.api.dto.request.ClubEventUpdateRequest;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clubs/{clubId}/events")
@RequiredArgsConstructor
@Tag(name = "Club Event", description = "동아리 캘린더 일정 API")
public class ClubEventController {

    private final ClubEventService clubEventService;

    @PostMapping
    @Operation(
            summary = "동아리 일정 생성",
            description = "동아리 대표 또는 운영진이 새로운 일정을 생성합니다."
    )
    public ResponseEntity<ApiResponse<ClubEventResponse>> createEvent(
            @PathVariable Long clubId,
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody ClubEventCreateRequest request
    ) {
        ClubEventResponse response = clubEventService.createEvent(
                clubId,
                userId,
                request
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.CLUB_EVENT_CREATE_SUCCESS,
                        response
                )
        );
    }

    @GetMapping
    @Operation(
            summary = "동아리 월별 일정 조회",
            description = "동아리에 가입 완료된 사용자가 특정 연도와 월의 일정을 조회합니다."
    )
    public ResponseEntity<ApiResponse<List<ClubEventListResponse>>> getMonthlyEvents(
            @PathVariable Long clubId,
            @AuthenticationPrincipal Long userId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        List<ClubEventListResponse> response = clubEventService.getMonthlyEvents(
                clubId,
                userId,
                year,
                month
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.CLUB_EVENT_LIST_GET_SUCCESS,
                        response
                )
        );
    }

    @GetMapping("/{eventId}")
    @Operation(
            summary = "동아리 일정 상세 조회",
            description = "동아리에 가입 완료된 사용자가 특정 일정의 상세 정보를 조회합니다."
    )
    public ResponseEntity<ApiResponse<ClubEventResponse>> getEvent(
            @PathVariable Long clubId,
            @PathVariable Long eventId,
            @AuthenticationPrincipal Long userId
    ) {
        ClubEventResponse response = clubEventService.getEvent(
                clubId,
                eventId,
                userId
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.CLUB_EVENT_DETAIL_GET_SUCCESS,
                        response
                )
        );
    }

    @PatchMapping("/{eventId}")
    @Operation(
            summary = "동아리 일정 수정",
            description = "동아리 대표 또는 운영진이 기존 일정을 수정합니다."
    )
    public ResponseEntity<ApiResponse<ClubEventResponse>> updateEvent(
            @PathVariable Long clubId,
            @PathVariable Long eventId,
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody ClubEventUpdateRequest request
    ) {
        ClubEventResponse response = clubEventService.updateEvent(
                clubId,
                eventId,
                userId,
                request
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.CLUB_EVENT_UPDATE_SUCCESS,
                        response
                )
        );
    }

    @DeleteMapping("/{eventId}")
    @Operation(
            summary = "동아리 일정 삭제",
            description = "동아리 대표 또는 운영진이 기존 일정을 삭제합니다."
    )
    public ResponseEntity<ApiResponse<Void>> deleteEvent(
            @PathVariable Long clubId,
            @PathVariable Long eventId,
            @AuthenticationPrincipal Long userId
    ) {
        clubEventService.deleteEvent(
                clubId,
                eventId,
                userId
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.CLUB_EVENT_DELETE_SUCCESS,
                        null
                )
        );
    }
}