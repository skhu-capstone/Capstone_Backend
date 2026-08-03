package com.skhu.skhucapstone.coffeechat.service;

import com.skhu.skhucapstone.clubmember.domain.repository.ClubMemberRepository;
import com.skhu.skhucapstone.coffeechat.entity.CoffeeChatProfile;
import com.skhu.skhucapstone.coffeechat.repository.CoffeeChatProfileRepository;
import com.skhu.skhucapstone.common.exception.CustomException;
import com.skhu.skhucapstone.common.exception.ErrorCode;
import com.skhu.skhucapstone.common.file.ImageUploadService;
import com.skhu.skhucapstone.user.entity.User;
import com.skhu.skhucapstone.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// 커피챗 프로필 이미지의 업로드·삭제 동작과 본인 확인 규칙을 검증한다.
@ExtendWith(MockitoExtension.class)
class CoffeeChatServiceTest {

    @Mock
    private CoffeeChatProfileRepository coffeeChatProfileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClubMemberRepository clubMemberRepository;

    @Mock
    private ImageUploadService imageUploadService;

    @InjectMocks
    private CoffeeChatService coffeeChatService;

    private static final Long OWNER_ID = 1L;
    private static final Long OTHER_ID = 2L;
    private static final String OLD_IMAGE = "https://objectstorage.example.com/o/coffeechat%2Fold.png";
    private static final String NEW_IMAGE = "https://objectstorage.example.com/o/coffeechat%2Fnew.png";

    private CoffeeChatProfile profile(String imageUrl) {
        return CoffeeChatProfile.builder()
                .id(100L)
                .user(User.builder().userId(OWNER_ID).name("정다운").build())
                .studentId("202012345")
                .headline("백엔드 개발자입니다")
                .interestTopics("백엔드")
                .contactLink("https://example.com")
                .profileImageUrl(imageUrl)
                .build();
    }

    private MultipartFile image() {
        return new MockMultipartFile("file", "사진.png", "image/png", new byte[]{1, 2, 3});
    }

    @Test
    @DisplayName("이미지를 업로드하면 기존 이미지를 지우고 새 주소로 교체한다")
    void uploadReplacesExistingImage() {
        CoffeeChatProfile profile = profile(OLD_IMAGE);
        when(coffeeChatProfileRepository.findByUserUserId(OWNER_ID)).thenReturn(Optional.of(profile));
        when(imageUploadService.upload(any(), eq("coffeechat"))).thenReturn(NEW_IMAGE);

        String result = coffeeChatService.uploadProfileImage(OWNER_ID, OWNER_ID, image());

        assertThat(result).isEqualTo(NEW_IMAGE);
        assertThat(profile.getProfileImageUrl()).isEqualTo(NEW_IMAGE);
        verify(imageUploadService).delete(OLD_IMAGE); // 기존 파일이 남지 않아야 한다
    }

    @Test
    @DisplayName("기존 이미지가 없으면 삭제를 시도하지 않고 업로드만 한다")
    void uploadWithoutExistingImageSkipsDelete() {
        when(coffeeChatProfileRepository.findByUserUserId(OWNER_ID))
                .thenReturn(Optional.of(profile(null)));
        when(imageUploadService.upload(any(), eq("coffeechat"))).thenReturn(NEW_IMAGE);

        coffeeChatService.uploadProfileImage(OWNER_ID, OWNER_ID, image());

        verify(imageUploadService, never()).delete(anyString());
    }

    @Test
    @DisplayName("다른 사용자의 프로필 이미지는 업로드할 수 없다")
    void uploadOtherUsersImageIsForbidden() {
        assertThatThrownBy(() -> coffeeChatService.uploadProfileImage(OWNER_ID, OTHER_ID, image()))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.COFFEECHAT_PROFILE_IMAGE_FORBIDDEN);

        // 권한 검사에서 막히므로 저장소나 스토리지에 접근하지 않아야 한다
        verify(coffeeChatProfileRepository, never()).findByUserUserId(any());
        verify(imageUploadService, never()).upload(any(), anyString());
    }

    @Test
    @DisplayName("이미지를 삭제하면 스토리지에서 지우고 프로필의 주소도 비운다")
    void deleteRemovesImageAndClearsUrl() {
        CoffeeChatProfile profile = profile(OLD_IMAGE);
        when(coffeeChatProfileRepository.findByUserUserId(OWNER_ID)).thenReturn(Optional.of(profile));

        coffeeChatService.deleteProfileImage(OWNER_ID, OWNER_ID);

        verify(imageUploadService).delete(OLD_IMAGE);
        assertThat(profile.getProfileImageUrl()).isNull();
    }

    @Test
    @DisplayName("이미지가 없는 상태에서 삭제해도 예외 없이 넘어간다")
    void deleteWithoutImageDoesNothing() {
        when(coffeeChatProfileRepository.findByUserUserId(OWNER_ID))
                .thenReturn(Optional.of(profile(null)));

        coffeeChatService.deleteProfileImage(OWNER_ID, OWNER_ID);

        verify(imageUploadService, never()).delete(anyString());
    }

    @Test
    @DisplayName("다른 사용자의 프로필 이미지는 삭제할 수 없다")
    void deleteOtherUsersImageIsForbidden() {
        assertThatThrownBy(() -> coffeeChatService.deleteProfileImage(OWNER_ID, OTHER_ID))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.COFFEECHAT_PROFILE_IMAGE_FORBIDDEN);

        verify(imageUploadService, never()).delete(anyString());
    }

    @Test
    @DisplayName("로그인하지 않은 요청은 이미지를 변경할 수 없다")
    void anonymousRequestIsForbidden() {
        assertThatThrownBy(() -> coffeeChatService.deleteProfileImage(null, OWNER_ID))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.COFFEECHAT_PROFILE_IMAGE_FORBIDDEN);
    }

    @Test
    @DisplayName("프로필이 없는 사용자는 이미지를 삭제할 수 없다")
    void deleteWithoutProfileThrows() {
        when(coffeeChatProfileRepository.findByUserUserId(OWNER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> coffeeChatService.deleteProfileImage(OWNER_ID, OWNER_ID))
                .isInstanceOf(CustomException.class)
                .extracting(e -> ((CustomException) e).getErrorCode())
                .isEqualTo(ErrorCode.COFFEECHAT_PROFILE_NOT_FOUND);
    }
}
