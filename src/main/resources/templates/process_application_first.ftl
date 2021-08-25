<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>

<@main.page>
    <@main.menu user=user />
    <h2>Application №${application.id}:</h2>

    <#if application.dateSending??>
         <span><i>Date sending: ${application.dateSending}</i></span> <br>
    </#if>
    <span>First name: ${application.student.firstName}</span> <br>
    <span>Last name: ${application.student.lastName}</span> <br>
    <span>Age: ${application.student.age}</span> <br>
    <span>Email: <#if application.student.account.email??>${application.student.account.email}<#else>None</#if></span> <br>
    <span>Student card photo:</span> <br>
    <#if application.filename??><img src = "/img/${application.filename}" height = "150" weight = "200" /><#else>None</#if> <br>
    <div>
        <form method = "post">
           <span> Course: <input type = "number" id = "course" name = "course" placeholder = "Course" value = "1" min = "1" max = "5" /> </span> <br>
           <span> University: <@ul.select enum = universities name = "university" /> </span> <br>
            <@security.token />
            <button type = "submit">Next step</button>
        </form>

    </div>

     <form method = "post" action = "/cards/add/university">
         <input name = "title" type = "text" placeholder = "Title university" /> <br>
         <button type = "submit">Add university</button>
     </form> <br>

    <form method = "post" action = "/admin/applications/reject/${application.id}">
        <button type = "submit">Reject the application</button>
    </form>
    <a href = "/admin/applications">Exit</a>
</@main.page>