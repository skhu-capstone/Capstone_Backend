package com.skhu.skhucapstone.likes.domain.repository;

import com.skhu.skhucapstone.likes.domain.Likes;
import com.skhu.skhucapstone.post.domain.Post;
import com.skhu.skhucapstone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByPostAndUser(Post post, User user);

    long countByPost(Post post);
}