package com.skhu.skhucapstone.club.api;

import com.skhu.skhucapstone.club.api.dto.Request.ClubCreateRequest;
import com.skhu.skhucapstone.club.api.dto.Response.ClubResponse;
import com.skhu.skhucapstone.club.application.ClubService;
import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
@Tag(name = "Club", description = "동아리 관리 API")
public class ClubController {

    private final ClubService clubService;

    @PostMapping
    @Operation(
            summary = "동아리 생성",
            description = "새로운 동아리를 생성합니다. 생성된 동아리는 별도 승인 없이 즉시 서비스에 노출됩니다."
    )
    public ResponseEntity<ApiResponse<ClubResponse>> createClub(
            @Valid @RequestBody ClubCreateRequest request) {

        ClubResponse response = clubService.createClub(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.CLUB_CREATE_SUCCESS,
                        response
                )
        );
    }

    @GetMapping
    @Operation(
            summary = "동아리 목록 조회",
            description = "서비스에 등록된 전체 동아리 목록을 조회합니다."
    )
    public ResponseEntity<ApiResponse<List<ClubResponse>>> getClubs() {

        List<ClubResponse> response = clubService.getClubs();

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.CLUB_LIST_GET_SUCCESS,
                        response
                )
        );
    }

    @GetMapping("/{clubId}")
    @Operation(
            summary = "동아리 상세 조회",
            description = "동아리 ID를 이용하여 특정 동아리의 상세 정보를 조회합니다."
    )
    public ResponseEntity<ApiResponse<ClubResponse>> getClub(
            @PathVariable Long clubId) {

        ClubResponse response = clubService.getClub(clubId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.CLUB_DETAIL_GET_SUCCESS,
                        response
                )
        );
    }

    @PostMapping(value = "/{clubId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "동아리 이미지 업로드",
            description = "동아리 이미지를 업로드합니다."
    )
    public ResponseEntity<ApiResponse<String>> uploadClubImage(
            @PathVariable Long clubId,
            @RequestPart MultipartFile file) {

        String imageUrl = clubService.uploadClubImage(clubId, file);

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.CLUB_IMAGE_UPLOAD_SUCCESS,
                        imageUrl
                )
        );
    }
}