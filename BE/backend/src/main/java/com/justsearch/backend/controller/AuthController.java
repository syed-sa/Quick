package com.justsearch.backend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.justsearch.backend.dto.SignIn;
import com.justsearch.backend.dto.SignupRequest;
import com.justsearch.backend.service.AuthService;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("api/user")
public class AuthController {
    @Autowired
    private  AuthService _authService;
    public  AuthController ( AuthService authService)
    
    {
        _authService = authService;

    }

@PostMapping("/signup")

    public ResponseEntity<Map<String,String>> SignupUser (@RequestBody SignupRequest request)
    {  
    try 
    {      
         _authService.userSignUp(request);
         return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
     } 
     catch (RuntimeException ex) 
     {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }        
    }


    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignIn signInCredentials) 
    {
    System.out.println("Password: " + signInCredentials.getPassword());
    System.out.println("UserInput: " + signInCredentials.getEmail());
    return _authService.userSignIn(signInCredentials);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refresh(@RequestBody  String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Refresh token is required"));
        }
        try 
        {
            return _authService.refresh(refreshToken);
        } 
        catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }       
    }

 @PostMapping("/logout")
public ResponseEntity<Map<String, String>> logout(@RequestHeader("x-refresh-token") String refreshToken) {
    try {
        _authService.logout(refreshToken);
        return ResponseEntity.ok(Map.of("message", "User logged out successfully!"));
    } catch (RuntimeException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }
}

    
    
        
}