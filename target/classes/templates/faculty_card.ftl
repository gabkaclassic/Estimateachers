<#import "parts/main.ftl" as main>
<#import "parts/cards_logic.ftl" as cl>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>

<@main.page>

<@cl.title card=faculty type="faculty" />

<div class="row mt-5">
    <@cl.images number=0 photos=faculty.photos size=faculty.photos?size />
</div>
<div class="row mt-5">
    <ul class="list-group">
        <li class="list-group-item">Price: ${faculty.priceRating}/10</li>
        <li class="list-group-item">Education: ${faculty.educationRating}/10</li>
        <li class="list-group-item">Rating: ${faculty.totalRating}/10</li>
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
                    <form method="post" action="/cards/estimation/faculty">
                        <@cl.estimation title="Education rating" name="educationRating" />
                        <@cl.estimation title="Price rating" name="priceRating" />
                        <input type="hidden" value="${faculty.id}" name="cardId" />
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
        <input type="hidden" name = "id" value = ${faculty.university.id} />
        ${faculty.university.title}
    </form>
</div>
<div class="row mt-3">
    Teachers:
    <@cl.links cards=faculty.teachers cardType="teacher" />
</div>
</@main.page>