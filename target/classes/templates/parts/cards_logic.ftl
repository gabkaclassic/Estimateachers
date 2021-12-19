<#import "users_logic.ftl" as ul>
<#import "security.ftl" as security>

<#macro images number photos=[] size=0>

<div id="photos_card${number}" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner">
            <#assign x = 0>
            <#list photos as photo>
                <#if photo??>
                    <#if x == 0>
                        <div class="carousel-item active" data-bs-interval="10000">
                    <#else>
                        <div class="carousel-item" data-bs-interval="2000">
                    </#if>
                    <#assign x++>
                    <img class="d-block card-img-top img-fluid rounded-2" src="/img/${photo}" alt="?" >
                </#if>
                </div>
            </#list>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#photos_card${number}"  data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#photos_card${number}"  data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>
</#macro>

<#macro title card type="">
    <#if isAdmin>
        <form class="d-block w-100 mt-3" method = "post" enctype="multipart/form-data" action = "/cards/edit">
            <input name = "title" type = "text" placeholder = "title" value="${card.title}"/> <br>
            <label for="formFileMultiple" class="form-label">Photos</label>
            <input class="form-control" name="files" type="file" id="formFileMultiple" multiple />
            <input type="hidden" name="id" value="${card.id}" />
            <input type="hidden" name="type" value="${type}" />
            <@security.token />
            <button type = "submit" class="btn btn-primary mt-1">Edit</button>
        </form>
    <#else>
        <div class="row mt-5">
            <h5>${card.title}</h5>
        </div>
    </#if>
</#macro>

<#macro links cardType cards=[]>

<#if cards??>
    <#list cards as card>
        <li class="list-group-item mt-3">
            <form method = "get" action = "/cards/get">
                <button class="btn btn-link" type="submit">${card.title}</button>
                <input type="hidden" name = "cardType" value = ${cardType} />
                <input type="hidden" name = "id" value = ${card.id} />
            </form>
        </li>
    </#list>
<#else>
    None
</#if>
</#macro>

<#macro card_view cardType card="NULL">

    <#if card??>

        <div class="card" style="width: 18rem height: 15rem;">
            <#if card.photos??>
                <figure class="figure">
                    <@images number=card.id photos=card.photos size=card.photos?size />
                </figure>
            <#else>
                :)
            </#if>
            <div class="card-body">
                <h5 class="card-title">${card.title}</h5>
                <p class="card-text">
                    <#if isAdmin>
                        <p>№${card.id}</p>
                    </#if>
                <p>Rating: ${(card.totalRating)!'-'}</p>
                </p>
                <form method = "get" action = "/cards/get">
                    <input type="hidden" name = "cardType" value = "${cardType}" />
                    <input type="hidden" name = "id" value = ${card.id} />
                    <@security.token />
                    <button class="btn btn-primary" type="submit">To consider...</button>
                </form>
            </div>
        </div>
    </#if>

</#macro>
