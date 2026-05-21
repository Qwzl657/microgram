<#if isOwnProfile?? && isOwnProfile>
    <a href="/users/edit" class="btn btn-outline-secondary btn-sm">
        Редактировать профиль
    </a>
<#else>
    <form action="/users/${profileUser.username}/subscribe"
          method="post"
          class="subscribe-form m-0"
          data-username="${profileUser.username}">
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
        <#if isFollowing?? && isFollowing>
            <button type="submit"
                    class="subscribe-btn btn btn-outline-secondary btn-sm"
                    data-following="true">
                Отписаться
            </button>
        <#else>
            <button type="submit"
                    class="subscribe-btn btn btn-primary btn-sm"
                    data-following="false">
                Подписаться
            </button>
        </#if>
    </form>
</#if>

<div class="d-flex gap-4 mb-3">
    <div>
        <span class="fw-bold">${profileUser.postCount!0}</span>
        <span class="text-muted"> публикаций</span>
    </div>
    <div>
        <span class="fw-bold followers-count">${profileUser.followersCount!0}</span>
        <span class="text-muted"> подписчиков</span>
    </div>
    <div>
        <span class="fw-bold">${profileUser.followingCount!0}</span>
        <span class="text-muted"> подписок</span>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/js/subscribe.js"></script>
<script src="/js/main.js"></script>