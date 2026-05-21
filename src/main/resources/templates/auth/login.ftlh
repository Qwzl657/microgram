<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход — Microgram</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body class="bg-light">
<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-body p-4">

                    <h3 class="text-center fw-bold mb-4">Microgram</h3>

                    <#if RequestParameters.registered??>
                        <div class="alert alert-success">
                            Регистрация прошла успешно! Войдите в аккаунт.
                        </div>
                    </#if>

                    <#if RequestParameters.logout??>
                        <div class="alert alert-info">
                            Вы вышли из системы.
                        </div>
                    </#if>

                    <#if RequestParameters.error??>
                        <div class="alert alert-danger">
                            Неверный логин или пароль.
                        </div>
                    </#if>

                    <form action="/auth/login" method="post">

                        <input type="hidden"
                               name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>

                        <div class="mb-3">
                            <input type="text"
                                   name="username"
                                   class="form-control"
                                   placeholder="Email или логин"
                                   required>
                        </div>
                        <div class="mb-3">
                            <input type="password"
                                   name="password"
                                   class="form-control"
                                   placeholder="Пароль"
                                   required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">
                            Войти
                        </button>
                    </form>

                </div>
            </div>

            <div class="text-center mt-3">
                Нет аккаунта?
                <a href="/auth/register">Зарегистрироваться</a>
            </div>

        </div>
    </div>
</div>
</body>
</html>