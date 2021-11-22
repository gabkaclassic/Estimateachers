<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <@ul.data_form registry = true textButton = "Sign up">

        <input type = "text" id = "firstName" name = "firstName" placeholder = "Your first name" /> <br>
        <input type = "text" id = "lastName" name = "lastName" placeholder = "Your last name" /> <br>
        <input type = "text" id = "patronymic" name = "patronymic" placeholder = "Your patronymic" /> <br>
        <input type = "email" id = "email" name = "email" placeholder = "Your email address" /> <br>
        <@ul.select enum = genders name = "genders" /> <br>
        <@ul.file type = "file" name = "profilePhoto" id = "profilePhoto" text = "Your profile photo" /> <br>
        <@ul.file type = "file" name = "cardPhoto" id = "cardPhoto" text = "Your photo with a student card" /> <br>

    </@ul.data_form>

    <@ul.foreach collection = remarks![] />

    <a href = "/users/login">Sign in </a>

</@main.page>