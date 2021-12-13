<#import "parts/main.ftl" as main>
<#import "parts/cards_logic.ftl" as cl>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

<div class="row mt-5">
    <h5>${dormitory.title}</h5>
</div>
<div class="row mt-5">
    <@cl.images photos=dormitory.photos />
</div>
<div class="row mt-5">
    <ul class="list-group">
        <li class="list-group-item">Cleaning: ${dormitory.cleaningRating}/10</li>
        <li class="list-group-item">Roommates: ${dormitory.roommatesRating}/10</li>
        <li class="list-group-item">Capacity: ${dormitory.capacityRating}/10</li>
        <li class="list-group-item">Rating: ${dormitory.totalRating}/10</li>
    </ul>
</div>
<div class="row mt-3">
    University:
    <form method = "get" action = "/cards/get">
        <input type="hidden" name = "cardType" value = "university" />
        <input type="hidden" name = "id" value = ${dormitory.university.id} />
        <button class="btn btn-second" type="submit"> ${dormitory.university.title}</button>
    </form>
</div>
</@main.page>