<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>

<@main.page>

<div class="mt-2">
    <form method = "post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Login: </label>
            <div class="col-sm-6">
                <input type = "text" name = "username" id = "username" placeholder = "Your login" class="form-control" />
                <div id="loginHelpBlock" class="form-text">
                    The login must consist of 2-32 letters
                </div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password: </label>
            <div class="col-sm-6">
                <input type = "password" name = "password" id = "password" placeholder = "Your password" class="form-control"/>
            </div>
        </div>
        <@security.token />
        <button type = "submit" class="btn btn-primary">Add new admin</button>
    </form>
</div>
<div class="row mt-2">
    <#if remarks??>
        <@ul.foreach collection = remarks![] status="danger" />
    </#if>
</div>


</@main.page>