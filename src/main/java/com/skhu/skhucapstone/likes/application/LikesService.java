package com.skhu.skhucapstone.likes.application;

import com.skhu.skhucapstone.common.exception.CustomException;
import com.skhu.skhucapstone.common.exception.ErrorCode;
import com.skhu.skhucapstone.likes.api.dto.response.LikeResponse;
import com.skhu.skhucapstone.likes.domain.Likes;
import com.skhu.skhucapstone.likes.domain.repository.LikesRepository;
import com.skhu.skhucapstone.post.domain.Post;
import com.skhu.skhucapstone.post.domain.repository.PostRepository;
import com.skhu.skhucapstone.user.entity.User;
import com.skhu.skhucapstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public LikeResponse toggleLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return likesRepository.findByPostAndUser(post, user)
                .map(like -> {
                    likesRepository.delete(like);

                    long likeCount = likesRepository.countByPost(post);

                    return LikeResponse.builder()
                            .liked(false)
                            .likeCount(likeCount)
                            .build();
                })
                .orElseGet(() -> {
                    Likes like = Likes.builder()
                            .post(post)
                            .user(user)
                            .createdAt(LocalDateTime.now())
                            .build();

                    likesRepository.save(like);

                    long likeCount = likesRepository.countByPost(post);

                    return LikeResponse.builder()
                            .liked(true)
                            .likeCount(likeCount)
                            .build();
                });
    }
}