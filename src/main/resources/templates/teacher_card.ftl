<#import "parts/main.ftl" as main>
<#import "parts/card_logic.ftl" as cl>
<#import "parts/user_logic.ftl" as ul>

<@main.page>

    <div class="row mt-5">
        <h5>${teacher.title}</h5>
    </div>
    <div class="row mt-5">
        <@cl.images photos=${teacher.photos} />
    </div>
    <div class="row mt-5">
        <ul class="list-group">
            <li class="list-group-item">First name: ${teacher.firstname}</li>
            <li class="list-group-item">Last name: ${teacher.lastname}</li>
            <li class="list-group-item">Patronymic: ${teacher.patronymic}</li>
            <li class="list-group-item">Age: ${teacher.age}</li>
            <li class="list-group-item">Severity: ${teacher.severityRating}/10</li>
            <li class="list-group-item">Exacting: ${teacher.exactingRating}/10</li>
            <li class="list-group-item">Freebies: ${teacher.freebiesRating}/10</li>
            <li class="list-group-item">Rating: ${teacher.totalRating}/10</li>
        </ul>
    </div>
    <div class="row mt-3">
        <@ul.foreach collection=${teacher.excuses} />
    </div>
    <div class="row mt-3">
        Universities:
        <@cl.links cards=${universities} cardType="university" />
    </div>
    <div class="row mt-3">
        Faculties:
        <@cl.links cards=${faculties} cardType="faculty" />
    </div>
</@main.page>