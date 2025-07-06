package com.justsearch.backend.security;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.justsearch.backend.model.User;
import com.justsearch.backend.repository.UserRepository;


@Component
public class CustomUserDetailsService implements UserDetailsService {
private UserRepository _UserRepository ;
    public CustomUserDetailsService(UserRepository userRepository) {
        this._UserRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = _UserRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
         return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), new ArrayList<>() // Add roles if needed
        );
    }
    


}
