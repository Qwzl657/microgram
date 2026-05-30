<#import "/spring.ftl" as spring>
<#macro base title="Microgram">
    <!DOCTYPE html>
    <html lang="${.locale}">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${title} — Microgram</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
              rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"
              rel="stylesheet">
    </head>
    <body class="bg-light">

    <#include "/layout/navbar.ftlh">

    <div class="container mt-4">
        <#nested>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
    </script>
    </body>
    </html>
</#macro>