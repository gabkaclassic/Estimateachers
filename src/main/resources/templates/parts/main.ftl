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

<#macro menu user="NULL" isAdmin=false>

    <#if user?? && user != "NULL">
        <#if isAdmin>
            <a href = "/admin/allUsers">All users</a>
            <a href = "/admin/applications">Applications</a>
        </#if>
        <a href = "/users/edit/${user.id}">Edit profile</a>
        <a href = "/chats">Chats</a>
        <a href = "/cards/">Cards</a>
    <#else>
        <a href = "/users/login">Sign in</a>
        <a href = "/users/registry">Sign up</a>
    </#if>

    <@ul.logout />

</#macro>

<#macro cards_menu>

    <a href = "/cards/universities">Universities</a>
    <a href = "/cards/dormitories">Dormitories</a>
    <a href = "/cards/faculties">Faculties</a>
    <a href = "/cards/teachers">Teachers</a>

</#macro>

