<#import "users_logic.ftl" as ul>

<#macro images photos=[]>

    <#if photos??>
        <div id="photos_card" class="carousel slide" data-ride="carousel">
            <ul class="carousel-indicators">
                <#list numbers as n>
                    <li data-target="#photos_card" data-slide-to="${n}" class="active"></li>
                </#list>
            </ul>
            <div class="carousel-inner">
                <#list photos as photo>
                    <#if photo??>
                        <div class="carousel-item active">
                            <img src="/img/${photo}" class="card-img-top" alt="?">
                        </div>
                    <#else>
                        None
                    </#if> <br>
                </#list>
            </div>
            <a class="carousel-control-prev" href="#photos_card" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#photos_card" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
    </#if>
</#macro>

<#macro links cards=[] cardType>

<#if cards??>
    <#list cards as card>
        <li>
            <form method = "get" action = "/cards/get">
                <input type="hidden" name = "cardType" value = ${cardType} />
                <input type="hidden" name = "id" value = ${card.id} />
                ${card.title}
            </form>
        </li>
    </#list>
</#if>
</#macro>

<#macro card_view card="NULL">

    <#if card??>

        <div class="card" style="width: 18rem;">
            <#if card.photos??>
                <@cl.images photos=card.photos />
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
                <form method = "get" action = "/cards/get" class = "btn btn-primary">
                    <input type="hidden" name = "cardType" value = ${cardType} />
                    <input type="hidden" name = "id" value = ${card.id} />
                    To consider...
                </form>
            </div>
        </div>
    </#if>

</#macro>
