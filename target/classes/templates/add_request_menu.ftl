<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>
<#import "parts/date.ftl" as date>

<@main.page>
    <h2>Creating new request</h2>

    <form method = "post" action = "/applications/requests" enctype="multipart/form-data">

        <div class="form-group">
            <label for="request_text">Text of request</label>
            <textarea class="form-control" id="request_text" name="text" rows="10"><#if text??>${text}</#if></textarea>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="radio" name="type" id="changing_cards" value="CHANGING_CARDS" />
            <label class="form-check-label" for="changing_cards">
                Changing cards
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="radio" name="type" id="operations_service" value="OPERATIONS_SERVICE" />
            <label class="form-check-label" for="operations_service">
                Operations of service
            </label>
        </div>
        <div class="form-group">
            <input type="reset" value="Clear" />
        </div>
        <label for="formFileMultiple" class="form-label">Photos</label>
        <input class="form-control" name="files" type="file" id="formFileMultiple" multiple />
        <@date.date />
        <@security.token />
        <button type = "submit" class="btn btn-primary">Send</button>
    </form>
    <#if remarks??>
        <@ul.foreach collection = remarks![] status="danger" />
    </#if>

</@main.page>