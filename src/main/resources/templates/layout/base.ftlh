<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title!'Microgram'}</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"
          rel="stylesheet">
</head>
<body>

<#if currentUser??>
    <nav class="navbar navbar-light border-bottom mb-4">
        <div class="container">
            <a class="navbar-brand fw-bold" href="/">Microgram</a>

            <form class="d-flex" action="/search" method="get">
                <input class="form-control me-2" type="search"
                       name="q" placeholder="Поиск">
                <button class="btn btn-outline-secondary" type="submit">
                    <i class="bi bi-search"></i>
                </button>
            </form>

            <div class="d-flex align-items-center gap-3">
                <a href="/" class="text-dark"><i class="bi bi-house-fill fs-5"></i></a>
                <a href="/posts/new" class="text-dark">
                    <i class="bi bi-plus-square fs-5"></i>
                </a>
                <a href="/users/${currentUser.username}" class="text-dark">
                    <i class="bi bi-person-circle fs-5"></i>
                </a>
                <form action="/auth/logout" method="post">
                    <input type="hidden"
                           name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-link text-dark p-0">
                        <i class="bi bi-box-arrow-right fs-5"></i>
                    </button>
                </form>
            </div>
        </div>
    </nav>
</#if>

<div class="container">
    <#nested>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
</script>
</body>
</html>