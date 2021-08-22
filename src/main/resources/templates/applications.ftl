<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

<h2><span>Applications:</span></h2>

    <#if applications??>

        <#list applications as application>

            <p><a value = "Application №${application.id}" href = "/admin/application/processing/${application.id}" /> </p> <br>
            <i>Date sending: ${application.dateSending}</i>

            <h1>---------------------------------------------------</h1>

        </#list>

    </#if>

</@main.page>