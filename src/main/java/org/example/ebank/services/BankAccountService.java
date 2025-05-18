package org.example.ebank.services;

import org.example.ebank.dtos.*;
import org.example.ebank.enums.AccountStatus;
import org.example.ebank.exceptions.BalanceNotSufficientException;
import org.example.ebank.exceptions.BankAccountNotFoundException;
import org.example.ebank.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentAccountDTO saveCurrentAccount(double initialBalance, double overDraft, Long customerId, AccountStatus status) throws CustomerNotFoundException;
    SavingAccountDTO saveSavingAccount(double initialBalance, double interestRate, Long customerId, AccountStatus status) throws CustomerNotFoundException;
    CurrentAccountDTO updateCurrentAccount(String id, double initialBalance, double overDraft, Long customerId, AccountStatus status) throws CustomerNotFoundException;
    SavingAccountDTO updateSavingAccount(String id, double initialBalance, double interestRate, Long customerId, AccountStatus status) throws CustomerNotFoundException;
    void deleteAccount(String id) throws CustomerNotFoundException, BankAccountNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    List<BankAccountDTO> bankAccountList();
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long customerId);
    List<AccountOperationDTO> accountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
    List<CustomerDTO> searchCustomers(String keyword);
}
