<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <@ul.data_form registry = true textButton = "Sign up">
        <@ul.select enum = genders![] name = "genders" />
    </@ul.data_form>

    <#if (!remarks??)>
        <@ul.foreach collection = remarks![] status="danger"/>
    </#if>


</@main.page>