package com.microgram.service.impl;

import com.microgram.dto.CommentCreateDto;
import com.microgram.dto.CommentDto;
import com.microgram.dto.UserDto;
import com.microgram.exception.CommentNotFoundException;
import com.microgram.exception.ForbiddenException;
import com.microgram.exception.PostNotFoundException;
import com.microgram.model.Comment;
import com.microgram.model.Post;
import com.microgram.model.User;
import com.microgram.repository.CommentRepository;
import com.microgram.repository.PostRepository;
import com.microgram.service.CommentService;
import com.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                    log.warn("Пост не найден при загрузке комментариев: {}",
                            postId);
                    return new PostNotFoundException();
                });
        return commentRepository.findByPostOrderByCreatedAtAsc(post)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @Transactional
    public void addComment(Long postId, CommentCreateDto dto,
                           String authorEmail) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        User author = userService.getUserByEmail(authorEmail);

        commentRepository.save(Comment.builder()
                .post(post)
                .user(author)
                .text(dto.getText())
                .build());

        post.setCommentCount(
                post.getCommentCount() == null
                        ? 1
                        : post.getCommentCount() + 1
        );
        postRepository.save(post);

        log.info("Комментарий добавлен: пост={}, user={}",
                postId, authorEmail);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, String currentUserEmail) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    log.warn("Комментарий не найден id={}", commentId);
                    return new CommentNotFoundException();
                });

        Post post = comment.getPost();

        if (!post.getUser().getEmail().equals(currentUserEmail)) {
            log.warn("Нет прав на удаление комментария id={}, user={}",
                    commentId, currentUserEmail);
            throw new ForbiddenException();
        }

        commentRepository.delete(comment);

        post.setCommentCount(
                post.getCommentCount() == null
                        ? 0
                        : Math.max(0, post.getCommentCount() - 1)
        );
        postRepository.save(post);

        log.info("Комментарий id={} удалён пользователем {}",
                commentId, currentUserEmail);
    }


    private CommentDto mapToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .user(UserDto.builder()
                        .id(comment.getUser().getId())
                        .username(comment.getUser().getUsername())
                        .name(comment.getUser().getName())
                        .avatar(comment.getUser().getAvatar())
                        .build())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}