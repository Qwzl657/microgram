package com.microgram.repository;

import com.microgram.model.Subscription;
import com.microgram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    boolean existsByFollowerAndFollowing(User follower, User following);

    Optional<Subscription> findByFollowerAndFollowing(User follower, User following);
}