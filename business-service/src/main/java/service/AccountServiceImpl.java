package service;

import model.Account;

import repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountServiceImpl  implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findAccountById(Long id) {
        return accountRepository.findOne(id);
    }

    @Override
    public Account findAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public String getPasswordById(Long id) {
        return accountRepository.findOne(id).getPassword();
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void update(Account account) {

        Account temp = accountRepository.findByUsername(account.getUsername());
        account.setRole(temp.getRole());
        account.setPassword(temp.getPassword());

        accountRepository.save(account);

    }

    @Override
    public void updatePassword(Long id, String password) {
        accountRepository.updatePassword(password, id);
    }
}
