<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="${.locale}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><@spring.message 'auth.login.title'/> — Microgram</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body class="bg-light">
<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-body p-4">

                    <h3 class="text-center fw-bold fst-italic mb-4">
                        Microgram
                    </h3>

                    <#if RequestParameters.registered??>
                        <div class="alert alert-success">
                            <@spring.message 'auth.login.registered'/>
                        </div>
                    </#if>

                    <#if RequestParameters.logout??>
                        <div class="alert alert-info">
                            <@spring.message 'auth.login.logout'/>
                        </div>
                    </#if>

                    <#if RequestParameters.error??>
                        <div class="alert alert-danger">
                            <@spring.message 'auth.login.error'/>
                        </div>
                    </#if>


                    <form action="/auth/login" method="post">

                        <#if _csrf??>
                            <input type="hidden"
                                   name="${(_csrf.parameterName)!'_csrf'}"
                                   value="${(_csrf.token)!''}"/>
                        </#if>

                        <div class="mb-3">
                            <label class="form-label">
                                <@spring.message 'auth.login.email'/>
                            </label>
                            <input type="text"
                                   name="username"
                                   class="form-control"
                                   required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">
                                <@spring.message 'auth.login.password'/>
                            </label>
                            <input type="password"
                                   name="password"
                                   class="form-control"
                                   required>
                        </div>

                        <button type="submit" class="btn btn-primary w-100">
                            <@spring.message 'auth.login.submit'/>
                        </button>
                    </form>

                </div>
            </div>

            <div class="text-center mt-3">
                <a href="/auth/register">
                    <@spring.message 'auth.login.register'/>
                </a>
            </div>

        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
</script>
</body>
</html>