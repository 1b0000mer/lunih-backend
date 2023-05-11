package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company, Integer> {

    Optional<Company> findByAccount_Id(int id);

    @Query(
            value = "SELECT * FROM Company s " +
                    "WHERE s.id like %?1% OR (s.companyName like %?1% OR s.companyContactPersonName like %?1%)",
            countQuery = "SELECT count(*) FROM Company",
            nativeQuery = true
    )
    Page<Company> getAllCompanyPaging(String search, Pageable pageable);

}
