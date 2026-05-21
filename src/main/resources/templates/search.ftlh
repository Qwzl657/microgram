<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Поиск — Microgram</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"
          rel="stylesheet">
    <style>
        .avatar-sm {
            width: 40px;
            height: 40px;
            object-fit: cover;
            border-radius: 50%;
        }
    </style>
</head>
<body class="bg-light">

<#include "layout/navbar.ftlh">

<div class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-md-6">

            <h5 class="mb-3">Поиск пользователей</h5>

            <form action="/search" method="get" class="mb-4">
                <div class="input-group">
                    <input type="text"
                           name="q"
                           class="form-control"
                           placeholder="Имя, логин или email"
                           value="${query!''}">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-search"></i> Найти
                    </button>
                </div>
            </form>

            <#if results??>
                <#if results?size == 0>
                    <div class="text-center text-muted py-4">
                        <i class="bi bi-person-x fs-2"></i>
                        <p class="mt-2">
                            По запросу "${query}" ничего не найдено
                        </p>
                    </div>
                <#else>
                    <div class="list-group">

                        <#list results as user>
                            <a href="/users/${user.username}"
                               class="list-group-item list-group-item-action
                                      d-flex align-items-center gap-3">

                                <#if user.avatar??>
                                    <img src="/avatars/${user.avatar}"
                                         class="avatar-sm" alt="avatar">
                                <#else>
                                    <img src="/avatars/default.png"
                                         class="avatar-sm" alt="avatar">
                                </#if>

                                <div>
                                    <div class="fw-bold">${user.username}</div>
                                    <#if user.name??>
                                        <div class="text-muted small">
                                            ${user.name}
                                        </div>
                                    </#if>
                                </div>

                            </a>
                        </#list>
                    </div>
                </#if>
            </#if>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
</script>
</body>
</html>