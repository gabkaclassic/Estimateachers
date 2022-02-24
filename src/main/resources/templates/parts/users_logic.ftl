<#import "security.ftl" as security>
<#import "date.ftl" as date>

<#macro data_form registry=false edit=false textButton = "Submit">

<div class="row mt-2">
    <form id="form" method = "post" enctype = "multipart/form-data">
        <div class="form-group row">
            <div class="col-sm-6">
                <label for="username" class="col-form-label">Login: </label>
                <input type = "text" name = "username" id = "username" value="${username!''}" placeholder = "Your login" class="form-control" />
                <#if registry>
                    <div id="loginHelpBlock" class="form-text">
                        The login must consist of 2-32 letters
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-6">
                <label for="password" class="col-form-label">Password: </label>
                <input type = "password" name = "password" value="${password!''}" id = "password" placeholder = "Your password" class="form-control"/>
            </div>
            <#if registry || edit>
                <div id="passwordHelpBlock" class="form-text">
                    The password must contain 8-32 characters, of which 1 digit and 1 specified character.
                </div>
            </#if>
        </div>
        <#if registry>
            <div class="form-group row">
                <div class="col-sm-4">
                    <label for="firstName" class="col-form-label">First name: </label>
                    <input type = "text" id = "firstName" value="${firstname!''}" name = "firstName" placeholder = "Your first name" class="form-control"/>
                    <div id="firstNameHelpBlock" class="form-text">
                        The first name must consist of 2-32 letters
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-4">
                    <label for="lastName" class="col-form-label">Last name: </label>
                    <input type = "text" id = "lastName" value="${lastname!''}" name = "lastName" placeholder = "Your last name" class="form-control"/>
                    <div id="lastNameHelpBlock" class="form-text">
                        The last name must consist of 2-32 letters
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-4">
                    <label for="patronymic" class="col-form-label">Patronymic: </label>
                    <input type = "text" id = "patronymic" value="${patronymic!''}" name = "patronymic" placeholder = "Your patronymic" class="form-control"/>
                    <div id="patronymicHelpBlock" class="form-text">
                        The patronymic must consist of 2-32 letters
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-4">
                    <label for="email" class="col-form-label">Email: </label>
                    <input type = "email" id = "email" value="${email!''}" name="email" placeholder = "Your email address" class="form-control"/>
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
                <div class="col-sm-4">
                    <label for="email" class="col-form-label">Email: </label>
                    <input type = "email" id = "email" value = "${(user.email!' ')}" name = "email" placeholder = "Your email address" class="form-control"/>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-4">
                    <@file type = "file" name = "profilePhoto" id = "profilePhoto" text = "Change profile photo" />
                </div>
                <#if isAdmin>
                    <div class="form-group row mt-2">
                        <div class="row"><h5>User roles:</h5></div>
                        <div class="row mt-1">
                            <div class="col-1">
                                <div class="btn-group" role="group" aria-label="User roles control">
                                    <input type="checkbox" class="btn-check" name="userRole" id="user_role" autocomplete="on" <#if userRole>checked</#if>>
                                    <label class="btn btn-outline-secondary" for="user_role">User</label>
                                    <input type="checkbox" class="btn-check" name="adminRole" id="admin_role"  autocomplete="off" <#if adminRole>checked</#if>>
                                    <label class="btn btn-outline-secondary" for="admin_role">Admin</label>
                                    <input type="checkbox" class="btn-check" name="lockedRole" id="locked_role" autocomplete="off" <#if lockedRole>checked</#if>>
                                    <label class="btn btn-outline-secondary" for="locked_role">Locked</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </#if>
                <div class="col-sm-4">
                    <#if user.filename??>
                        <img src = "/img/${user.filename}" height = "100" weight = "150" class="img-thumbnail" />
                    </#if>
                </div>
            </div>
        </#if>
            <@security.token />

            <#if registry>
                <@security.captcha /> <br>
            </#if>
        <button type = "submit" class="btn btn-primary mt-3"> <#if edit> &#9998; <#elseif registry> &#9745; </#if> ${textButton}</button>
    </form>
    <#if delete??>
        <#if delete>
            <form method = "post" action = "/admin/delete">
                <input type="hidden" name = "userId" value = ${user.id} />
                <@security.token />
                <button class="btn btn-danger" type="submit"> &#10006; Delete</button>
            </form>
        </#if>
    </#if>
</div>

</#macro>

<#macro logout>

    <form  action = "/users/signout" method = "post">
        <input type = "submit" value = "Sign out" class="btn btn-dark mt-1">
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