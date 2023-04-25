package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepo extends JpaRepository<Student, String> {

    @Query(
            value = "SELECT * FROM Student s " +
                    "WHERE s.studentID like %?1% OR (s.firstName like %?1% OR s.surName like %?1%)",
            countQuery = "SELECT count(*) FROM Student",
            nativeQuery = true
    )
    Page<Student> getAllStudentPaging(String search, Pageable pageable);

}
