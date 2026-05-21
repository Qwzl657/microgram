<#import "/spring.ftl" as spring>
<nav class="navbar navbar-light bg-white border-bottom">
    <div class="container">

        <a class="navbar-brand fw-bold fst-italic" href="/">Microgram</a>

        <form class="d-flex" action="/search" method="get">
            <input class="form-control form-control-sm me-2"
                   type="search"
                   name="q"
                   placeholder="Поиск">
            <button class="btn btn-outline-secondary btn-sm" type="submit">
                <i class="bi bi-search"></i>
            </button>
        </form>

        <div class="d-flex align-items-center gap-3">

            <a href="/" class="text-dark">
                <i class="bi bi-house-fill fs-5"></i>
            </a>

            <a href="/posts/new" class="text-dark">
                <i class="bi bi-plus-square fs-5"></i>
            </a>

            <#if currentUser??>
                <a href="/users/${currentUser.username}" class="text-dark">
                    <i class="bi bi-person-circle fs-5"></i>
                </a>
            </#if>

            <form action="/auth/logout" method="post" class="m-0">
                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
                <button type="submit"
                        class="btn btn-link text-dark p-0">
                    <i class="bi bi-box-arrow-right fs-5"></i>
                </button>
            </form>

        </div>
    </div>
</nav>