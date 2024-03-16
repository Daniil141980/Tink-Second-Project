package com.example.tink_2_project.config;

import com.example.tink_2_project.domain.Account;
import com.example.tink_2_project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final AuthService authService;

    @Value("${admin.nickname}")
    private String nickname;

    @Value("${admin.password}")
    private String password;

    @Override
    public void run(ApplicationArguments args) {
        registerAdmin();
    }


    private void registerAdmin() {
        authService.registerAdmin(
                new Account(null,
                        nickname,
                        password,
                        Account.Role.ROLE_ADMIN,
                        null));
    }

}