package com.example.tink_2_project.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponseDto(String nickname, String role, @JsonProperty("refresh_token") String refreshToken) {
}
