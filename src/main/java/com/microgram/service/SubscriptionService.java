package com.microgram.service;

public interface SubscriptionService {

    void toggleSubscription(String followerEmail, String followingUsername);

    boolean isFollowing(String followerEmail, String followingUsername);
}