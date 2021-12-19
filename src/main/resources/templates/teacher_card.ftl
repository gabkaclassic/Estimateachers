<#import "parts/main.ftl" as main>
<#import "parts/cards_logic.ftl" as cl>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

<@cl.title card=teacher type="teacher" />

    <div class="row mt-5">
        <@cl.images number=0 photos=teacher.photos size=teacher.photos?size />
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
        <@cl.links cards=teacher.universities cardType="university" />
    </div>
    <div class="row mt-3">
        Faculties:
        <@cl.links cards=teacher.faculties cardType="faculty" />
    </div>
</@main.page>