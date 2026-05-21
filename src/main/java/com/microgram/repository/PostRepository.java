package com.microgram.repository;

import com.microgram.model.Post;
import com.microgram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserOrderByCreatedAtDesc(User user);

    @Query("""
            SELECT p FROM Post p
            WHERE p.user IN (
                SELECT s.following FROM Subscription s
                WHERE s.follower.id = :userId
            )
            ORDER BY p.createdAt DESC
            """)
    List<Post> findFeedForUser(@Param("userId") Long userId);
}