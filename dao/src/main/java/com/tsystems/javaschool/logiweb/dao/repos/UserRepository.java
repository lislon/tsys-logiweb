package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Integer> {
    Account findByEmail(String email);
}
