package com.olx.olx_saas.repository;

import com.olx.olx_saas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

   
    Optional<User> findByEmail(String email);
}