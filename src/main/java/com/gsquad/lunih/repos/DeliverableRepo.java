package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.Deliverable;
import com.gsquad.lunih.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeliverableRepo extends JpaRepository<Deliverable, Integer> {
    @Query(
            value = "SELECT * FROM Deliverable p " +
                    "WHERE (p.titleEn like %?1% OR p.titleLv like %?1%)",
            countQuery = "SELECT count(*) FROM Deliverable",
            nativeQuery = true
    )
    Page<Deliverable> getAllDeliverablePaging(String search, Pageable pageable);
}
