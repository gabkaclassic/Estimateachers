<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <h2><span>Applications:</span></h2>

    <#if applications??>

        <#list applications as application>
            <div class="card">
                <div class="card-header">
                    Application №${application.id}
                </div>`
                <div class="card-body">
                    <h5 class="card-title"><i>Date sending: <#if application.date??>${application.date}<#else>None</#if></i></h5>
                    <#if registry>
                        <a href="/applications/processing/first/${application.id}" class="btn btn-primary">Reject</a>
                    <#else>
                        <a href="/applications/approving/${application.id}" class="btn btn-primary">Reject</a>
                    </#if>
                </div>
            </div>
        </#list>

    </#if>

</@main.page>