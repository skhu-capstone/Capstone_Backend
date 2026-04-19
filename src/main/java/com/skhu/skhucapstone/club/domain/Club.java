package com.skhu.skhucapstone.club.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clubName;

    private String description;

    private boolean isApproved;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public Club(String clubName, String description) {
        this.clubName = clubName;
        this.description = description;
        this.isApproved = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String clubName, String description) {
        this.clubName = clubName;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }


    public void setApproveStatus(boolean status) {
        this.isApproved = status;
        this.updatedAt = LocalDateTime.now();
    }
}