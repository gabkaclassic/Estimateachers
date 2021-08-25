<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

<h2><span>Applications:</span></h2>

    <#if applications??>

        <#list applications as application>

            <p><a href = "/admin/applications/processing/first/${application.id}"> Application №${application.id}</a></p> <br>
            <i>Date sending: <#if application.dateSending??>${application.dateSending}<#else>None</#if></i>

            <h1>---------------------------------------------------</h1>

        </#list>

    </#if>

</@main.page>