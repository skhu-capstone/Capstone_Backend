package com.skhu.skhucapstone.post.application;

import com.skhu.skhucapstone.club.domain.Club;
import com.skhu.skhucapstone.club.domain.repository.ClubRepository;
import com.skhu.skhucapstone.clubmember.domain.ClubMember;
import com.skhu.skhucapstone.clubmember.domain.ClubRole;
import com.skhu.skhucapstone.clubmember.domain.repository.ClubMemberRepository;
import com.skhu.skhucapstone.post.api.dto.request.PostCreateRequest;
import com.skhu.skhucapstone.post.api.dto.request.PostUpdateRequest;
import com.skhu.skhucapstone.post.api.dto.response.PostResponse;
import com.skhu.skhucapstone.post.domain.Post;
import com.skhu.skhucapstone.post.domain.repository.PostRepository;
import com.skhu.skhucapstone.post.exception.PostException;
import com.skhu.skhucapstone.user.entity.User;
import com.skhu.skhucapstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;

    public PostResponse createSomePostThing(
            Long clubId,
            Long userId,
            PostCreateRequest request
    ) {

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new PostException("동아리를 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new PostException("유저를 찾을 수 없습니다."));

        ClubMember clubMember = clubMemberRepository
                .findByClubAndUser(club, user)
                .orElseThrow(() -> new PostException("동아리 멤버가 아닙니다."));

        if (clubMember.getRole() != ClubRole.STAFF &&
                clubMember.getRole() != ClubRole.PRESIDENT) {

            throw new PostException("게시글 작성 권한이 없습니다.");
        }

        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .imageUrl(request.imageUrl())
                .postType(request.postType())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .club(club)
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);

        return PostResponse.builder()
                .postId(savedPost.getPostId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .imageUrl(savedPost.getImageUrl())
                .postType(savedPost.getPostType())
                .writerName(savedPost.getUser().getName())
                .createdAt(savedPost.getCreatedAt())
                .build();
    }
}