package org.example.ebank.services;

import lombok.AllArgsConstructor;
import org.example.ebank.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userService.loadUserByUsername(username);
        if(user == null) throw new UsernameNotFoundException(String.format("User %s not found", username));
        String[] roles = user.getRoles().stream().map(u-> u.getRole()).toArray(String[]::new);
        UserDetails userDetails = User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .build();
        return userDetails;
    }
}
