package com.example.darts.repository;

import com.example.darts.model.entity.Account;
import com.example.darts.service.DartsAccountDetailsService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    boolean existsByCompanies_email(String email);
}
