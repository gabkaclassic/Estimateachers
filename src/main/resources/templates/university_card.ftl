<#import "parts/main.ftl" as main>
<#import "parts/cards_logic.ftl" as cl>
<#import "parts/users_logic.ftl" as ul>

<@main.page>

<@cl.title card=university type="university" />

<div class="row mt-5">
    <@cl.images number=0 photos=university.photos size=university.photos?size />
</div>
<div class="row mt-5">
    <ul class="list-group">
        <li class="list-group-item">Abbreviation: ${university.abbreviation}</li>
        <li class="list-group-item">Price: ${university.priceRating}/10</li>
        <li class="list-group-item">Complexity: ${university.complexityRating}/10</li>
        <li class="list-group-item">Utility: ${university.utilityRating}/10</li>
        <li class="list-group-item">Rating: <#if university.totalRating??> ${university.totalRating}<#else>0</#if>/10</li>
        <li class="list-group-item">Bachelor: <#if university.bachelor> Yes <#else> None </#if></li>
        <li class="list-group-item">Magistracy: <#if university.magistracy> Yes <#else> None </#if></li>
        <li class="list-group-item">Specialty: <#if university.specialty> Yes <#else> None </#if></li>
    </ul>
</div>
<div class="row mt-3">
    Faculties:
    <@cl.links cards=university.faculties cardType="faculty" />
</div>
<div class="row mt-3">
    Dormitories:
    <@cl.links cards=university.dormitories cardType="dormitory" />
</div>
<div class="row mt-3">
    Teachers:
    <@cl.links cards=university.teachers cardType="teacher" />
</div>
</@main.page>