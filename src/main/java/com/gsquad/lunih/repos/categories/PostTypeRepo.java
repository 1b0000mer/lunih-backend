package com.gsquad.lunih.repos.categories;

import com.gsquad.lunih.entities.categories.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostTypeRepo extends JpaRepository<PostType, Integer> {

//    @Query(
//            value = "SELECT * FROM PostType p " +
//                    "WHERE p.status=true AND (p.nameEn like %?1% OR f.nameLv like %?1%)",
//            countQuery = "SELECT count(*) FROM PostType",
//            nativeQuery = true
//    )
    @Query(
            value = "SELECT * FROM post_type p " +
                    "WHERE p.nameEn like %?1% OR p.nameLv like %?1%",
            countQuery = "SELECT count(*) FROM post_type",
            nativeQuery = true
    )
    Page<PostType> getAllPostTypePaging(String search, Pageable pageable);
}
