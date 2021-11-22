<#import "security.ftl" as security>

<#macro data_form registry textButton = "Submit">

<div>
    <form method = "post" enctype = "multipart/form-data">
        <#if registry>
            <@from_registry_or_login />
        </#if>
        <#nested>
        <@security.token />
        <button type = "submit">${textButton}</button>
    </form>
</div>

</#macro>

<#macro from_registry_or_login>

<input type = "text" name = "username" id = "username" placeholder = "Your login" /> <br>
<input type = "password" name = "password" id = "password"  placeholder = "Password" /> <br>

</#macro>

<#macro logout>

<form action = "/users/logout" method = "post">
    <input type = "hidden" value = "Sign out" />
    <@security.token />
</form>

</#macro>

<#macro form_message>

<div>
    <form method = "post" enctype = "multipart/form-data">
        <input type = "text" name = "text" placeholder = "Your message" />
        <@file type = "file" name = "file" id = "file" text = "Add file" />
        <@security.token />
        <#nested>
    </form>
</div>

</#macro>

<#macro file type name id text = "" >

    <label for = "${id}" class = "btn">${text}</label> <br>
    <input id = "${id}"  type = "${type}" name = "${name}" /> <br>

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