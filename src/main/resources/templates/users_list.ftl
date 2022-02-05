<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>

<@main.page>
    <div class="row">
      <h2><span>All users:</span></h2>
    </div>
    <div class="row mt-3">
        <div class="col">
          <#if users??>

              <#list users as usr>

                    <div class="card" style="width: 50rem;">
                        <#if usr.filename??>
                            <img src = "/img/${usr.filename}" height = "100" weight = "150" class="img-thumbnail" />
                        </#if>
                    <div class="card-body">
                        <h5 class="card-title">Login: <a href = "/users/edit/${usr.id}">${usr.username}</a></h5> <h5><#if usr.online()><u> <font color="green">&odot;</font> Online</u><#else> <font color="red">&otimes;</font> Offline</#if></h5>
                            <h6 class="card-subtitle mb-2 text-muted"><p>Id: ${usr.id}</p></h6>
                            <p class="card-text">
                                <p>Email: <#if usr.email??>${usr.email}<#else>None</#if></p>
                                <p> Roles:
                                    <@ul.foreach collection = usr.roles![] status="light" />
                                </p>
                            </p>
                        </div>
                    </div>
              </#list>
            </#if>
        </div>
        <div class="col">
            <div class="row sticky-top">
            <div class="col">
                <form class="d-flex w-5" method="post" action="/admin/search/login">
                    <input class="form-control me-2" value="${username!''}" name="username" type="search" placeholder="Username" aria-label="Search">
                    <button class="btn btn-outline-success" type="submit">Search</button>
                    <@security.token />
                </form>
            </div>
            <div class="col">
                <form class="d-flex w-75" method="post" action="/admin/search/id">
                    <input class="form-control me-2" value="${id!''}" name="id" type="search" placeholder="ID" aria-label="Search">
                    <button class="btn btn-outline-primary" type="submit">Search</button>
                    <@security.token />
                </form>
            </div>
            </div>
        </div>
    </div>

</@main.page>