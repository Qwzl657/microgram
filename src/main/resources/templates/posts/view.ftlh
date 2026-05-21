<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Публикация — Microgram</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"
          rel="stylesheet">
    <style>
        .post-image { width:100%; max-height:600px; object-fit:cover; }
        .avatar-sm { width:32px; height:32px; object-fit:cover; border-radius:50%; }
        .comments-section { max-height:400px; overflow-y:auto; }
    </style>
</head>
<body class="bg-light">

<#include "../layout/navbar.ftlh">

<div class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow-sm">

                <div class="card-header bg-white d-flex
                            align-items-center justify-content-between">
                    <div class="d-flex align-items-center gap-2">
                        <#if post.user.avatar??>
                            <img src="/avatars/${post.user.avatar}"
                                 class="avatar-sm" alt="avatar">
                        <#else>
                            <img src="/avatars/default.png"
                                 class="avatar-sm" alt="avatar">
                        </#if>
                        <a href="/users/${post.user.username}"
                           class="fw-bold text-dark text-decoration-none">
                            ${post.user.username}
                        </a>
                    </div>

                    <#if currentUser?? &&
                    currentUser.username == post.user.username>
                        <form action="/posts/${post.id}/delete"
                              method="post"
                              class="delete-form m-0"
                              data-confirm="Удалить публикацию? Все комментарии тоже удалятся.">
                            <input type="hidden"
                                   name="${_csrf.parameterName}"
                                   value="${_csrf.token}"/>
                            <button type="submit"
                                    class="btn btn-link text-danger p-0">
                                <i class="bi bi-trash"></i>
                            </button>
                        </form>
                    </#if>
                </div>

                <img src="/posts/${post.image}"
                     class="post-image" alt="post">

                <div class="card-body">

                    <div class="d-flex gap-3 mb-2 post-actions">

                        <form class="like-form"
                              data-post-id="${post.id}"
                              style="display:inline;">
                            <input type="hidden"
                                   name="${_csrf.parameterName}"
                                   value="${_csrf.token}"/>
                            <button type="submit"
                                    class="btn btn-link p-0 text-decoration-none">
                                <#if post.likedByCurrentUser>
                                    <i class="like-icon bi bi-heart-fill text-danger fs-4"></i>
                                <#else>
                                    <i class="like-icon bi bi-heart fs-4"></i>
                                </#if>
                            </button>
                        </form>

                        <span class="align-self-center">
                            <i class="bi bi-chat fs-4"></i>
                        </span>
                    </div>

                    <div class="fw-bold mb-2">
                        <span class="like-count">${post.likeCount}</span>
                        отметок "Нравится"
                    </div>

                    <#if post.description??>
                        <div class="mb-3">
                            <span class="fw-bold">${post.user.username}</span>
                            ${post.description}
                        </div>
                    </#if>

                    <hr>

                    <div class="comments-section mb-3">
                        <#if !comments?? || comments?size == 0>
                            <p class="text-muted small">
                                Комментариев пока нет
                            </p>
                        <#else>
                            <#list comments as comment>
                                <div class="d-flex gap-2 mb-2 align-items-start">
                                    <#if comment.user.avatar??>
                                        <img src="/avatars/${comment.user.avatar}"
                                             class="avatar-sm" alt="avatar">
                                    <#else>
                                        <img src="/avatars/default.png"
                                             class="avatar-sm" alt="avatar">
                                    </#if>
                                    <div class="flex-grow-1">
                                        <span class="fw-bold small">
                                            ${comment.user.username}
                                        </span>
                                        <span class="small">
                                            ${comment.text}
                                        </span>
                                    </div>
                                    <#if currentUser?? &&
                                    currentUser.username == post.user.username>
                                        <form action="/posts/${post.id}/comments/${comment.id}/delete"
                                              method="post"
                                              class="delete-form m-0"
                                              data-confirm="Удалить комментарий?">
                                            <input type="hidden"
                                                   name="${_csrf.parameterName}"
                                                   value="${_csrf.token}"/>
                                            <button type="submit"
                                                    class="btn btn-link text-muted p-0 small">
                                                <i class="bi bi-x"></i>
                                            </button>
                                        </form>
                                    </#if>
                                </div>
                            </#list>
                        </#if>
                    </div>

                    <form action="/posts/${post.id}/comments" method="post">
                        <input type="hidden"
                               name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
                        <div class="d-flex gap-2">
                            <input type="text"
                                   name="text"
                                   class="form-control form-control-sm"
                                   placeholder="Добавьте комментарий...">
                            <button type="submit"
                                    class="btn btn-primary btn-sm">
                                Отправить
                            </button>
                        </div>
                        <@spring.bind "newComment.text"/>
                        <#list spring.status.errorMessages as msg>
                            <div class="text-danger small mt-1">${msg}</div>
                        </#list>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/js/likes.js"></script>
<script src="/js/post-form.js"></script>
<script src="/js/main.js"></script>
</body>
</html>