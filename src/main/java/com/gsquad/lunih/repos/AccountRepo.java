package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepo extends JpaRepository<Account, Integer> {

    //Account as user_account
    @Query(
            value = "SELECT * FROM user_account a " +
                    "WHERE a.status=true AND a.email LIKE %?1%",
            countQuery = "SELECT count(*) FROM Account",
            nativeQuery = true
    )
    Page<Account> getAllAccountPaging(String search, Pageable pageable);

}
