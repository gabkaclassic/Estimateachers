<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <@ul.data_form registry = false textButton = "Sign in" />


    <#if remarks??>
        <@ul.foreach collection = remarks![] status="danger"/>
    </#if>

</@main.page>