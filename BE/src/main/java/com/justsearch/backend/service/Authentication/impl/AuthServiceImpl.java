package com.justsearch.backend.service.Authentication.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.justsearch.backend.dto.SignInDto;
import com.justsearch.backend.dto.SignupRequestDto;
import com.justsearch.backend.dto.TokenResponseDto;
import com.justsearch.backend.model.RefreshToken;
import com.justsearch.backend.model.User;
import com.justsearch.backend.repository.RefreshTokenRepository;
import com.justsearch.backend.repository.UserRepository;
import com.justsearch.backend.security.JwtUtils;
import com.justsearch.backend.service.Authentication.AuthService;




@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository _userRepository;

    private RefreshTokenRepository _refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, JwtUtils jwtUtils,
            RefreshTokenRepository refreshTokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this._refreshTokenRepository = refreshTokenRepository;
    }

    public void userSignUp(SignupRequestDto signUpRequest) {

        if (_userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email already taken");
        }
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setPhone(signUpRequest.getPhone());
        _userRepository.save(user);
    }

    public ResponseEntity<?> userSignIn(SignInDto request) {
        Optional<User> userOptional = _userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "User not found");
            errorResponse.put("error", "INVALID_EMAIL");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorResponse);
        }
        User user = userOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid password");
            errorResponse.put("error", "INVALID_PASSWORD");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(errorResponse); // ← Return JSON object instead of null
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), new ArrayList<>());
        String token = jwtUtils.generateToken(userDetails);
        RefreshToken refreshToken = jwtUtils.generateRefreshToken(user.getId());
        _refreshTokenRepository.save(refreshToken);
        TokenResponseDto tokenResponse = new TokenResponseDto(user.getName(), token, refreshToken.getToken(),
                user.getId());
        return ResponseEntity.ok(tokenResponse);
    }

    @Transactional
    public ResponseEntity<TokenResponseDto> refresh(String refreshToken) {
        if (jwtUtils.validateRefreshToken(refreshToken)) {
            long userId = jwtUtils.getUserIdFromRefreshToken(refreshToken);
            User user = _userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            _refreshTokenRepository.deleteByToken(refreshToken);
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(), new ArrayList<>());
            String newAccessToken = jwtUtils.generateToken(userDetails);
            RefreshToken newRefreshToken = jwtUtils.generateRefreshToken(user.getId());
            TokenResponseDto tokenResponse = new TokenResponseDto(user.getName(), newAccessToken,
                    newRefreshToken.getToken(),
                    user.getId());
            _refreshTokenRepository.save(newRefreshToken);
            return ResponseEntity.ok(tokenResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    public void logout(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new RuntimeException("Refresh token is required");
        }
        RefreshToken refreshTokenEntity = _refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        refreshTokenEntity.setRevoked(true);
        _refreshTokenRepository.save(refreshTokenEntity);
    }

}