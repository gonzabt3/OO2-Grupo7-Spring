package com.grupo7.oo2spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo7.oo2spring.models.EmailToken;

public interface IEmailTokenRepository extends JpaRepository<EmailToken, Integer> {
    EmailToken findByToken(String token);
}
