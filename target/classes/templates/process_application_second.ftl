<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>

<@main.page>

    <h2>Application №${application.id}:</h2>

        <div class="form-group row">
         <span><i>Date sending: <#if application.date??>${application.date}<#else>None</#if></i></span>
        </div>
        <div class="form-group row">
                 <span>First name: ${student.firstName}</span>
        </div>
        <div class="form-group row">
                 <span>Last name: ${student.lastName}</span>
        </div>
        <div class="form-group row">
                 <span>Email: <#if student.account.email??>${student.account.email}<#else>None</#if></span>
        </div>

         <span>Student card photo:</span>
        <#if application.filename??>
            <img src = "/img/${application.filename}" height = "150" weight = "200" />
        <#else>
            None
        </#if>

        <div class="form-group row">
                <span>University: ${university.title}</span>
        </div>
        <div class="form-group row">
                <span>Course: ${course}</span>
        </div>
        <form method = "post" action = "/applications/processing/second/${application.id}">
            <span> Faculty: <@ul.select enum = faculties name = "faculty" /> </span>
            <span> Dormitory: <@ul.select enum = dormitories name = "dormitory" /> </span>
            <input type = "hidden" name = "course" value = "${course}"/>
            <input type = "hidden" name = "universityId" value = "${university.id}"/>

            <@security.token />
            <button type = "submit" class="btn btn-success">Apply application</button>
        </form>
    </div>

    <form method = "post" action = "/applications/reject/registry/${application.id}">
        <@security.token />
        <button type = "submit" class="btn btn-danger">Reject the application</button>
    </form>
    <form method = "get" action = "/applications/users">
        <@security.token />
        <button type = "submit" class="btn btn-secondary">Exit</button>
    </form>

</@main.page>