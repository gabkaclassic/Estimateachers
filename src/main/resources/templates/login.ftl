<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <@ul.form_login>
        <button type = "submit">Sign in</button>
    </@ul.form_login>

    <@ul.foreach collection = remarks![] />

    <@ul.logout />

</@main.page>