<#import "security.ftl" as security>

<#macro form_login>

<div>
    <form method = "post" enctype = "multipart/form-data">
        <input type = "text" name = "username" id = "username" placeholder = "Your login" />
        <input type = "password" name = "password" id = "password"  placeholder = "Password" />
        <@security.token />
        <#nested>
    </form>
</div>

</#macro>

<#macro logout>

<form action = "/users/logout" method = "post">
    <input type = "submit" value = "Sign out" />
    <@security.token />
</form>

</#macro>

<#macro form_message>

<div>
    <form method = "post" enctype = "multipart/form-data">
        <input type = "text" name = "text" placeholder = "Your message" />
        <@file type = "file" />
        <@security.token />
        <#nested>
    </form>
</div>

</#macro>

<#macro file type>

    <input type = "${type}" name = "${type}" />

</#macro>

<#macro foreach collection=[]>

    <#if collection??>
        <#list collection as row>
            <li>${row}</li>
        </#list>
    </#if>

</#macro>

<#macro select enum=[] name="">

    <#if enum??>
        <select name="${name}">
            <#list enum as element>
                <option value="${element}">${element}</option>
            </#list>
        </select>
    </#if>

</#macro>