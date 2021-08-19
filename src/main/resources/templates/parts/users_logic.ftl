<#macro form>

<div>
    <form method = "post">
        <input type = "text" name = "username" id = "username" placeholder = "Your login" />
        <input type = "password" name = "password" id = "password"  placeholder = "Password" />
        <input type = "hidden" name = "_csrf" value = "${_csrf.token}" />
        <#nested>
    </form>
</div>

</#macro>

<#macro logout>

    <form action = "/users/logout" method = "post">
        <input type = "submit" value = "Sign out" />
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>

</#macro>