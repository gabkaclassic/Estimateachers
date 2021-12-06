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
                <li>
                    <div class="collapse navbar-collapse" id="navbarNavDarkDropdownCards">
                        <ul class="navbar-nav">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="/cards/universities" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="true">
                                    Cards
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <li><a class="dropdown-item" href = "/cards/list/universities">Universities</a></li>
                                    <li> <a class="dropdown-item" href = "/cards/list/dormitories">Dormitories</a></li>
                                    <li> <a class="dropdown-item" href = "/cards/list/faculties">Faculties</a></li>
                                    <li><a class="dropdown-item" href = "/cards/list/teachers">Teachers</a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <#if known><li><a class="dropdown-item" href = "/cards/add">Add new card</a></li></#if>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </li>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/admin/allUsers">All users</a>
                    </li>
                <li>
                    <div class="collapse navbar-collapse" id="navbarNavDarkDropdownApplications">
                        <ul class="navbar-nav">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="/admin/applications/users" id="navbarDropdown2" role="button" data-bs-toggle="dropdown" aria-expanded="true">
                                    Applications
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <li><a class="dropdown-item" href = "/admin/applications/users">New users</a></li>
                                    <li> <a class="dropdown-item" href = "/admin/applications/cards">New cards</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
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
            <#if user??>
                <a class="nav-link active" aria-current="page" href="/users/edit/${user.id}">${username}</a>
            <#else>
                ${username}
            </#if>
        </div>
    </div>
    </div>
</nav>
