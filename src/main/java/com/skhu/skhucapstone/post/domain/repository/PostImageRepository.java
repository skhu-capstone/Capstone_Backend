package com.skhu.skhucapstone.post.domain.repository;

import com.skhu.skhucapstone.post.domain.Post;
import com.skhu.skhucapstone.post.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    List<PostImage> findByPostOrderByImageOrderAsc(Post post);

    void deleteByPost(Post post);
}