<#import "/parts/main.ftl" as main>

<@main.page>

    <@main.menu user=user isAdmin=isAdmin />

    <p>№${card.id}</p> <br>
    <p>Title:
        ${card.title}
    </p> <br>
    <p>Rating: ${(card.totalRating)!'-'}</p> <br>
    <ul>
        <li></li>
    </ul>
    <p>Images: </p>  <br>
    <#if card.photos??>
    <@cl.images photos=card.photos />
    <#else>
    None
    </#if> <br>

</@main.page>