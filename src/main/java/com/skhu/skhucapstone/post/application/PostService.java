package com.skhu.skhucapstone.post.application;

import com.skhu.skhucapstone.club.domain.Club;
import com.skhu.skhucapstone.club.domain.repository.ClubRepository;
import com.skhu.skhucapstone.clubmember.domain.ClubMember;
import com.skhu.skhucapstone.clubmember.domain.ClubRole;
import com.skhu.skhucapstone.clubmember.domain.repository.ClubMemberRepository;
import com.skhu.skhucapstone.common.exception.CustomException;
import com.skhu.skhucapstone.common.exception.ErrorCode;
import com.skhu.skhucapstone.post.api.dto.request.PostCreateRequest;
import com.skhu.skhucapstone.post.api.dto.request.PostUpdateRequest;
import com.skhu.skhucapstone.post.api.dto.response.PostPageResponse;
import com.skhu.skhucapstone.post.api.dto.response.PostResponse;
import com.skhu.skhucapstone.post.domain.Post;
import com.skhu.skhucapstone.post.domain.PostImage;
import com.skhu.skhucapstone.post.domain.repository.PostImageRepository;
import com.skhu.skhucapstone.post.domain.repository.PostRepository;
import com.skhu.skhucapstone.user.entity.User;
import com.skhu.skhucapstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;

    @Transactional
    public PostResponse createPost(Long clubId, Long userId, PostCreateRequest request) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ClubMember clubMember = clubMemberRepository.findByClubAndUser(club, user)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_WRITE_FORBIDDEN));

        if (clubMember.getRole() != ClubRole.STAFF &&
                clubMember.getRole() != ClubRole.PRESIDENT) {
            throw new CustomException(ErrorCode.POST_WRITE_FORBIDDEN);
        }

        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .postType(request.postType())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .club(club)
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);

        savePostImages(savedPost, request.imageUrls());

        return toPostResponse(savedPost);
    }

    public PostPageResponse getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);

        return PostPageResponse.builder()
                .content(posts.getContent()
                        .stream()
                        .map(this::toPostResponse)
                        .toList())
                .page(posts.getNumber())
                .size(posts.getSize())
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .last(posts.isLast())
                .build();
    }

    public PostPageResponse getClubPosts(Long clubId, int page, int size) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);

        Page<Post> posts = postRepository.findByClubOrderByCreatedAtDesc(club, pageable);

        return PostPageResponse.builder()
                .content(posts.getContent()
                        .stream()
                        .map(this::toPostResponse)
                        .toList())
                .page(posts.getNumber())
                .size(posts.getSize())
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .last(posts.isLast())
                .build();
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        return toPostResponse(post);
    }

    @Transactional
    public PostResponse updatePost(Long postId, Long userId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.POST_UPDATE_FORBIDDEN);
        }

        post.updatePost(
                request.title(),
                request.content(),
                request.postType()
        );

        postImageRepository.deleteByPost(post);
        savePostImages(post, request.imageUrls());

        return toPostResponse(post);
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        boolean isWriter = post.getUser().getUserId().equals(userId);

        ClubMember clubMember = clubMemberRepository.findByClubAndUser(post.getClub(), user)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_DELETE_FORBIDDEN));

        boolean isManager = clubMember.getRole() == ClubRole.STAFF ||
                clubMember.getRole() == ClubRole.PRESIDENT;

        if (!isWriter && !isManager) {
            throw new CustomException(ErrorCode.POST_DELETE_FORBIDDEN);
        }

        postImageRepository.deleteByPost(post);
        postRepository.delete(post);
    }

    private void savePostImages(Post post, List<String> imageUrls) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            return;
        }

        List<PostImage> postImages = new ArrayList<>();

        for (int i = 0; i < imageUrls.size(); i++) {
            PostImage postImage = PostImage.builder()
                    .post(post)
                    .imageUrl(imageUrls.get(i))
                    .imageOrder(i)
                    .build();

            postImages.add(postImage);
        }

        postImageRepository.saveAll(postImages);
    }

    private PostResponse toPostResponse(Post post) {
        List<String> imageUrls = postImageRepository.findByPostOrderByImageOrderAsc(post)
                .stream()
                .map(PostImage::getImageUrl)
                .toList();

        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrls(imageUrls)
                .postType(post.getPostType())
                .writerName(post.getUser().getName())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public PostPageResponse getRecommendedPosts(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Post> posts =
                postRepository.findAllOrderByLikeCountDesc(pageable);

        return PostPageResponse.builder()
                .content(posts.getContent()
                        .stream()
                        .map(this::toPostResponse)
                        .toList())
                .page(posts.getNumber())
                .size(posts.getSize())
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .last(posts.isLast())
                .build();
    }
}