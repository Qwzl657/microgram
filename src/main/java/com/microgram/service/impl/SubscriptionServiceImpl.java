package com.microgram.service.impl;

import com.microgram.model.Subscription;
import com.microgram.model.User;
import com.microgram.repository.SubscriptionRepository;
import com.microgram.repository.UserRepository;
import com.microgram.service.SubscriptionService;
import com.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    @Transactional
    public void toggleSubscription(String followerEmail, String followingUsername) {

        User follower = userService.getUserByEmail(followerEmail);
        User following = userService.getUserDtoByUsername(followingUsername) != null
                ? userRepository.findByUsername(followingUsername).orElseThrow()
                : null;

        if (following == null) return;

        if (follower.getId().equals(following.getId())) {
            log.warn("Пользователь {} пытается подписаться на себя", followerEmail);
            return;
        }

        if (subscriptionRepository.existsByFollowerAndFollowing(follower, following)) {
            Subscription sub = subscriptionRepository
                    .findByFollowerAndFollowing(follower, following)
                    .orElseThrow();

            subscriptionRepository.delete(sub);

            follower.setFollowingCount(Math.max(0, follower.getFollowingCount() - 1));
            following.setFollowersCount(Math.max(0, following.getFollowersCount() - 1));

            log.info("Пользователь {} отписался от {}", followerEmail, followingUsername);
        } else {
            Subscription sub = Subscription.builder()
                    .follower(follower)
                    .following(following)
                    .build();

            subscriptionRepository.save(sub);

            follower.setFollowingCount(follower.getFollowingCount() + 1);
            following.setFollowersCount(following.getFollowersCount() + 1);

            log.info("Пользователь {} подписался на {}", followerEmail, followingUsername);
        }

        userRepository.save(follower);
        userRepository.save(following);
    }

    @Override
    public boolean isFollowing(String followerEmail, String followingUsername) {
        try {
            User follower = userService.getUserByEmail(followerEmail);
            User following = userRepository.findByUsername(followingUsername)
                    .orElse(null);

            if (following == null) return false;

            return subscriptionRepository
                    .existsByFollowerAndFollowing(follower, following);
        } catch (Exception e) {
            log.debug("Ошибка проверки подписки: {}", e.getMessage());
            return false;
        }
    }
}