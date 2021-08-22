<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

        <h2><span>All users:</span></h2>

        <#if users??>

            <#list users as user>

                <p>Id: ${user.id}</p>
                <p>Login: <a href = "/users/edit/${user.id}" value = ${user.username} /></p>
                <p>Password: ${user.password}</p>
                <p>Email: ${user.email}</p>
                <p><img src = "/img/${user.filename}" /></p>
                <p> Roles:
                    <@ul.foreach collection = user.roles![] />
                </p>
                <h1>---------------------------------------------------</h1>

            </#list>

        </#if>

</@main.page>