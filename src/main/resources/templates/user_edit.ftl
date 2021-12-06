<#import "/parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <@ul.data_form registry = false edit = true textButton = "Edit" />

    <#if remarks??>
        <@ul.foreach collection = remarks![] status="danger"/>
    </#if>

</@main.page>