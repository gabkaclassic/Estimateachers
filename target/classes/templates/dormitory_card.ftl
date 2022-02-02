<#import "parts/main.ftl" as main>
<#import "parts/cards_logic.ftl" as cl>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>

<@main.page>

<div class="row mt-3">
    <div class="col-3">
        <@cl.title card=dormitory type="dormitory" />
    </div>
    <div class="col-2 mt-5">

        <form method="post" action="/cards/collection/add">
            <input type="hidden" value="${dormitory.id}" name="cardId" />
            <input type="hidden" value="${dormitory.getCardType()}" name="cardType" />
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
    <@cl.images number=0 photos=dormitory.photos size=dormitory.photos?size />
</div>
<div class="row mt-5">
    <div class="col-3 w-60">
        <ul class="list-group">
            <li class="list-group-item">Cleaning: ${dormitory.cleaningRating}/10</li>
            <li class="list-group-item">Roommates: ${dormitory.roommatesRating}/10</li>
            <li class="list-group-item">Capacity: ${dormitory.capacityRating}/10</li>
            <li class="list-group-item">Rating: ${dormitory.totalRating}/10</li>
        </ul>

        <div class="modal fade" id="estimationModal" aria-hidden="true" aria-labelledby="estimationModalLabel" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="estimationModalLabel">Your estimation</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="form group">
                            <form method="post" action="/cards/estimation/dormitory">
                                <@cl.estimation title="Cleaning rating" name="cleaningRating" />
                                <@cl.estimation title="Roommates rating" name="roommatesRating" />
                                <@cl.estimation title="Capacity rating" name="capacityRating" />
                                <input type="hidden" value="${dormitory.id}" name="cardId" />
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
<div class="row mt-3">
    University:
    <form method = "get" action = "/cards/get">
        <input type="hidden" name = "cardType" value = "${dormitory.university.getCardType()}" />
        <input type="hidden" name = "id" value = ${dormitory.university.id} />
        <button class="btn btn-second" type="submit"> ${dormitory.university.title}</button>
    </form>
</div>
</@main.page>