package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepo extends JpaRepository <Post, Integer> {

    @Query(
            value = "SELECT * FROM Post p " +
                    "WHERE p.status=true AND (p.titleEn like %?1% OR p.titleLv like %?1%)",
            countQuery = "SELECT count(*) FROM Post",
            nativeQuery = true
    )
    Page<Post> getAllPostPaging(String search, Pageable pageable);
}
