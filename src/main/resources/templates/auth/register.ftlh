<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="${.locale}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><@spring.message 'auth.register.title'/> — Microgram</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body class="bg-light">
<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-body p-4">

                    <h3 class="text-center fw-bold mb-4">
                        <@spring.message 'auth.register.title'/>
                    </h3>

                    <#if error??>
                        <div class="alert alert-danger">${error}</div>
                    </#if>

                    <form action="/auth/register" method="post">

                        <#if _csrf??>
                            <input type="hidden"
                                   name="${(_csrf.parameterName)!'_csrf'}"
                                   value="${(_csrf.token)!''}"/>
                        </#if>

                        <div class="mb-3">
                            <label class="form-label">
                                <@spring.message 'auth.register.username'/>
                            </label>
                            <@spring.bind "user.username"/>
                            <input type="text"
                                   name="username"
                                   class="form-control ${spring.status.error?string('is-invalid', '')}"
                                   value="${spring.status.value!''}">
                            <#list spring.status.errorMessages as msg>
                                <div class="invalid-feedback">${msg}</div>
                            </#list>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">
                                <@spring.message 'auth.register.email'/>
                            </label>
                            <@spring.bind "user.email"/>
                            <input type="email"
                                   name="email"
                                   class="form-control ${spring.status.error?string('is-invalid', '')}"
                                   value="${spring.status.value!''}">
                            <#list spring.status.errorMessages as msg>
                                <div class="invalid-feedback">${msg}</div>
                            </#list>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">
                                <@spring.message 'auth.register.name'/>
                            </label>
                            <@spring.bind "user.name"/>
                            <input type="text"
                                   name="name"
                                   class="form-control"
                                   value="${spring.status.value!''}">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">
                                <@spring.message 'auth.register.password'/>
                            </label>
                            <@spring.bind "user.password"/>
                            <input type="password"
                                   name="password"
                                   class="form-control ${spring.status.error?string('is-invalid', '')}"
                                   placeholder="<@spring.message 'auth.register.password.hint'/>">
                            <#list spring.status.errorMessages as msg>
                                <div class="invalid-feedback">${msg}</div>
                            </#list>
                        </div>

                        <button type="submit" class="btn btn-primary w-100">
                            <@spring.message 'auth.register.submit'/>
                        </button>
                    </form>

                </div>
            </div>

            <div class="text-center mt-3">
                <@spring.message 'auth.register.have.account'/>
                <a href="/auth/login">
                    <@spring.message 'nav.login'/>
                </a>
            </div>

        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
</script>
</body>
</html>