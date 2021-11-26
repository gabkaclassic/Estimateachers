<#include "auth.ftl">

<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Estimateachers</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/">Home</a>
                </li>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/admin/allUsers">All users</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/admin/applications">Applications</a>
                    </li>
                </#if>
                <#if known>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/users/edit/${user.id}">Edit profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/chats/">Chats</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/cards/">Cards</a>
                    </li>
                    <li class="nav-item">
                        <@ul.logout />
                    </li>
                <#else>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/users/login">Sign in</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/users/registry">Sign up</a>
                    </li>
                </#if>
        </ul>
        <div class="navbar-text">
            ${username}
        </div>
    </div>
    </div>
</nav>
