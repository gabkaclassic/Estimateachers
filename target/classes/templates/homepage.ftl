<#import "/parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <a href = "/users/registry">Sign up</a>
    <a href = "/users/login">Sign in</a>
    <a href = "/users/allUsers">List of all users</a>

    <@ul.logout />

</@main.page>