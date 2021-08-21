<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <@ul.form_login>

        <input type = "text" id = "firstName" name = "firstName" placeholder = "Your first name" />
        <input type = "text" id = "lastName" name = "lastName" placeholder = "Your last name" />
        <input type = "email" id = "email" name = "email" placeholder = "Your email address" />
        <@ul.select enum = genders name = "genders" />
        <input type = "number" id = "age" name = "age" placeholder = "Age" value = "18" min = "0" max = "200" />
        <@ul.file type = "file" />
        <button type = "submit">Sign up</button>

    </@ul.form_login>

    <@ul.foreach collection = remarks />

    <@ul.logout />

</@main.page>