package org.example.ebank.controllers;


import org.example.ebank.dtos.*;
import org.example.ebank.exceptions.BalanceNotSufficientException;
import org.example.ebank.exceptions.BankAccountNotFoundException;
import org.example.ebank.exceptions.CustomerNotFoundException;
import org.example.ebank.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class BankAccountController {
    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }
    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());
    }
    @PostMapping("/accounts/save/current-account")
    public Map<String, String> saveCurrentAccount(@RequestBody CurrentAccountDTO currentAccountDTO) throws CustomerNotFoundException {
        this.bankAccountService.saveCurrentAccount(
                currentAccountDTO.getBalance(),
                currentAccountDTO.getOverDraft(),
                currentAccountDTO.getCustomerDTO().getId(),
                currentAccountDTO.getStatus());
        return  Map.of("status","success",
                "message", "Account created Successfully !");
    }
    @PutMapping("/accounts/update/current-account/{Id}")
    public Map<String, String> updateCurrentAccount(@PathVariable String Id, @RequestBody CurrentAccountDTO currentAccountDTO) throws CustomerNotFoundException {
        this.bankAccountService.updateCurrentAccount(
                Id,
                currentAccountDTO.getBalance(),
                currentAccountDTO.getOverDraft(),
                currentAccountDTO.getCustomerDTO().getId(),
                currentAccountDTO.getStatus()
        );
        return  Map.of("status","success",
                "message", "Account updated Successfully !");
    }
    @PostMapping("/accounts/save/saving-account")
    public Map<String, String> saveSavingAccount(@RequestBody SavingAccountDTO savingAccountDTO) throws CustomerNotFoundException {
        this.bankAccountService.saveSavingAccount(
                savingAccountDTO.getBalance(),
                savingAccountDTO.getInterestRate(),
                savingAccountDTO.getCustomerDTO().getId(),
                savingAccountDTO.getStatus()
        );
        return  Map.of("status","success",
                "message", "Account created Successfully !");
    }
    @PutMapping("/accounts/update/saving-account/{Id}")
    public Map<String, String> updateSavingAccount(@PathVariable String Id, @RequestBody SavingAccountDTO savingAccountDTO) throws CustomerNotFoundException {
        this.bankAccountService.updateSavingAccount(
                Id,
                savingAccountDTO.getBalance(),
                savingAccountDTO.getInterestRate(),
                savingAccountDTO.getCustomerDTO().getId(),
                savingAccountDTO.getStatus()
        );
        return  Map.of("status","success",
                "message", "Account updated Successfully !");
    }
    @DeleteMapping("/accounts/delete/{Id}")
    public Map<String, String> deleteAccount(@PathVariable String Id) throws CustomerNotFoundException, BankAccountNotFoundException {
        this.bankAccountService.deleteAccount(Id);
        return  Map.of("status","success",
                "message", "Account deleted Successfully !");
    }
}
