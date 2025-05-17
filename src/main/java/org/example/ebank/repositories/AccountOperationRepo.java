package org.example.ebank.repositories;

import org.example.ebank.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepo extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByBankAccountId(String bankAccountId);
    Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String bankAccountId, Pageable pageable);
}
