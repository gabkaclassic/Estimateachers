<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <@ul.form>
        <button type = "submit">Sign up</button>
    </@ul.form>

    <#if remarks??>
        <#list remarks as remark>
            <li>${remark}</li>
        </list>
    </#if>

    <@ul.logout />

</@main.page>