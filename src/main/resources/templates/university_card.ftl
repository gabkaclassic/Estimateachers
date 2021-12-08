<#import "parts/main.ftl" as main>
<#import "parts/card_logic.ftl" as cl>
<#import "parts/user_logic.ftl" as ul>

<@main.page>

<div class="row mt-5">
    <h5>${university.abbreviation}</h5>
</div>
<div class="row mt-5">
    <@cl.images photos=${university.photos} />
</div>
<div class="row mt-5">
    <ul class="list-group">
        <li class="list-group-item">Price: ${university.priceRating}/10</li>
        <li class="list-group-item">Complexity: ${university.complexityRating}/10</li>
        <li class="list-group-item">Utility: ${university.utilityRating}/10</li>
        <li class="list-group-item">Rating: ${university.totalRating}/10</li>
        <li class="list-group-item">Bachelor: <#if ${university.bachelor}> Yes <#else> None </#if></li>
        <li class="list-group-item">Magistracy: <#if ${university.magistracy}> Yes <#else> None </#if></li>
        <li class="list-group-item">Specialty: <#if ${university.specialty}> Yes <#else> None </#if></li>
    </ul>
</div>
<div class="row mt-3">
    Faculties:
    <@cl.links cards=${faculties} cardType="faculty" />
</div>
<div class="row mt-3">
    Dormitories:
    <@cl.links cards=${dormitories} cardType="dormitory" />
</div>
<div class="row mt-3">
    Teachers:
    <@cl.links cards=${teachers} cardType="teacher" />
</div>
</@main.page>