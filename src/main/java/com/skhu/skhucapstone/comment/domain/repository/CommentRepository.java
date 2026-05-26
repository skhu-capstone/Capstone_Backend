package com.skhu.skhucapstone.comment.domain.repository;

import com.skhu.skhucapstone.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import com.skhu.skhucapstone.post.domain.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByCreatedAtAsc(Post post);
}