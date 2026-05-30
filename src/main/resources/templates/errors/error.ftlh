<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="${.locale}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${status!500} — Microgram</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body class="bg-light">
<div class="container text-center mt-5">
    <h1 class="display-1 text-danger">${status!500}</h1>
    <p class="lead text-muted">${message!'Что-то пошло не так'}</p>
    <a href="/" class="btn btn-primary">
        <@spring.message 'error.back'/>
    </a>
</div>
</body>
</html>