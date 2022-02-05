<#import "parts/main.ftl" as main>
<#import "parts/security.ftl" as security>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/cards_logic.ftl" as cl>

<@main.page>
    <div class="row">
        <div class="col w-50">
            <h2><span>${listName}</span></h2>
        </div>
        <div class="col-3 w-80 ml-12 mt-5 sticky-top">
            <form class="d-flex" method="post" action="/cards/search/title">
                <input class="form-control me-2" value="${title!''}" name="title" type="search" placeholder="Card's title" aria-label="Search">
                <#if collection??>
                    <input type="hidden" name="cardType" value="ALL" />
                    <input type="hidden" name="collection" value="${collection?c}" />
                <#else>
                    <input type="hidden" name="cardType" value="${cardType}" />
                </#if>
                <button class="btn btn-outline-success" type="submit">Search</button>
                <@security.token />
            </form>
        </div>
    </div>
    <div class="row mt-3 w-80">
        <#if cards??>
            <#list cards as card>
                <div class="row mt-2 fluid">
                    <@cl.card_view cardType="${card.getCardType()}" card=card />
                </div>
            </#list>
        </#if>
    </div>
</@main.page>