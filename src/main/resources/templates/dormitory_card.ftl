<#import "parts/main.ftl" as main>
<#import "parts/cards_logic.ftl" as cl>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>

<@main.page>

<@cl.title card=dormitory type="dormitory" />

<div class="row mt-5">
    <@cl.images number=0 photos=dormitory.photos size=dormitory.photos?size />
</div>
<div class="row mt-5">
    <ul class="list-group">
        <li class="list-group-item">Cleaning: ${dormitory.cleaningRating}/10</li>
        <li class="list-group-item">Roommates: ${dormitory.roommatesRating}/10</li>
        <li class="list-group-item">Capacity: ${dormitory.capacityRating}/10</li>
        <li class="list-group-item">Rating: ${dormitory.totalRating}/10</li>
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
        <input type="hidden" name = "cardType" value = "university" />
        <input type="hidden" name = "id" value = ${dormitory.university.id} />
        <button class="btn btn-second" type="submit"> ${dormitory.university.title}</button>
    </form>
</div>
</@main.page>