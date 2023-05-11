package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.Company;
import com.gsquad.lunih.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepo extends JpaRepository<Company, Integer> {

    @Query(
            value = "SELECT * FROM Company s " +
                    "WHERE s.companyID like %?1% OR (s.name like %?1%)",
            countQuery = "SELECT count(*) FROM Company",
            nativeQuery = true
    )
    Page<Company> getAllCompanyPaging(String search, Pageable pageable);

}
