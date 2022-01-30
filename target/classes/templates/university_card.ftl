<#import "parts/main.ftl" as main>
<#import "parts/cards_logic.ftl" as cl>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>

<@main.page>

<@cl.title card=university type="university" />

<div class="row mt-5">
    <@cl.images number=0 photos=university.photos size=university.photos?size />
</div>
<div class="row mt-5">
    <div class="col-3 w-60">
        <ul class="list-group">
            <li class="list-group-item">Abbreviation: ${university.abbreviation}</li>
            <li class="list-group-item">Price: ${university.getPriceRating()}/5</li>
            <li class="list-group-item">Complexity: ${university.getComplexityRating()}/5</li>
            <li class="list-group-item">Utility: ${university.getUtilityRating()}/5</li>
            <li class="list-group-item">Rating: <#if university.totalRating??> ${university.totalRating}<#else>0</#if>/5</li>
            <li class="list-group-item">Bachelor: <#if university.bachelor> Yes <#else> None </#if></li>
            <li class="list-group-item">Magistracy: <#if university.magistracy> Yes <#else> None </#if></li>
            <li class="list-group-item">Specialty: <#if university.specialty> Yes <#else> None </#if></li>
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
                    <form method="post" action="/cards/estimation/university">
                        <@cl.estimation title="Utility rating" name="utilityRating" />
                        <@cl.estimation title="Price rating" name="priceRating" />
                        <@cl.estimation title="Complexity rating" name="complexityRating" />
                        <input type="hidden" value="${university.id}" name="cardId" />
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
    </div>
</div>
<div class="row mt-3">
    <div class="col-4">
        <h5>Faculties:</h5>
        <@cl.links cards=university.faculties cardType="faculty" />
    </div>
</div>
<div class="row mt-3">
    <div class="col-4">
        <h5>Dormitories:</h5>
        <@cl.links cards=university.dormitories cardType="dormitory" />
    </div>
</div>
<div class="row mt-4">
    <div class="col-5">
        <h5>Teachers:</h5>
        <@cl.links cards=university.teachers cardType="teacher" />
    </div>
</div>
</@main.page>