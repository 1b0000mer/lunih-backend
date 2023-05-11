package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepo extends JpaRepository <Post, Integer> {

//    @Query(
//            value = "",
//            countQuery = "",
//            nativeQuery = true
//    )
//    Page<Post> getAllPostPaging(String search, Pageable pageable);
}
