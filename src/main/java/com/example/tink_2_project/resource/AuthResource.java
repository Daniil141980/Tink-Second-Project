package com.example.tink_2_project.resource;

import com.example.tink_2_project.dto.TokenDto;
import com.example.tink_2_project.dto.account.AuthRequestDto;
import com.example.tink_2_project.dto.account.AuthResponseDto;
import com.example.tink_2_project.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthResource {
    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponseDto registerUserAccount(@RequestBody AuthRequestDto request, HttpServletResponse response) {
        var answer = authService.register(request.nickname().trim(), request.password().trim());
        addCookie(answer.accessToken(), response);
        return new AuthResponseDto(answer.account().getNickname(), answer.account().getRole().getDescription(), answer.refreshToken());
    }

    @PostMapping("/login")
    public AuthResponseDto loginUserAccount(@RequestBody AuthRequestDto request, HttpServletResponse response) {
        var answer = authService.login(request.nickname().trim(), request.password().trim());
        addCookie(answer.accessToken(), response);
        return new AuthResponseDto(answer.account().getNickname(), answer.account().getRole().getDescription(), answer.refreshToken());
    }

    @PostMapping("/logout")
    public void logoutUserAccount(@RequestBody TokenDto request, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        SecurityContextHolder.clearContext();
        authService.deleteToken(request.token());
    }

    @PostMapping("/refresh")
    public TokenDto refreshToken(@RequestBody TokenDto request, HttpServletResponse response) {
        var answer = authService.refreshToken(request.token());
        addCookie(answer.getSecond(), response);
        return new TokenDto(answer.getFirst());
    }

    private void addCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(5 * 60);
        response.addCookie(cookie);
    }
}