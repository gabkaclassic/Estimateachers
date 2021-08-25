<#import "/parts/main.ftl" as main>

<@main.page>

    <@main.user_menu user=user />

    <form method = "post" enctype = "multipart/form-data">
        <input type = "text" name = "username" value = "Login: ${user.username}" />
        <input type = "text" name = "password" value = "Password: ${user.password}" />
        <input type = "email" name = "email" value = "Email: ${user.email}" />
        <@ul.file type = "file" name = "profilePhoto" id = "profilePhoto" text = "Change profile photo" />
    </form>

</@main.page>