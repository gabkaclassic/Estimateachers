<!--<#import "parts/main.ftl" as main>-->

<!--<@main.page>-->

<!--<form method = "post" enctype = "multipart/form-data">-->

<!--    <div class="form-group row">-->
<!--        <label class="col-sm-2 col-form-label">Login: </label>-->
<!--        <div class="col-sm-6">-->
<!--            <input type = "text" name = "username" id = "username" value="${(user.username)!''}" placeholder = "Your login" class="form-control" />-->
<!--            <#if registry>-->
<!--            <div id="loginHelpBlock" class="form-text">-->
<!--                The login must consist of 2-32 letters-->
<!--            </div>-->
<!--        </#if>-->
<!--    </div>-->
<!--    </div>-->
<!--    <div class="form-group row">-->
<!--        <label class="col-sm-2 col-form-label">Password: </label>-->
<!--        <div class="col-sm-6">-->
<!--            <input type = "password" name = "password" id = "password" placeholder = "Your password" class="form-control"/>-->
<!--        </div>-->
<!--        <#if registry>-->
<!--        <div id="passwordHelpBlock" class="form-text">-->
<!--            The password must contain 8-32 characters, of which 1 digit and 1 specified character.-->
<!--        </div>-->
<!--    </#if>-->
<!--    </div>-->
<!--    <#if registry>-->
<!--    <div class="form-group row">-->
<!--        <label class="col-sm-2 col-form-label">First name: </label>-->
<!--        <div class="col-sm-4">-->
<!--            <input type = "text" id = "firstName" name = "firstName" placeholder = "Your first name" class="form-control"/>-->
<!--            <div id="firstNameHelpBlock" class="form-text">-->
<!--                The first name must consist of 2-32 letters-->
<!--            </div>-->

<!--        </div>-->
<!--    </div>-->
<!--    <div class="form-group row">-->
<!--        <label class="col-sm-2 col-form-label">Last name: </label>-->
<!--        <div class="col-sm-4">-->
<!--            <input type = "text" id = "lastName" name = "lastName" placeholder = "Your last name" class="form-control"/>-->
<!--            <div id="lastNameHelpBlock" class="form-text">-->
<!--                The last name must consist of 2-32 letters-->
<!--            </div>-->
<!--        </div>-->
<!--    </div>-->
<!--    <div class="form-group row">-->
<!--        <label class="col-sm-2 col-form-label">Patronymic: </label>-->
<!--        <div class="col-sm-4">-->
<!--            <input type = "text" id = "patronymic" name = "patronymic" placeholder = "Your patronymic" class="form-control"/>-->
<!--            <div id="patronymicHelpBlock" class="form-text">-->
<!--                The patronymic must consist of 2-32 letters-->
<!--            </div>-->
<!--        </div>-->
<!--    </div>-->
<!--    <div class="form-group row">-->
<!--        <label class="col-sm-2 col-form-label">Email: </label>-->
<!--        <div class="col-sm-4">-->
<!--            <input type = "email" id = "email" name = "email" placeholder = "Your email address" class="form-control"/>-->
<!--        </div>-->
<!--    </div>-->
<!--    <div class="form-group row">-->
<!--        <div class="col-sm-2">-->
<!--            <#nested>-->
<!--        </div>-->
<!--    </div>-->
<!--    <div class="form-group row">-->
<!--        <div class="col">-->
<!--            <@file type = "file" name = "profilePhoto" id = "profilePhoto" text = "Your profile photo" />-->
<!--        </div>-->
<!--        <div class="col">-->
<!--            <@file type = "file" name = "cardPhoto" id = "cardPhoto" text = "Your photo with a student card" />-->
<!--        </div>-->
<!--    </div>-->
<!--</#if>-->
<!--<#if edit>-->
<!--<div class="form-group row">-->
<!--    <label class="col-sm-2 col-form-label">Email: </label>-->
<!--    <div class="col-sm-4">-->
<!--        <input type = "email" id = "email" value = "${(user.email!' ')}" name = "email" placeholder = "Your email address" class="form-control"/>-->
<!--    </div>-->
<!--</div>-->
<!--<div class="form-group row">-->
<!--    <div class="col-sm-4">-->
<!--        <@file type = "file" name = "profilePhoto" id = "profilePhoto" text = "Change profile photo" />-->
<!--    </div>-->
<!--    <div class="col-sm-4">-->
<!--        <img src="/img/${user.filename!''}" class="img-thumbnail">-->
<!--    </div>-->
<!--</div>-->
<!--</#if>-->
<!--<@security.token />-->
<!--<button type = "submit" class="btn btn-primary">${textButton}</button>-->
<!--</form>-->


<!--</@main.page>-->