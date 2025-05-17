package org.example.ebank.repositories;

import org.example.ebank.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepo extends JpaRepository<BankAccount, String> {
}
