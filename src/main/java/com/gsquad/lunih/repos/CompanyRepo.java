package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company, Integer> {

    Optional<Company> findByAccount_Id(int id);
}
