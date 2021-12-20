<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>
      <h2><span>All users:</span></h2>

      <#if users??>

          <#list users as usr>

                <div class="card" style="width: 50rem;">
                    <#if usr.filename??>
                        <img src = "/img/${usr.filename}" height = "100" weight = "150" class="img-thumbnail" />
                    <#else>
                        None
                    </#if>
                <div class="card-body">
                        <h5 class="card-title">Login: <a href = "/users/edit/${usr.id}">${usr.username}</a></h5> <#if usr.online()><u>Online</u><#else>Offline</#if>
                        <h6 class="card-subtitle mb-2 text-muted"><p>Id: ${usr.id}</p></h6>
                        <p class="card-text">
                            <p>Email: <#if usr.email??>${usr.email}<#else>None</#if></p>
                            <p>Profile photo: </p>  <br>
                            <p> Roles:
                                <@ul.foreach collection = usr.roles![] status="light" />
                            </p>
                        </p>
                    </div>
                </div>
          </#list>
        </#if>

</@main.page>