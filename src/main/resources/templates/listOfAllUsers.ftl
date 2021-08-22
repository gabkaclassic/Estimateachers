<#import "parts/main.ftl" as main>

<@main.page>

        <h2><span>All users:</span></h2>

        <#if users??>
            <#list users as user>
                <p>${user.username}</p>

            </#list>
        </#if>

</@main.page>