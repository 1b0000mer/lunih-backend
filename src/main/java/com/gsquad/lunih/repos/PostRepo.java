package com.gsquad.lunih.repos;

import com.gsquad.lunih.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository <Post, Integer> {
}
