package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository <Post, Integer> {

//    @Query(
//            value = "SELECT * FROM Post p " +
//                    "WHERE p.status=true AND (p.titleEn like %?1% OR p.titleLv like %?1%)",
//            countQuery = "SELECT count(*) FROM Post",
//            nativeQuery = true
//    )
    @Query(
            value = "SELECT * FROM Post p " +
                    "WHERE p.titleEn like %?1% OR p.titleLv like %?1%",
            countQuery = "SELECT count(*) FROM Post",
            nativeQuery = true
    )
    Page<Post> getAllPostPaging(String search, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.studentList s WHERE s.studentID = :studentID")
    List<Post> findPostsByStudentID(@Param("studentID") String studentID);
}
