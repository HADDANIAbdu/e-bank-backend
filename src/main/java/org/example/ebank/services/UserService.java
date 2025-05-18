package org.example.ebank.services;

import org.example.ebank.entities.AppRole;
import org.example.ebank.entities.AppUser;

public interface UserService {
    AppUser addUser(String username, String password, String email, String confirmPassword);
    AppRole addRole(String role);
    void AddRoleToUser(String username, String role);
    void RemoveRoleFromUser(String username, String role);
    AppUser loadUserByUsername(String username);
}
