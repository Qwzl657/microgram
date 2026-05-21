package com.microgram.service.impl;

import com.microgram.dto.PostCreateDto;
import com.microgram.dto.PostDto;
import com.microgram.dto.UserDto;
import com.microgram.exception.ForbiddenException;
import com.microgram.exception.PostNotFoundException;
import com.microgram.model.Like;
import com.microgram.model.Post;
import com.microgram.model.User;
import com.microgram.repository.CommentRepository;
import com.microgram.repository.LikeRepository;
import com.microgram.repository.PostRepository;
import com.microgram.service.FileService;
import com.microgram.service.PostService;
import com.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final FileService fileService;

    @Override
    @Transactional
    public void createPost(PostCreateDto dto, String authorEmail) {
        User author = userService.getUserByEmail(authorEmail);

        String imageName = fileService.savePostImage(dto.getImage());

        Post post = Post.builder()
                .user(author)
                .image(imageName)
                .description(dto.getDescription())
                .likeCount(0)
                .commentCount(0)
                .build();

        postRepository.save(post);

        author.setPostCount(author.getPostCount() + 1);
        userService.updateProfile(authorEmail, mapUserToDto(author));

        log.info("Пользователь {} создал пост id={}", authorEmail, post.getId());
    }

    @Override
    public PostDto getPostById(Long id, String currentUserEmail) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Пост не найден id={}", id);
                    return new PostNotFoundException();
                });

        return mapToDto(post, currentUserEmail);
    }

    @Override
    public List<PostDto> getFeedForUser(String email) {
        User user = userService.getUserByEmail(email);
        log.debug("Загрузка ленты для пользователя: {}", email);

        return postRepository.findFeedForUser(user.getId())
                .stream()
                .map(post -> mapToDto(post, email))
                .toList();
    }

    @Override
    public List<PostDto> getPostsByUsername(String username, String currentUserEmail) {
        User user = userService.getUserById(
                userService.getUserDtoByUsername(username).getId()
        );

        return postRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(post -> mapToDto(post, currentUserEmail))
                .toList();
    }

    @Override
    @Transactional
    public void deletePost(Long postId, String currentUserEmail) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (!post.getUser().getEmail().equals(currentUserEmail)) {
            log.warn("Пользователь {} пытается удалить чужой пост id={}",
                    currentUserEmail, postId);
            throw new ForbiddenException();
        }

        commentRepository.deleteAllByPost(post);

        User author = post.getUser();
        author.setPostCount(Math.max(0, author.getPostCount() - 1));

        postRepository.delete(post);
        log.info("Пользователь {} удалил пост id={}", currentUserEmail, postId);
    }

    @Override
    @Transactional
    public void toggleLike(Long postId, String currentUserEmail) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        User user = userService.getUserByEmail(currentUserEmail);

        if (likeRepository.existsByPostAndUser(post, user)) {
            Like like = likeRepository.findByPostAndUser(post, user)
                    .orElseThrow();
            likeRepository.delete(like);
            post.setLikeCount(Math.max(0, post.getLikeCount() - 1));
            log.debug("Пользователь {} убрал лайк с поста id={}", currentUserEmail, postId);
        } else {
            Like like = Like.builder()
                    .post(post)
                    .user(user)
                    .build();
            likeRepository.save(like);
            post.setLikeCount(post.getLikeCount() + 1);
            log.debug("Пользователь {} лайкнул пост id={}", currentUserEmail, postId);
        }

        postRepository.save(post);
    }

    private PostDto mapToDto(Post post, String currentUserEmail) {
        boolean liked = false;

        if (currentUserEmail != null) {
            try {
                User currentUser = userService.getUserByEmail(currentUserEmail);
                liked = likeRepository.existsByPostAndUser(post, currentUser);
            } catch (Exception e) {
                log.debug("Не удалось проверить лайк для: {}", currentUserEmail);
            }
        }

        return PostDto.builder()
                .id(post.getId())
                .user(mapUserToDto(post.getUser()))
                .image(post.getImage())
                .description(post.getDescription())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .likedByCurrentUser(liked)
                .build();
    }

    private UserDto mapUserToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .avatar(user.getAvatar())
                .postCount(user.getPostCount())
                .followersCount(user.getFollowersCount())
                .followingCount(user.getFollowingCount())
                .build();
    }
}