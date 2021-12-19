<#import "parts/main.ftl" as main>
<#import "parts/security.ftl" as security>

<@main.page>

<div class="row">
    <h2>Sorry, this application is already viewed</h2>
</div>

<div class="row">
    <form method = "get" action = "/applications/users">
        <@security.token />
        <button type = "submit" class="btn btn-secondary">Go to registration applications list</button>
    </form>
    <form method = "get" action = "/applications/cards">
        <@security.token />
        <button type = "submit" class="btn btn-secondary">Go to cards applications list</button>
    </form>
</div>

</@main.page>