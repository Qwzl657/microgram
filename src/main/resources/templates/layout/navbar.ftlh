<#import "/spring.ftl" as spring>

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
                <input type="hidden"
                       name="${(_csrf.parameterName)!'_csrf'}"
                       value="${(_csrf.token)!''}"/>
                <button type="submit"
                        class="btn btn-link text-dark p-0"
                        title="<@spring.message 'nav.logout'/>">
                    <i class="bi bi-box-arrow-right fs-5"></i>
                </button>
            </form>

            <div class="dropdown">
                <button class="btn btn-sm btn-outline-secondary dropdown-toggle"
                        type="button"
                        data-bs-toggle="dropdown">
                    <i class="bi bi-translate"></i>
                </button>
                <ul class="dropdown-menu dropdown-menu-end">
                    <li>
                        <a class="dropdown-item" href="?lang=ru">
                            <@spring.message 'nav.lang.ru'/>
                        </a>
                    </li>
                    <li>
                        <a class="dropdown-item" href="?lang=en">
                            <@spring.message 'nav.lang.en'/>
                        </a>
                    </li>
                </ul>
            </div>

        </div>
    </div>
</nav>