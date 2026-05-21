<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Ошибка ${status!500}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body class="bg-light">
<div class="container text-center mt-5">
    <h1 class="display-1 text-danger">${status!500}</h1>
    <p class="lead">${message!'Что-то пошло не так'}</p>
    <a href="/" class="btn btn-primary">На главную</a>
</div>
</body>
</html>