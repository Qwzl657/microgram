<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="${.locale}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><@spring.message 'post.new.title'/> — Microgram</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body class="bg-light">

<#include "/layout/navbar.ftlh">

<div class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-header fw-bold">
                    <@spring.message 'post.new.title'/>
                </div>
                <div class="card-body">

                    <form action="/posts" method="post"
                          enctype="multipart/form-data">

                        <#if _csrf??>
                            <input type="hidden"
                                   name="${(_csrf.parameterName)!'_csrf'}"
                                   value="${(_csrf.token)!''}"/>
                        </#if>


                        <div class="mb-3">
                            <label class="form-label fw-bold">
                                <@spring.message 'post.new.image'/>
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
                                 style="max-height:300px; object-fit:cover;">
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">
                                <@spring.message 'post.new.description'/>
                            </label>
                            <textarea id="description-input"
                                      name="description"
                                      class="form-control"
                                      rows="4"
                                      maxlength="2200"></textarea>
                            <div class="text-end mt-1">
                                <small>
                                    <@spring.message 'post.new.chars.left'/>:
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
                            <button type="submit" class="btn btn-primary">
                                <@spring.message 'post.new.submit'/>
                            </button>
                            <a href="/" class="btn btn-outline-secondary">
                                <@spring.message 'post.new.cancel'/>
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