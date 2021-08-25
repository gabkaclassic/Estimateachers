<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

        <h2><span>All users:</span></h2>

        <#if users??>

            <#list users as user>

                <p>Id: ${user.id}</p> <br>
                <p>Login: <a href = "/users/edit/${user.id}">${user.username}</a></p> <br>
                <p>Password: ${user.password}</p> <br>
                <p>Email: <#if user.email??>${user.email}<#else>None</#if></p> <br>
                <p>Profile photo: </p>  <br>
                <#if user.filename??><p><img src = "/img/${user.filename}" /></p><#else>None</#if> <br>
                <p> Roles:
                    <@ul.foreach collection = user.roles![] />
                </p>  <br>
                <h1>---------------------------------------------------</h1>  <br>

            </#list>

        </#if>

</@main.page>