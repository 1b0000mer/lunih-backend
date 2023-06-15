package com.gsquad.lunih.repos.categories;

import com.gsquad.lunih.entities.categories.Industry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IndustryRepo extends JpaRepository<Industry, Integer> {

    @Query(
            value = "SELECT * FROM Industry i " +
                    "WHERE i.nameEn like %?1% OR i.nameLv like %?1%",
            countQuery = "SELECT count(*) FROM Industry",
            nativeQuery = true
    )
    Page<Industry> getAllIndustryPaging(String search, Pageable pageable);
}
