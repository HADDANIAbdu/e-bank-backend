package org.example.ebank.services;

import lombok.AllArgsConstructor;
import org.example.ebank.entities.AppRole;
import org.example.ebank.entities.AppUser;
import org.example.ebank.repositories.AppRoleRepo;
import org.example.ebank.repositories.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private AppRoleRepo appRoleRepo;

    @Override
    public AppUser addUser(String username, String password, String email, String confirmPassword) {
        AppUser user = userRepo.findByUsername(username);
        if(user != null) throw new RuntimeException("User already exists");
        if(!password.equals(confirmPassword)) throw new RuntimeException("Passwords do not match");
        user = AppUser.builder().username(username)
                .password(password)
                .email(email).build();
        userRepo.save(user);
        return user;
    }

    @Override
    public AppRole addRole(String role) {
        AppRole appRole = appRoleRepo.findByRole(role);
        if(appRole != null) throw new RuntimeException("Role already exists");
        return appRoleRepo.save(AppRole.builder().role(role).build());
    }

    @Override
    public void AddRoleToUser(String username, String role) {
        AppUser user = userRepo.findByUsername(username);
        AppRole appRole = appRoleRepo.findByRole(role);
        user.getRoles().add(appRole);
    }

    @Override
    public void RemoveRoleFromUser(String username, String role) {
        AppUser user = userRepo.findByUsername(username);
        AppRole appRole = appRoleRepo.findByRole(role);
        user.getRoles().add(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
