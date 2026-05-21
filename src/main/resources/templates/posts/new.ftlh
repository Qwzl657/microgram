<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Новая публикация — Microgram</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body class="bg-light">

<#include "../layout/navbar.ftlh">

<div class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-header fw-bold">Новая публикация</div>
                <div class="card-body">

                    <form action="/posts" method="post"
                          enctype="multipart/form-data">

                        <input type="hidden"
                               name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>


                        <div class="mb-3">
                            <label class="form-label fw-bold">
                                Фотография
                            </label>
                            <input type="file"
                                   id="image-input"
                                   name="image"
                                   class="form-control"
                                   accept="image/*"
                                   required>
                            <@spring.bind "post.image"/>
                            <#list spring.status.errorMessages as msg>
                                <div class="text-danger small">${msg}</div>
                            </#list>
                        </div>

                        <div id="image-preview" class="mb-3" hidden>
                            <img src=""
                                 alt="Превью"
                                 class="img-fluid rounded"
                                 style="max-height: 300px; object-fit: cover;">
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">
                                Описание
                            </label>

                            <textarea id="description-input"
                                      name="description"
                                      class="form-control"
                                      rows="4"
                                      placeholder="Введите описание..."
                                      maxlength="2200"></textarea>
                            <div class="text-end mt-1">
                                <small>
                                    Осталось символов:
                                    <span id="description-counter"
                                          class="text-muted">2200</span>
                                </small>
                            </div>
                            <@spring.bind "post.description"/>
                            <#list spring.status.errorMessages as msg>
                                <div class="text-danger small">${msg}</div>
                            </#list>
                        </div>

                        <div class="d-flex gap-2">
                            <button type="submit"
                                    class="btn btn-primary">
                                Опубликовать
                            </button>
                            <a href="/" class="btn btn-outline-secondary">
                                Отмена
                            </a>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/js/post-form.js"></script>
<script src="/js/main.js"></script>
</body>
</html>