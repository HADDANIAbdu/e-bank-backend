package org.example.ebank.repositories;

import org.example.ebank.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepo extends JpaRepository<AppRole, String> {
    AppRole findByRole(String role);
}
