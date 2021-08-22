<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

       <h2>Application №${application.id}:</h2>

       <span><i>Date sending: ${application.dateSending}</i></span> <br>
       <span>First name: ${application.student.firstname}</span> <br>
       <span>Last name: ${application.student.lastname}</span> <br>
       <span>Age: ${application.student.age}</span> <br>
       <span>Student card photo:</span> <br>
       <img src = "/img/${application.filename}" /> <br>

</@main.page>