<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация — Microgram</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body class="bg-light">
<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-body p-4">

                    <h3 class="text-center fw-bold mb-4">Регистрация</h3>

                    <#if error??>
                        <div class="alert alert-danger">${error}</div>
                    </#if>

                    <form action="/auth/register" method="post">

                        <input type="hidden"
                               name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>

                        <div class="mb-3">
                            <input type="text"
                                   name="username"
                                   class="form-control
                                   <#if user?? && user.username??>
                                       <@spring.bind "user.username"/>
                                       <#if spring.status.error>is-invalid</#if>
                                   </#if>"
                                   placeholder="Имя пользователя"
                                   value="${user.username!''}">
                            <@spring.bind "user.username"/>
                            <#list spring.status.errorMessages as msg>
                                <div class="invalid-feedback d-block">${msg}</div>
                            </#list>
                        </div>

                        <div class="mb-3">
                            <input type="email"
                                   name="email"
                                   class="form-control"
                                   placeholder="Email"
                                   value="${user.email!''}">
                            <@spring.bind "user.email"/>
                            <#list spring.status.errorMessages as msg>
                                <div class="invalid-feedback d-block">${msg}</div>
                            </#list>
                        </div>

                        <div class="mb-3">
                            <input type="text"
                                   name="name"
                                   class="form-control"
                                   placeholder="Имя (необязательно)"
                                   value="${user.name!''}">
                        </div>

                        <div class="mb-3">
                            <input type="password"
                                   name="password"
                                   class="form-control"
                                   placeholder="Пароль (мин. 6 символов)">
                            <@spring.bind "user.password"/>
                            <#list spring.status.errorMessages as msg>
                                <div class="invalid-feedback d-block">${msg}</div>
                            </#list>
                        </div>

                        <button type="submit" class="btn btn-primary w-100">
                            Зарегистрироваться
                        </button>
                    </form>

                </div>
            </div>

            <div class="text-center mt-3">
                Уже есть аккаунт?
                <a href="/auth/login">Войти</a>
            </div>

        </div>
    </div>
</div>
</body>
</html>