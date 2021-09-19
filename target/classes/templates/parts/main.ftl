<#import "users_logic.ftl" as ul>

<#macro page>
<html lang = "en">

<head>

    <meta charset="UTF-8">
    <title>Esimateachers</title>
    <link href = "/main_style.css">
</head>

<body>

<#nested>


</body>

</html>
</#macro>

<#macro menu user="NULL">

    <#if user?? && user != "NULL">
        <#if isAdmin>
            <a href = "/admin/allUsers">All users</a>
            <a href = "/admin/applications">Applications</a>
        </#if>
        <a href = "/users/chats">Chats</a>
        <a href = "/users/cards">Cards</a>
    <#else>
        <a href = "/users/login">Sign in</a>
        <a href = "/users/registry">Sign up</a>
    </#if>

    <@ul.logout />

</#macro>
