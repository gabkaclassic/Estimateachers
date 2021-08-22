<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <@ul.data_form textButton = "Sign in">

    </@ul.data_form>

    <@ul.foreach collection = remarks![] />

    <@ul.logout />

</@main.page>