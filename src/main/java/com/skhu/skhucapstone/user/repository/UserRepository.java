package com.skhu.skhucapstone.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.skhu.skhucapstone.user.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsBySchoolEmail(String schoolEmail);
}
