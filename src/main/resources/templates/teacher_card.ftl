<#import "parts/main.ftl" as main>
<#import "parts/cards_logic.ftl" as cl>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>

<@main.page>

<div class="row mt-3">
    <div class="col-3">
        <@cl.title card=teacher type="teacher" />
    </div>
    <div class="col-2 mt-5">

        <form method="post" action="/cards/collection/add">
            <input type="hidden" value="${teacher.id}" name="cardId" />
            <input type="hidden" value="${teacher.getCardType()}" name="cardType" />
            <input type="hidden" value="${inCollection?c}" name="inCollection" />
            <@security.token />
            <button class="btn btn-primary" type="submit">
                <#if inCollection??>
                <#if inCollection><i class="fa fa-close"></i><#else><i class="fa fa-folder"></i></#if>
                <#else>
                <i class="fa fa-folder"></i>
            </#if>
            </button>
        </form>
    </div>
</div>
</div>

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
            <li class="list-group-item">Rating: <#if teacher.totalRating??> ${teacher.totalRating}<#else>0</#if>/10</li>
        </ul>
    </div>

<div class="modal fade" id="estimationModal" aria-hidden="true" aria-labelledby="estimationModalLabel" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="estimationModalLabel">Your estimation</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="form group">
                    <form method="post" action="/cards/estimation/teacher">
                        <@cl.estimation title="Severity rating" name="severityRating" />
                        <@cl.estimation title="Exacting rating" name="exactingRating" />
                        <@cl.estimation title="Freebies rating" name="freebiesRating" />
                        <input type="hidden" value="${teacher.id}" name="cardId" />
                        <@security.token />
                        <button class="btn-primary" type="submit">Estimate</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<#if !estimated && !isAdmin>
    <a class="btn btn-primary mt-2" data-bs-toggle="modal" href="#estimationModal" role="button">Estimate this card</a>
</#if>

<form class="mt-2" method = "get" action = "/discussions/get/${discussionId}}">
    <button class="btn btn-secondary" type="submit">To discussion...</button>
</form>
    <div class="row mt-3">
        Excuses:
        <#if teacher.excuses?has_content><@ul.foreach collection=teacher.excuses /> <#else>None</#if>
    </div>
    <div class="row mt-3">
        Universities:
        <@cl.links cards=teacher.universities />
    </div>
    <div class="row mt-3">
        Faculties:
        <@cl.links cards=teacher.faculties />
    </div>
</@main.page>