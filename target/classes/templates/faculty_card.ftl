<#import "parts/main.ftl" as main>
<#import "parts/cards_logic.ftl" as cl>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

<div class="row mt-5">
    <h5>${faculty.title}</h5>
</div>
<div class="row mt-5">
    <@cl.images photos=faculty.photos />
</div>
<div class="row mt-5">
    <ul class="list-group">
        <li class="list-group-item">Price: ${faculty.priceRating}/10</li>
        <li class="list-group-item">Education: ${faculty.educationRating}/10</li>
        <li class="list-group-item">Rating: ${faculty.totalRating}/10</li>
    </ul>
</div>
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