<#import "/parts/main.ftl" as main>
<#import "/parts/users_logic.ftl" as ul>

<@main.page>

<@main.user_menu />

<a href = "/admin">I am admin</a>

<@ul.logout />

</@main.page>