package com.gsquad.lunih.repos.categories;

import com.gsquad.lunih.entities.categories.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacultyRepo extends JpaRepository<Faculty, Integer> {

//    @Query(
//            value = "SELECT * FROM Faculty f " +
//                    "WHERE f.status=true AND (f.nameEn like %?1% OR f.nameLv like %?1%)",
//            countQuery = "SELECT count(*) FROM Faculty",
//            nativeQuery = true
//    )
@Query(
        value = "SELECT * FROM Faculty f " +
                "WHERE f.nameEn like %?1% OR f.nameLv like %?1%",
        countQuery = "SELECT count(*) FROM Faculty",
        nativeQuery = true
)
    Page<Faculty> getAllFacultyPaging(String search, Pageable pageable);

}
