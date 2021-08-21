<#import "parts/main.ftl" as main>

<@main.page>

        <h2><span>All users:</span></h2>

        <#if users??>
            <#list users as user>
                <p>${user.username}</p>
                <#if user.<img></img>
            </#list>
        </#if>

</@main.page>