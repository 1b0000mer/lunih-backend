package com.gsquad.lunih.repos.categories;

import com.gsquad.lunih.entities.categories.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProgramRepo extends JpaRepository<Program, Integer> {

//    @Query(
//            value = "SELECT * FROM Program f " +
//                    "WHERE f.status=true AND (f.name like %?1%)",
//            countQuery = "SELECT count(*) FROM Program",
//            nativeQuery = true
//    )

    @Query(
            value = "SELECT * FROM Program f " +
                    "WHERE f.nameEn like %?1% OR f.nameLv like %?1%",
            countQuery = "SELECT count(*) FROM Program",
            nativeQuery = true
    )Page<Program> getALLProgramPaging(String search, Pageable pageable);

}

