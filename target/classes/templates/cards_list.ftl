<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/cards_logic.ftl" as cl>

<@main.page>
    <@main.menu user=user />
    <h2><span>${listName}</span></h2>

    <#if cards??>

        <#list cards as card>

        <p>№${card.id}</p> <br>
        <p>Title:
            <form method = "post" action = "/cards/get">
                <input type="hidden" name = "cardType" value = ${cardType} />
                <input type="hidden" name = "id" value = ${card.id} />
                <button type = "submit">${card.title}</button>
            </form>
        </p> <br>
        <p>Rating: ${(card.rating)!'-'}</p> <br>
        <p>Images: </p>  <br>
        <#if card.photos??>
            <@cl.images photos=card.photos />
        <#else>
            None
        </#if> <br>

        <h1>---------------------------------------------------</h1>  <br>

        </#list>
    </#if>

</@main.page>