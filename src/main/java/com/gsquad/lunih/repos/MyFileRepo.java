package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyFileRepo extends JpaRepository<MyFile, Integer> {
}
