<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>

<@main.page>
    <h2>Application №${application.id}:</h2>


    <div class="form-group row">
        <span><i>Date sending: <#if application.date??>${application.date}<#else>None</#if></i></span>
    </div>

    <div class="form-group row">
        <b><span>${application.cardType}</span></b>
    </div>
    <div class="form-group row">
        ${card.title}
    </div>
    <#if cardType == "UNIVERSITY">
        <ul>
            <li class="list-group-item">Bachelor: <#if card.bachelor> Yes <#else> None </#if></li>
            <li class="list-group-item">Magistracy: <#if card.magistracy> Yes <#else> None </#if></li>
            <li class="list-group-item">Specialty: <#if card.specialty> Yes <#else> None </#if></li>
        </ul>
    <#elseif cardType == "DORMITORY">
        <div class="row mt-3">
            University:
            <form method = "get" action = "/cards/get">
                <input type="hidden" name = "cardType" value = "university" />
                <input type="hidden" name = "id" value = ${card.university.id} />
                <@security.token />
                <button class="btn btn-second" type="submit"> ${card.university.title}</button>
            </form>
        </div>
    <#elseif cardType == "FACULTY">
        <div class="row mt-3">
            University:
            <form method = "get" action = "/cards/get">
                <input type="hidden" name = "cardType" value = "university" />
                <input type="hidden" name = "id" value = ${faculty.university.id} />
                ${faculty.university.title}
                <@security.token />
            </form>
        </div>
        <div class="row mt-3">
            Teachers:
            <@cl.links cards=faculty.teachers cardType="teacher" />
        </div>
    <#else>
        <div class="row mt-5">
            <ul class="list-group">
                <li class="list-group-item">First name: ${card.firstName}</li>
                <li class="list-group-item">Last name: ${card.lastName}</li>
                <li class="list-group-item">Patronymic: ${card.patronymic}</li>
            </ul>
        </div>
        <div class="row mt-3">
            Excuses:
            <#if card.excuses?has_content><@ul.foreach collection=card.excuses /> <#else>None</#if>
        </div>
        <div class="row mt-3">
            Universities:
            <#if card.universities?has_content><@cl.links cards=card.universities cardType="university" /> <#else>None</#if>
        </div>
        <div class="row mt-3">
            Faculties:
            <#if card.faculties?has_content><@cl.links cards=card.faculties cardType="faculty" /> <#else>None</#if>
        </div>
    </#if>
    <form method = "post" action = "/applications/approving">
        <input type="hidden" name = "id" value="${application.id}" />
        <@security.token />
        <button type = "submit" class="btn btn-success">Approve</button>
    </form>
    <form method = "post" action = "/applications/reject/card/${application.id}">
        <@security.token />
        <button type = "submit" class="btn btn-danger">Reject the application</button>
    </form>
    <form method = "get" action = "/applications">
        <@security.token />
        <button type = "submit" class="btn btn-secondary">Exit</button>
    </form>
</@main.page>