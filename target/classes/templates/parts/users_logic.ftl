<#import "security.ftl" as security>
<#import "date.ftl" as date>

<#macro data_form registry=false edit=false textButton = "Submit">

<div>
    <form method = "post" enctype = "multipart/form-data">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Login: </label>
            <div class="col-sm-6">
                <input type = "text" name = "username" id = "username" value="${(user.username)!''}" placeholder = "Your login" class="form-control" />
                <#if registry>
                    <div id="loginHelpBlock" class="form-text">
                        The login must consist of 2-32 letters
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password: </label>
            <div class="col-sm-6">
                <input type = "password" name = "password" id = "password" placeholder = "Your password" class="form-control"/>
            </div>
            <#if registry>
                <div id="passwordHelpBlock" class="form-text">
                    The password must contain 8-32 characters, of which 1 digit and 1 specified character.
                </div>
            </#if>
        </div>
        <#if registry>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">First name: </label>
                <div class="col-sm-4">
                    <input type = "text" id = "firstName" name = "firstName" placeholder = "Your first name" class="form-control"/>
                    <div id="firstNameHelpBlock" class="form-text">
                        The first name must consist of 2-32 letters
                    </div>

                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Last name: </label>
                <div class="col-sm-4">
                    <input type = "text" id = "lastName" name = "lastName" placeholder = "Your last name" class="form-control"/>
                    <div id="lastNameHelpBlock" class="form-text">
                        The last name must consist of 2-32 letters
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Patronymic: </label>
                <div class="col-sm-4">
                    <input type = "text" id = "patronymic" name = "patronymic" placeholder = "Your patronymic" class="form-control"/>
                    <div id="patronymicHelpBlock" class="form-text">
                        The patronymic must consist of 2-32 letters
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email: </label>
                <div class="col-sm-4">
                    <input type = "email" id = "email" name = "email" placeholder = "Your email address" class="form-control"/>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-2">
                    <#nested>
                </div>
            </div>
            <div class="form-group row">
                <div class="col">
                    <@file type = "file" name = "profilePhoto" id = "profilePhoto" text = "Your profile photo" />
                </div>
                <div class="col">
                    <@file type = "file" name = "cardPhoto" id = "cardPhoto" text = "Your photo with a student card" />
                </div>
            </div>
            <@date.date />
        </#if>
        <#if edit>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email: </label>
                <div class="col-sm-4">
                    <input type = "email" id = "email" value = "${(user.email!' ')}" name = "email" placeholder = "Your email address" class="form-control"/>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-4">
                    <@file type = "file" name = "profilePhoto" id = "profilePhoto" text = "Change profile photo" />
                </div>
                <div class="col-sm-4">
                    <#if user.filename??>
                        <img src = "/img/${user.filename}" height = "100" weight = "150" class="img-thumbnail" />
                    </#if>
                </div>
            </div>
        </#if>
            <@security.token />
        <button type = "submit" class="btn btn-primary">${textButton}</button>
    </form>
</div>

</#macro>

<#macro logout>

    <form action = "/users/logout" method = "post">
        <input type = "submit" value = "Sign out" class="btn btn-dark"/>
        <@security.token />
    </form>

</#macro>

<#macro form_message>

<div>
    <form method = "post" enctype = "multipart/form-data">
        <input type = "text" name = "text" placeholder = "Your message" />
        <@file type = "file" name = "file" id = "file" text = "Add file" />
        <@security.token />
        <#nested>
    </form>
</div>

</#macro>

<#macro file type name id text = "" >

    <label for="${id}" class="form-label">${text}</label>
    <input class="form-control" type="${type}" name="${name}" id="${id}"/>
</#macro>

<#macro foreach collection=[] status="light">

    <#if collection??>
        <#list collection as row>
            <div class="alert alert-${status}" role="alert">
                <li>${row}</li>
            </div>
        </#list>
    </#if>

</#macro>

<#macro select enum=[] name="">

    <#if enum??>
        <select name="${name}" class="form-select">
            <#list enum as element>
                <option value="${element}">${element}</option>
            </#list>
        </select>
    </#if>

</#macro>

<#macro m_select enum=[] name="">

<#if enum??>
<select class="form-select" multiple true name="${name}">
    <#list enum as element>
    <option value="${element}">${element}</option>
</#list>
</select>
</#if>

</#macro>