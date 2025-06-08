
package com.justsearch.backend.service;

import org.springframework.http.ResponseEntity;

import com.justsearch.backend.dto.SignIn;
import com.justsearch.backend.dto.SignupRequest;
import com.justsearch.backend.dto.TokenResponse;

public interface AuthService {

    void userSignUp(SignupRequest signUpRequest);

    ResponseEntity<?> userSignIn(SignIn request);

    ResponseEntity<TokenResponse> refresh(String refreshToken);

    void logout(String refreshToken);
}