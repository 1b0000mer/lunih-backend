package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, String> {

}
