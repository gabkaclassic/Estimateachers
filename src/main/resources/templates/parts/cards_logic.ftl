<#import "users_logic.ftl" as ul>

<#macro images photos=[]>

    <#if photos??>
        <#list photos as photo>
            <#if photo??>
                <img src="/img/${photo}" class="card-img-top" alt="?">
            <#else>
                None
            </#if> <br>
        </#list>
    </#if>


</#macro>

<#macro card_view card="NULL">

    <#if card??>

        <div class="card" style="width: 18rem;">
            <#if card.photos??>
            <@cl.images photos=card.photos />
            <#else>
            <div class="card-body">
                <h5 class="card-title">${card.title}</h5>
                <p class="card-text">
                    <#if isAdmin>
                        <p>№${card.id}</p>
                    </#if>
                <p>Rating: ${(card.totalRating)!'-'}</p>
                </p>
                <form method = "post" action = "/cards/get" class = "btn btn-primary">
                    <input type="hidden" name = "cardType" value = ${cardType} />
                    <input type="hidden" name = "id" value = ${card.id} />
                    To consider...
                </form>
            </div>
        </div>
    </#if>
</#macro>
