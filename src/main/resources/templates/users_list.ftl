<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>
      <h2><span>All users:</span></h2>

      <#if users??>

          <#list users as usr>

                <div class="card" style="width: 50rem;">
                    <div class="card-body">
                        <h5 class="card-title">Login: <a href = "/users/edit/${usr.id}">${usr.username}</a></h5>
                        <h6 class="card-subtitle mb-2 text-muted"><p>Id: ${usr.id}</p></h6>
                        <p class="card-text">
                            <p>Email: <#if usr.email??>${usr.email}<#else>None</#if></p>
                            <p>Profile photo: </p>  <br>
                            <p><img width="300" height="200" src = "/img/${usr.filename!''}" /></p>
                            <p> Roles:
                                <@ul.foreach collection = usr.roles![] status="light" />
                            </p>
                        </p>
                    </div>
                </div>
          </#list>
        </#if>

</@main.page>