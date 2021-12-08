<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>

<@main.page>
    <h2>Application №${application.id}:</h2>


    <div class="form-group row">
        <span><i>Date sending: <#if application.date??>${application.date}<#else>None</#if></i></span>
    </div>
    <div class="form-group row">
        <span>First name: ${student.firstName!'None'}</span>
    </div>
    <div class="form-group row">
        <span>Last name: ${student.lastName!'None'}</span>
    </div>
    <div class="form-group row">
        <span>Email: <#if student.account.email??>${student.account.email}<#else>None</#if></span>
    </div>
    <div class="form-group row">
        <span>Student card photo:</span> <br/>
        <#if application.filename??>
            <img src = "/img/${application.filename}" height = "300" weight = "400" class="img-thumbnail" />
        <#else>
            None
        </#if>
    </div>
        <form method = "post">
            <div class="form-group row">
                <span> Course: <input type = "number" id = "course" name = "course" placeholder = "Course" value = "1" min = "1" max = "5" /> </span>
            </div>
            <div class="form-group row">
                <span> University: <@ul.select enum = universities name = "university" /> </span>
            </div>
            <@security.token />
            <button type = "submit" class="btn btn-success">Next step</button>
        </form>
    </div>

    <form method = "post" action = "/admin/applications/reject/${application.id}">
        <button type = "submit" class="btn btn-danger">Reject the application</button>
    </form>

    <form method = "get" action = "/admin/applications">
        <@security.token />
        <button type = "submit" class="btn btn-secondary">Exit</button>
    </form>
</@main.page>