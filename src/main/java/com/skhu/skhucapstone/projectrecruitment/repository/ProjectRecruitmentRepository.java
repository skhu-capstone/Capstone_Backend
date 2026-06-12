package com.skhu.skhucapstone.projectrecruitment.repository;

import com.skhu.skhucapstone.projectrecruitment.entity.ProjectRecruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRecruitmentRepository extends JpaRepository<ProjectRecruitment, Long> {

    @Query("""
        SELECT p FROM ProjectRecruitment p
        WHERE (:keyword IS NULL
            OR p.title LIKE CONCAT('%', :keyword, '%')
            OR p.content LIKE CONCAT('%', :keyword, '%')
            OR p.positions LIKE CONCAT('%', :keyword, '%'))
        ORDER BY p.createdAt DESC
        """)
    Page<ProjectRecruitment> searchRecruitments(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
