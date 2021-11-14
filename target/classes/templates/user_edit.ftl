<#import "/parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <@main.menu user=user />

    <@ul.data_form registry = false textButton = "Edit">

        Login: <input type = "text" name = "username" value = "${user.username}" />
        Password: <input type = "text" name = "password" value = "${user.password}" />
        Email: <input type = "email" name = "email" value = "${(user.email!' ')}" />
        <@ul.file type = "file" name = "profilePhoto" id = "profilePhoto" text = "Change profile photo" />

    </@ul.data_form>

    <@ul.foreach collection = remarks![] />

</@main.page>