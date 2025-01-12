package com.example.usermanagementservice.repositories;

import com.example.usermanagementservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    // select * from token where value = <?> and isDeleted = false
    Optional<Token> findByValueAndDeleted(String value, Boolean isDeleted);
}
