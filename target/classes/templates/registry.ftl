<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <@ul.form>
        <input type = "email" id = "email" name = "email" placeholder = "Your email address" />
        <button type = "submit">Sign up</button>
    </@ul.form>

    <@ul.foreach collection = remarks![] />

    <@ul.logout />

</@main.page>