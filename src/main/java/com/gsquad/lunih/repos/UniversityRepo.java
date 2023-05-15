package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.University;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UniversityRepo extends JpaRepository<University, Integer> {

    @Query(
            value = "SELECT * FROM University u INNER JOIN user_account a ON (u.account_id = a.id) " +
                    "WHERE a.email LIKE %?1% OR u.name LIKE %?1%",
            countQuery = "SELECT count(*) FROM University",
            nativeQuery = true
    )
    Page<University> getAllUniversityPaging(String search, Pageable pageable);

    Optional<University> findByAccount_Id(int id);

    Optional<University> findByAccount_Email(String email);
}
