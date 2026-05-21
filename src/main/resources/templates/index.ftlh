<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="${.locale}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Microgram</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"
          rel="stylesheet">
    <style>
        .post-card { max-width:600px; margin:0 auto 24px auto; }
        .post-image { width:100%; object-fit:cover; max-height:600px; }
        .avatar-sm { width:32px; height:32px; object-fit:cover; border-radius:50%; }
    </style>
</head>
<body class="bg-light">

<#include "/layout/navbar.ftlh">

<div class="container mt-4">
    <div class="row">
        <div class="col-md-8 offset-md-2">

            <#if !posts?? || posts?size == 0>
                <div class="text-center py-5">
                    <i class="bi bi-camera fs-1 text-muted"></i>
                    <p class="text-muted mt-3">
                        <@spring.message 'feed.empty'/>
                    </p>
                    <a href="/search" class="btn btn-primary">
                        <@spring.message 'feed.find.users'/>
                    </a>
                </div>
            <#else>
                <#list posts as post>
                    <div class="card post-card shadow-sm">

                        <div class="card-header bg-white d-flex align-items-center gap-2">
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

                        <a href="/posts/${post.id}">
                            <img src="/posts/${post.image}"
                                 class="post-image" alt="post">
                        </a>

                        <div class="card-body">

                            <div class="d-flex gap-3 mb-2 post-actions">
                                <form class="like-form"
                                      data-post-id="${post.id}"
                                      style="display:inline;">
                                    <#if _csrf??>
                                        <input type="hidden"
                                               name="${(_csrf.parameterName)!'_csrf'}"
                                               value="${(_csrf.token)!''}"/>
                                    </#if>
                                    <button type="submit"
                                            class="btn btn-link p-0 text-decoration-none">
                                        <#if post.likedByCurrentUser>
                                            <i class="like-icon bi bi-heart-fill text-danger fs-5"></i>
                                        <#else>
                                            <i class="like-icon bi bi-heart fs-5"></i>
                                        </#if>
                                    </button>
                                </form>
                                <a href="/posts/${post.id}" class="text-dark">
                                    <i class="bi bi-chat fs-5"></i>
                                </a>
                            </div>

                            <div class="fw-bold mb-1">
                                <span class="like-count">${post.likeCount!0}</span>
                                <@spring.message 'post.likes'/>
                            </div>

                            <#if post.description??>
                                <div class="mb-1">
                                    <span class="fw-bold">${post.user.username}</span>
                                    ${post.description}
                                </div>
                            </#if>

                            <a href="/posts/${post.id}"
                               class="text-muted text-decoration-none small">
                                Посмотреть все комментарии (${post.commentCount!0})
                            </a>

                        </div>
                    </div>
                </#list>
            </#if>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/js/likes.js"></script>
<script src="/js/search.js"></script>
<script src="/js/main.js"></script>
</body>
</html>