<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>

<@main.page>

       <h2>Application №${application.id}:</h2>


        <span><i>Date sending:<#if application.dateSending??>${application.dateSending}<#else>None</#if></i></span> <br>

        <span>First name: ${application.student.firstName}</span> <br>
        <span>Last name: ${application.student.lastName}</span> <br>
        <span>Age: ${application.student.age}</span> <br>
        <span>Email: <#if application.student.account.email??>${application.student.account.email}<#else>None</#if></span> <br>
        <span>Student card photo:</span> <br>
        <#if application.filename??><img src = "/img/${application.filename}" height = "150" weight = "200" /><#else>None</#if> <br>
        <div>
       <span>University: ${university.title}</span> <br>
       <span>Course: ${course}</span>
       <div>
           <form method = "post" action = "/admin/applications/processing/second/${application.id}">
               <span> Faculty: <@ul.select enum = faculties name = "faculty" /> </span> <br>
               <span> Dormitory: <@ul.select enum = dormitories name = "dormitory" /> </span> <br>
               <input type = "hidden" name = "course" value = "${course}"/>
               <input type = "hidden" name = "universityId" value = "${university.id}"/>
               <@security.token />
               <button type = "submit">Apply application</button>
           </form>
       </div>
        <form method = "post" action = "/admin/addFaculty">
            <input name = "title" type = "text" placeholder = "Title faculty" /> <br>
            <input type = "hidden" name = "universityId" value = "${university.id}"/>
            <@security.token />
            <button type = "submit">Add faculty</button>
        </form> <br>
        <form method = "post" action = "/admin/addDormitory">
            <input name = "title" type = "text" placeholder = "Title dormitory" /> <br>
            <input type = "hidden" name = "universityId" value = "${university.id}" />
            <@security.token />
            <button type = "submit">Add dormitory</button>
        </form> <br>
       <form method = "post" action = "/admin/applications/reject/${application.id}">
           <@security.token />
           <button type = "submit">Reject the application</button>
       </form>
       <a href = "/admin/applications">Exit</a>

</@main.page>