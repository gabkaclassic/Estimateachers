<#import "parts/main.ftl" as main>
<#import "parts/cards_logic.ftl" as cl>
<#import "parts/users_logic.ftl" as ul>

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
<div class="row mt-3">
    University:
    <form method = "get" action = "/cards/get">
        <input type="hidden" name = "cardType" value = "university" />
        <input type="hidden" name = "id" value = ${dormitory.university.id} />
        <button class="btn btn-second" type="submit"> ${dormitory.university.title}</button>
    </form>
</div>
</@main.page>