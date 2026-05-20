package com.skhu.skhucapstone.coffeechat.repository;

import com.skhu.skhucapstone.coffeechat.entity.CoffeeChatProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CoffeeChatProfileRepository extends JpaRepository<CoffeeChatProfile, Long> {

    Optional<CoffeeChatProfile> findByUserUserId(Long userId);

    @Query("SELECT c FROM CoffeeChatProfile c WHERE c.isPublic = true " +
            "AND (:keyword IS NULL OR c.headline LIKE %:keyword% " +
            "OR c.interestTopics LIKE %:keyword% " +
            "OR c.user.name LIKE %:keyword%)")
    Page<CoffeeChatProfile> searchProfiles(@Param("keyword") String keyword, Pageable pageable);
}
