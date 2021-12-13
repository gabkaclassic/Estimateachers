<#import "parts/main.ftl" as main>
<#import "parts/cards_logic.ftl" as cl>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

    <div class="row mt-5">
        <h5>${teacher.title}</h5>
    </div>
    <div class="row mt-5">
        <@cl.images photos=teacher.photos />
    </div>
    <div class="row mt-5">
        <ul class="list-group">
            <li class="list-group-item">First name: ${teacher.firstName}</li>
            <li class="list-group-item">Last name: ${teacher.lastName}</li>
            <li class="list-group-item">Patronymic: ${teacher.patronymic}</li>
            <li class="list-group-item">Severity: ${teacher.severityRating}/10</li>
            <li class="list-group-item">Exacting: ${teacher.exactingRating}/10</li>
            <li class="list-group-item">Freebies: ${teacher.freebiesRating}/10</li>
            <li class="list-group-item">Rating: <#if teacher.totalRating??> ${teacher.totalRating}<#else>0</#if>/10/10</li>
        </ul>
    </div>
    <div class="row mt-3">
        Excuses:
        <#if teacher.excuses?has_content><@ul.foreach collection=teacher.excuses /> <#else>None</#if>
    </div>
    <div class="row mt-3">
        Universities:
        <#if teacher.universities?has_content><@cl.links cards=teacher.universities cardType="university" /> <#else>None</#if>
    </div>
    <div class="row mt-3">
        Faculties:
        <#if teacher.faculties?has_content><@cl.links cards=teacher.faculties cardType="faculty" /> <#else>None</#if>
    </div>
</@main.page>