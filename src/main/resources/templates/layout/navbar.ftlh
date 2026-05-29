<#import "/spring.ftl" as spring>

<#if _csrf??>
    <meta name="_csrf" content="${(_csrf.token)!''}"/>
    <meta name="_csrf_header" content="${(_csrf.headerName)!'X-CSRF-TOKEN'}"/>
</#if>

<nav class="navbar navbar-light bg-white border-bottom">
    <div class="container">

        <a class="navbar-brand fw-bold fst-italic" href="/">Microgram</a>

        <div class="position-relative">
            <input type="text"
                   id="search-input"
                   class="form-control form-control-sm"
                   placeholder="<@spring.message 'nav.search'/>"
                   autocomplete="off">
            <div id="search-results"
                 class="list-group position-absolute w-100 shadow-sm"
                 style="z-index:1000; top:38px;"
                 hidden>
            </div>
        </div>

        <div class="d-flex align-items-center gap-3">

            <a href="/" class="text-dark" title="<@spring.message 'nav.home'/>">
                <i class="bi bi-house-fill fs-5"></i>
            </a>

            <a href="/posts/new" class="text-dark"
               title="<@spring.message 'nav.new.post'/>">
                <i class="bi bi-plus-square fs-5"></i>
            </a>

            <#if currentUser??>
                <a href="/users/${currentUser.username}"
                   class="text-dark"
                   title="<@spring.message 'nav.profile'/>">
                    <i class="bi bi-person-circle fs-5"></i>
                </a>
            </#if>

            <form action="/auth/logout" method="post" class="m-0">
                <#if _csrf??>
                    <input type="hidden"
                           name="${(_csrf.parameterName)!'_csrf'}"
                           value="${(_csrf.token)!''}"/>
                </#if>
                <button type="submit"
                        class="btn btn-link text-dark p-0"
                        title="<@spring.message 'nav.logout'/>">
                    <i class="bi bi-box-arrow-right fs-5"></i>
                </button>
            </form>

        </div>
    </div>
</nav>