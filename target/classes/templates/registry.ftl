<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <@ul.data_form textButton = "Sign up">

        <input type = "text" id = "firstName" name = "firstName" placeholder = "Your first name" /> <br>
        <input type = "text" id = "lastName" name = "lastName" placeholder = "Your last name" /> <br>
        <input type = "email" id = "email" name = "email" placeholder = "Your email address" /> <br>
        <@ul.select enum = genders name = "genders" /> <br>
        <input type = "number" id = "age" name = "age" placeholder = "Age" value = "18" min = "0" max = "200" /> <br>
        <@ul.file type = "file" name = "profilePhoto" id = "profilePhoto" text = "Your profile photo" /> <br>
        <@ul.file type = "file" name = "cardPhoto" id = "cardPhoto" text = "Your photo with a student card" /> <br>

    </@ul.data_form>

    <@ul.foreach collection = remarks />

    <@ul.logout />

</@main.page>