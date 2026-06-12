package com.skhu.skhucapstone.projectrecruitment.entity;

import com.skhu.skhucapstone.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProjectRecruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectRecruitmentId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 1000)
    private String imageUrl;

    @Column(nullable = false, length = 100)
    private String writerStack;

    @Column(nullable = false, length = 100)
    private String positions;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void updateRecruitment(
            String title,
            String imageUrl,
            String writerStack,
            String positions,
            String content,
            LocalDate deadline
    ) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.writerStack = writerStack;
        this.positions = positions;
        this.content = content;
        this.deadline = deadline;
        this.updatedAt = LocalDateTime.now();
    }
}
