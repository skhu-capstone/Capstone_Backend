package com.skhu.skhucapstone.main.api;

import com.skhu.skhucapstone.common.exception.SuccessCode;
import com.skhu.skhucapstone.common.response.ApiResponse;
import com.skhu.skhucapstone.main.api.dto.response.MainResponse;
import com.skhu.skhucapstone.main.application.MainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
@Tag(name = "Main", description = "메인페이지 API")
public class MainController {

    private final MainService mainService;

    @GetMapping
    @Operation(
            summary = "메인페이지 조회",
            description = "메인페이지에 필요한 데이터를 조회합니다."
    )
    public ResponseEntity<ApiResponse<MainResponse>> getMainPage() {
        MainResponse response = mainService.getMainPage();

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MAIN_GET_SUCCESS, response));
    }
}