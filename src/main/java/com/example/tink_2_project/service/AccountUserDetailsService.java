package com.example.tink_2_project.service;

import com.example.tink_2_project.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        return accountRepository.findByNickname(nickname).orElseThrow(
                () -> new UsernameNotFoundException("Пользователя с ником " + nickname + " не было найдено")
        );
    }
}