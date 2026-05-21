<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${profileUser.username} — Microgram</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"
          rel="stylesheet">
    <style>
        .avatar-lg {
            width: 150px;
            height: 150px;
            object-fit: cover;
            border-radius: 50%;
        }
        .post-grid-img {
            width: 100%;
            aspect-ratio: 1;
            object-fit: cover;
            cursor: pointer;
        }
    </style>
</head>
<body class="bg-light">

<#include "layout/navbar.ftlh">

<div class="container mt-4">

    <#-- Шапка профиля -->
    <div class="row mb-4">
        <div class="col-md-3 text-center">

            <#if profileUser.avatar??>
                <img src="/avatars/${profileUser.avatar}"
                     class="avatar-lg" alt="avatar">
            <#else>
                <img src="/avatars/default.png"
                     class="avatar-lg" alt="avatar">
            </#if>

        </div>

        <div class="col-md-9">

            <div class="d-flex align-items-center gap-3 mb-3">

                <h4 class="mb-0">${profileUser.username}</h4>

                <#if isOwnProfile?? && isOwnProfile>
                    <a href="/users/edit" class="btn btn-outline-secondary btn-sm">
                        Редактировать профиль
                    </a>
                <#else>
                    <form action="/users/${profileUser.username}/subscribe"
                          method="post">
                        <input type="hidden"
                               name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
                        <#if isFollowing?? && isFollowing>
                            <button type="submit"
                                    class="btn btn-outline-secondary btn-sm">
                                Отписаться
                            </button>
                        <#else>
                            <button type="submit"
                                    class="btn btn-primary btn-sm">
                                Подписаться
                            </button>
                        </#if>
                    </form>
                </#if>

            </div>

            <div class="d-flex gap-4 mb-3">
                <div>
                    <span class="fw-bold">${profileUser.postCount!0}</span>
                    <span class="text-muted"> публикаций</span>
                </div>
                <div>
                    <span class="fw-bold">${profileUser.followersCount!0}</span>
                    <span class="text-muted"> подписчиков</span>
                </div>
                <div>
                    <span class="fw-bold">${profileUser.followingCount!0}</span>
                    <span class="text-muted"> подписок</span>
                </div>
            </div>

            <#if profileUser.name??>
                <div class="fw-bold">${profileUser.name}</div>
            </#if>

            <#if profileUser.bio??>
                <div class="text-muted">${profileUser.bio}</div>
            </#if>

        </div>
    </div>

    <hr>

    <#if !posts?? || posts?size == 0>
        <div class="text-center py-5 text-muted">
            <i class="bi bi-camera fs-1"></i>
            <p class="mt-2">Публикаций пока нет</p>
        </div>
    <#else>
        <div class="row g-2">
            <#list posts as post>
                    <a href="/posts/${post.id}">
                        <img src="/posts/${post.image}"
                             class="post-grid-img" alt="post">
                    </a>
                </div>
            </#list>
        </div>
    </#if>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
</script>
</body>
</html>