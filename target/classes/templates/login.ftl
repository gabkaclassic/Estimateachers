<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <@ul.data_form registry = true textButton = "Sign in" />


    <@ul.foreach collection = remarks![] />

    <a href = "/users/registry">Sign up</a>

</@main.page>