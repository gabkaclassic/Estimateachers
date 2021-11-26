<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/cards_logic.ftl" as cl>

<@main.page>
    <h2><span>${listName}</span></h2>

    <#if cards??>

        <#list cards as card>

            <@cl.card_view card=card />
        </#list>
    </#if>

</@main.page>