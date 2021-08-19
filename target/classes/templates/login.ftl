<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <@ul.form>
        <button type = "submit">Sign in</button>
    </@ul.form>

    <@ul.logout />

</@main.page>