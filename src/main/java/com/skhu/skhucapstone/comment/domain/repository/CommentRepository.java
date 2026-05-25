package com.skhu.skhucapstone.comment.domain.repository;

import com.skhu.skhucapstone.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}