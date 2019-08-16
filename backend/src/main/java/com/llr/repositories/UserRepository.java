package com.llr.repositories;

import com.llr.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);

    User findByEmail(String email);

    Optional<User> findByIdAndPassword(Long userId, String password);

    List<User> findUserByEmailIn(List<String> emails);

}
