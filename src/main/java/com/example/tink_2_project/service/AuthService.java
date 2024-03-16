package com.example.tink_2_project.service;

import com.example.tink_2_project.domain.Account;
import com.example.tink_2_project.domain.TokenEntity;
import com.example.tink_2_project.exception.AccountAlreadyExistException;
import com.example.tink_2_project.exception.BadTokenException;
import com.example.tink_2_project.exception.LoginFailException;
import com.example.tink_2_project.repository.AccountRepository;
import com.example.tink_2_project.repository.TokenRepository;
import com.example.tink_2_project.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ServiceResponse login(String nickname, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(nickname, password)
            );
            var account = accountRepository.findByNickname(nickname).orElseThrow(LoginFailException::new);
            return new ServiceResponse(account,
                    tokenRepository.save(new TokenEntity(null, jwtService.generateRefreshToken(account), account)).getToken(),
                    jwtService.generateAccessToken(account));
        } catch (AuthenticationException e) {
            throw new LoginFailException();
        }
    }

    @Transactional
    public ServiceResponse register(String nickname, String password) throws AccountAlreadyExistException {
        if (accountRepository.findByNickname(nickname).isPresent()) {
            throw new AccountAlreadyExistException(nickname);
        }
        var account = new Account();
        account.setNickname(nickname);
        account.setPassword(passwordEncoder.encode(password));
        account.setRole(Account.Role.ROLE_USER);
        var savedAccount = accountRepository.save(account);
        return new ServiceResponse(account,
                tokenRepository.save(new TokenEntity(null, jwtService.generateRefreshToken(savedAccount), savedAccount)).getToken(),
                jwtService.generateAccessToken(savedAccount));
    }

    @Transactional
    public Pair<String, String> refreshToken(String oldToken) {
        var token = tokenRepository.findByToken(oldToken);
        if (token.isEmpty()) {
            throw new BadTokenException();
        }
        var account = token.get().getAccount();
        token.get().setToken(jwtService.generateRefreshToken(account));
        return Pair.of(tokenRepository.save(token.get()).getToken(), jwtService.generateAccessToken(account));
    }

    @Transactional
    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    @Transactional
    public void registerAdmin(Account account) {
        if (accountRepository.findByNickname(account.getNickname()).isEmpty()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            accountRepository.save(account);
        }
    }

    public record ServiceResponse(Account account,
                                  String refreshToken,
                                  String accessToken) {
    }
}