package com.dws.challenge.service;

import com.dws.challenge.domain.Account;
import com.dws.challenge.repository.AccountsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

  private final Map<String,Account> accounts= new ConcurrentHashmap<>();
  @Getter
  private final AccountsRepository accountsRepository;

  @Autowired
  public AccountsService(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  public void createAccount(Account account) {
    this.accountsRepository.createAccount(account);
  }

  public Account getAccount(String accountId) {
    return this.accountsRepository.getAccount(accountId);
  }
  
  public String transferAmount(String fromAccountId,String toAccountId,BigDecimal amount) {
	  
	  synchronized(accounts) {
		  Account fromAccount= accountsRepository.getAccount(fromAccountId).orElseThrow(() -> new TransferAmountException("Invalid accountFrom ID."));
		  Account toAccount= accountsRepository.getAccount(toAccountId).orElseThrow(() -> new TransferAmountException("Invalid accountTo ID."));
	  
			  if (fromAccount.getBalance().compareTo(amount) < 0) {
				  throw new TransferAmountException("Insufficient funds.");
			  }

			  fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
			  toAccount.setBalance(toAccount.getBalance().add(amount));

			  accountsRepository.save(fromAccount);
			  accountsRepository.save(toAccount);
	}
  }
}
