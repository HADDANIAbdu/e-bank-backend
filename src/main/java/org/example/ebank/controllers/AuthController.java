package org.example.ebank.controllers;


import lombok.AllArgsConstructor;
import org.example.ebank.dtos.AddRoleDTO;
import org.example.ebank.dtos.UserDTO;
import org.example.ebank.entities.AppRole;
import org.example.ebank.entities.AppUser;
import org.example.ebank.services.UserServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private JwtEncoder jwtEncoder;
    private AuthenticationManager authenticationManager;
    private UserServiceImpl userService;

    @GetMapping("/profile")
    public Authentication infos(Authentication authentication){
        return authentication;
    }
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody UserDTO userDTO){
        if (userDTO.getUsername() != null){
            userService.addUser(
                    userDTO.getUsername(),
                    userDTO.getPassword(),
                    userDTO.getEmail(),
                    userDTO.getConfirmPassword()
            );
            return  Map.of("status","success",
                    "message", "User Added Successfully !");
        }
        return  Map.of("status","error",
                "message", "Error adding user!");
    }
    @PostMapping("/login")
    public Map<String,String> login(@RequestBody UserDTO userDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
        );
        Instant now=Instant.now();
        String scope= authentication.getAuthorities()
                .stream().map(auth->auth.getAuthority())
                .collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                .issuedAt(now)
                .subject(authentication.getName())
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .claim("scope",scope)
                .build();
        JwtEncoderParameters jwtEncoderParameters=
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(),
                        jwtClaimsSet
                );
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);
        return Map.of("access-token",jwt.getTokenValue());
    }

    @DeleteMapping("/delete/{Id}")
    public void deleteUser(@PathVariable Long Id){
        userService.deleteUser(Id);
    }

    @PostMapping("/add-role")
    public Map<String,String> addRole(@RequestBody AppRole appRole){
        if(appRole.getRole() != null){
            userService.addRole(appRole.getRole());
            return  Map.of("status","success",
                    "message", "Role Added Successfully !");
        }
        return  Map.of("status","error",
                "message", "Error adding role!");
    }

    @PostMapping("/add-role-user")
    public Map<String,String> addRoleUser(@RequestBody AddRoleDTO addRoleDTO){
        if(addRoleDTO.getRole() != null && addRoleDTO.getUsername() != null){
            userService.AddRoleToUser(addRoleDTO.getUsername(), addRoleDTO.getRole());
            return  Map.of("status","success",
                    "message", "Role Added to user Successfully !");
        }
        return  Map.of("status","error",
                "message", "Error adding role to user!");
    }
    @PostMapping("/delete-role-user")
    public Map<String,String> deleteRoleUser(@RequestBody AddRoleDTO addRoleDTO){
        if(addRoleDTO.getRole() != null && addRoleDTO.getUsername() != null){
            userService.RemoveRoleFromUser(addRoleDTO.getUsername(), addRoleDTO.getRole());
            return  Map.of("status","success",
                    "message", "Role deleted from user Successfully !");
        }
        return  Map.of("status","error",
                "message", "Error deleting role from user!");
    }

}
