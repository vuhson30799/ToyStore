package repository;

import model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);

    Account findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update Account a set a.password = :password where a.id = :id")
    void updatePassword(@Param("password") String password, @Param("id") Long id);

}
