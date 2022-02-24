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
                    <img class="d-block card-img-top rounded-2" width="350" height="300" src="/img/${photo}" alt="?" >
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
        <div class="row mt-5">
            <form class="d-block w-100 mt-3" method = "post" enctype="multipart/form-data" action = "/cards/edit">
                <input name = "title" type = "text" placeholder = "title" value="${card.title}"/> <br>
                <label for="formFileMultiple" class="form-label mt-3">Photos</label>
                <input class="form-control mt-2" name="files" type="file" id="formFileMultiple" multiple />
                <input type="hidden" name="id" value="${card.id}" />
                <input type="hidden" name="type" value="${card.getCardType()}" />
                <#if card.getCardType()=='TEACHER'>
                    <div class="form-group row mt-3">
                        <label class="col-3 col-form-label">Excuses: </label>
                        <div class="col w-30">
                            <input type = "text" id = "excuses" name = "excuses" placeholder = "Teacher excuses" class="form-control"/>
                            <div id="excusesHelpBlock" class="form-text">
                                Enter excuses using the ';' symbol
                            </div>
                        </div>
                    </div>
                </#if>
                <@security.token />
                <button type = "submit" class="btn btn-primary mt-1">Edit</button>
            </form>
        </div>
        <div class="row mt-1">
            <form method = "post" action = "/cards/delete">
                <input type="hidden" name = "type" value = "${card.getCardType()}" />
                <input type="hidden" name = "id" value = ${card.id} />
                <@security.token />
                <button class="btn btn-danger" type="submit">Delete</button>
            </form>
        </div>
    <#else>
        <div class="row mt-5">
            <h5>${card.title}</h5>
        </div>
    </#if>
</#macro>

<#macro links cards=[]>

    <#if cards??>
    <#if cards?has_content>
        <#list cards as card>
            <li class="list-group-item mt-3">
                <form method = "get" action = "/cards/get">
                    <button class="btn btn-link" type="submit">${card.title}</button>
                    <input type="hidden" name = "cardType" value = ${card.getCardType()} />
                    <input type="hidden" name = "id" value = ${card.id} />
                </form>
            </li>
        </#list>
    <#else>
        None
    </#if>
    <#else>
        None
    </#if>
</#macro>

<#macro card_view cardType card="NULL">

    <#if card??>

        <div class="w-75 h-10 mt-2 card" style="width: 18rem height: 15rem;">

            <div class="card-body">
                <div class="row">
                    <div class="col">
                        <h5 class="card-title">${card.title}</h5>
                        <p class="card-text">
                            <#if isAdmin>
                                <p>№${card.id}</p>
                            </#if>
                            <p>Rating: ${(card.totalRating)!'-'}/5</p>
                        </p>
                        <div class="col">
                            <form method = "get" action = "/cards/get/${card.id}">
                                <input type="hidden" name = "cardType" value = "${card.getCardType()}" />
                                <button class="btn btn-primary" type="submit">To consider...</button>
                            </form>
                        </div>
                        <div class="col">
                            <form method = "get" action = "/discussions/get/${card.discussion.id}">
                                <button class="btn btn-secondary" type="submit">To discussion...</button>
                            </form>
                        </div>
                        <div class="col">
                            <#if isAdmin>
                                <form method = "post" action = "/cards/delete">
                                    <input type="hidden" name = "type" value = "${card.getCardType()}" />
                                    <input type="hidden" name = "id" value = ${card.id} />
                                    <@security.token />
                                    <button class="btn btn-danger" type="submit">Delete</button>
                                </form>
                            </#if>
                        </div>
                    </div>
                    <div class="col">
                        <#if card.photos??>
                        <figure class="figure">
                            <@images number=card.id photos=card.photos size=card.photos?size />
                        </figure>
                        <#else>
                        :)
                    </#if>
                    </div>
                </div>
            </div>
        </div>
    </#if>

</#macro>

<#macro estimation title="" name="">
    <label>${title}</label>
    <div class="star-rating mt-1">
        <div class="star-rating__wrap">
            <input class="star-rating__input" id="${title}-star-5" type="radio" name="${name}" value="5">
            <label class="star-rating__ico fa fa-star-o fa-lg" for="${title}-star-5" title="Great"></label>
            <input class="star-rating__input" id="${title}-star-4" type="radio" name="${name}" value="4">
            <label class="star-rating__ico fa fa-star-o fa-lg" for="${title}-star-4" title="Good"></label>
            <input class="star-rating__input" id="${title}-star-3" type="radio" name="${name}" value="3">
            <label class="star-rating__ico fa fa-star-o fa-lg" for="${title}-star-3" title="Satisfactory"></label>
            <input class="star-rating__input" id="${title}-star-2" type="radio" name="${name}" value="2">
            <label class="star-rating__ico fa fa-star-o fa-lg" for="${title}-star-2" title="Badly"></label>
            <input class="star-rating__input" id="${title}-star-1" type="radio" name="${name}" value="1">
            <label class="star-rating__ico fa fa-star-o fa-lg" for="${title}-star-1" title="Terribly"></label>
        </div>
    </div>
</#macro>