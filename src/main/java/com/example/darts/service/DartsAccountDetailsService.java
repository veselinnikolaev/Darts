package com.example.darts.service;

import com.example.darts.model.entity.Account;
import com.example.darts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class DartsAccountDetailsService implements UserDetailsService {
    private final AccountRepository repository;
    private static UserDetails map(Account account) {
        return User
                .withUsername(account.getEmail())
                .password(account.getPassword())
                //.authorities()
                .build();
    }

//    private static GrantedAuthority map(Role role) {
//        return new SimpleGrantedAuthority(
//                "ROLE_" + role.getAuthority().name()
//        );
//    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository
                .findByEmail(email)
                .map(DartsAccountDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found!".formatted(email)));
    }
}
