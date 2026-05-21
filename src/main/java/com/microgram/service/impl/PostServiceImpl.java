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
import com.microgram.repository.UserRepository;
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
    private final UserRepository userRepository;
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
                .build();

        postRepository.save(post);

        author.setPostCount(author.getPostCount() + 1);
        userRepository.save(author);

        log.info("Создан пост id={} пользователем {}", post.getId(),
                authorEmail);
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
        log.debug("Загрузка ленты для: {}", email);
        return postRepository.findFeedForUser(user.getId())
                .stream()
                .map(post -> mapToDto(post, email))
                .toList();
    }

    @Override
    public List<PostDto> getPostsByUsername(
            String username, String currentUserEmail) {
        User user = userService.getUserByUsername(username);
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
            log.warn("Нет прав на удаление поста id={}, user={}",
                    postId, currentUserEmail);
            throw new ForbiddenException();
        }

        commentRepository.deleteAllByPost(post);

        User author = post.getUser();
        author.setPostCount(Math.max(0, author.getPostCount() - 1));
        userRepository.save(author);

        postRepository.delete(post);
        log.info("Пост id={} удалён пользователем {}",
                postId, currentUserEmail);
    }

    @Override
    @Transactional
    public void toggleLike(Long postId, String currentUserEmail) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        User user = userService.getUserByEmail(currentUserEmail);

        if (likeRepository.existsByPostAndUser(post, user)) {
            likeRepository.findByPostAndUser(post, user)
                    .ifPresent(likeRepository::delete);
            post.setLikeCount(Math.max(0, post.getLikeCount() - 1));
            log.debug("Лайк убран: пост={}, user={}",
                    postId, currentUserEmail);
        } else {
            likeRepository.save(Like.builder()
                    .post(post)
                    .user(user)
                    .build());
            post.setLikeCount(post.getLikeCount() + 1);
            log.debug("Лайк поставлен: пост={}, user={}",
                    postId, currentUserEmail);
        }

        postRepository.save(post);
    }

    private PostDto mapToDto(Post post, String currentUserEmail) {
        boolean liked = false;
        if (currentUserEmail != null) {
            try {
                User currentUser = userService.getUserByEmail(
                        currentUserEmail);
                liked = likeRepository.existsByPostAndUser(
                        post, currentUser);
            } catch (Exception e) {
                log.debug("Не удалось проверить лайк для: {}",
                        currentUserEmail);
            }
        }
        return PostDto.builder()
                .id(post.getId())
                .user(mapUserToDto(post.getUser()))
                .image(post.getImage())
                .description(post.getDescription())
                .likeCount(post.getLikeCount() != null
                        ? post.getLikeCount() : 0)
                .commentCount(post.getCommentCount() != null
                        ? post.getCommentCount() : 0)
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
                .postCount(user.getPostCount() != null
                        ? user.getPostCount() : 0)
                .followersCount(user.getFollowersCount() != null
                        ? user.getFollowersCount() : 0)
                .followingCount(user.getFollowingCount() != null
                        ? user.getFollowingCount() : 0)
                .build();
    }
}