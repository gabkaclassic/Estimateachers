<#macro page>
    <html lang = "en">

        <head>

            <meta charset="UTF-8">
            <title>Esimateachers</title>
            <link href = "/main_style.css">
        </head>

        <body>

        <#nested>


        </body>

    </html>
</#macro>

<#macro admin_menu>

    <a href = "/admin/allUsers">All users</a>
    <a href = "/admin/applications">Applications</a>

</#macro>

<#macro user_menu>

    <a href = "/users/registry">Sign up</a>
    <a href = "/users/login">Sign in</a>


</#macro>