package service;

import model.Account;

public interface AccountService {

    Iterable<Account> findAll();

    Account findAccountById(Long id);

    Account findAccountByUsername(String username);

    Account findAccountByEmail(String email);

    String getPasswordById(Long id);

    void save(Account account);

    void update(Account account);

    void updatePassword(Long id, String password);
}
