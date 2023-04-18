package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Integer> {

}
