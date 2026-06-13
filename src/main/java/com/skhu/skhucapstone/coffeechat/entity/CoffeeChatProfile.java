package com.skhu.skhucapstone.coffeechat.entity;

import com.skhu.skhucapstone.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Table(name = "coffeechat_profile")
public class CoffeeChatProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String headline;

    @Column(nullable = false)
    private String interestTopics;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeetingType meetingType;

    @Column(nullable = false)
    private String contactLink;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Column
    private String profileImageUrl;

    @Builder.Default
    @ColumnDefault("true")
    @Column(nullable = false)
    private Boolean isPublic = true;

    public void update(String studentId, String headline, String interestTopics, MeetingType meetingType, String contactLink, String introduction, String profileImageUrl, Boolean isPublic) {
        this.studentId = studentId;
        this.headline = headline;
        this.interestTopics = interestTopics;
        this.meetingType = meetingType;
        this.contactLink = contactLink;
        this.introduction = introduction;
        this.profileImageUrl = profileImageUrl;
        this.isPublic = isPublic;
    }

    public void updateProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    // 커피챗 공개 유무
    public void updateVisibility(Boolean isPublic) {
        this.isPublic = isPublic;
    }
}
