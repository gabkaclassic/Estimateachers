<#import "parts/main.ftl" as main>
<#import "parts/cards_logic.ftl" as cl>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>

<@main.page>

<div class="row mt-5">
    <h5>
        <b>
        <#if request.requestType == "CHANGING_CARDS">
            Changing card
        <#else>
            Operations service
        </#if>
        </b>
    </h5>
</div>
<div class="row mt-5">
    <samp>
        Text request:
        ${request.textRequest}
    </samp>
</div>
<form method = "post" action = "/applications/requests/success/${request.id}">
    <input type="hidden" name = "id" />
    <@security.token />
    <button type = "submit" class="btn btn-success">Accept</button>
</form>
<form method = "post" action = "/applications/requests/reject/${request.id}">
    <div class="form-group">
        <label for="reason">Reason of reject</label>
        <textarea class="form-control" id="reason" name="reason" rows="5"></textarea>
    </div>
    <@security.token />
    <button type = "submit" class="btn btn-danger">Reject the request</button>
</form>
<#if request.requestType == "CHANGING_CARDS">
    <form method = "get" action = "/applications/requests/cards">
<#else>
    <form method = "get" action = "/applications/requests/service">
</#if>
    <@security.token />
    <button type = "submit" class="btn btn-secondary">Exit</button>
</form>
</@main.page>