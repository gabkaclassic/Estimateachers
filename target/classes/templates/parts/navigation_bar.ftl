<#include "auth.ftl">

<nav class="navbar navbar-expand-lg navbar-dark sticky-top bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand mb-4" href="/">Estimateachers</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon">
                 <a class="navbar-brand" href="#">
                  <img src="" alt="" width="30" height="24">
                </a>
            </span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/">Home</a>
                </li>
                <li>
                    <div class="collapse navbar-collapse" id="navbarNavDarkDropdown">
                        <ul class="navbar-nav">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    Cards
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <li><a class="dropdown-item" href = "/cards/list/ALL">All cards</a></li>
                                    <li><a class="dropdown-item" href = "/cards/list/UNIVERSITY">Universities</a></li>
                                    <li> <a class="dropdown-item" href = "/cards/list/DORMITORY">Dormitories</a></li>
                                    <li> <a class="dropdown-item" href = "/cards/list/FACULTY">Faculties</a></li>
                                    <li><a class="dropdown-item" href = "/cards/list/TEACHER">Teachers</a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <#if known>
                                        <li><a class="dropdown-item" href = "/cards/add">Add new card</a></li>
                                        <li><a class="dropdown-item" href = "/cards/collection">My cards</a></li>
                                    </#if>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </li>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/admin/add">Add admin</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active ml-4" aria-current="page" href="/admin/allUsers">All users</a>
                    </li>
                <li>
                    <div class="collapse navbar-collapse" id="navbarNavDarkDropdownApplications">
                        <ul class="navbar-nav">
                            <li class="nav-item dropdown">
                                <div class="collapse navbar-collapse" id="navbarNavDarkDropdown2">
                                    <ul class="navbar-nav">
                                        <li class="nav-item dropdown">
                                            <a class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink2" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                Applications
                                            </a>
                                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown2">
                                                <li><a class="dropdown-item" href = "/applications/users">New users</a></li>
                                                <li> <a class="dropdown-item" href = "/applications/cards">New cards</a></li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                </li>
                </#if>
                <#if known>
                    <li class="nav-item">
                        <a class="nav-link active mr-5" aria-current="page" href="/users/edit/${user.id}">Edit profile</a>
                    </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle ml-2" href="/admin/applications/users" id="navbarDropdown3" role="button" data-bs-toggle="dropdown" aria-expanded="true">
                                Requests
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown3">
                                <#if isAdmin>
                                    <li><a class="dropdown-item" href = "/applications/requests/cards">Changing cards</a></li>
                                    <li><a class="dropdown-item" href = "/applications/requests/service">Operation of the service</a></li>
                                <#else>
                                    <li><a class="dropdown-item" href = "/applications/requests/">New requests</a></li>
                                </#if>
                            </ul>
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
        <style>
        .right {
            position: absolute;
            right: 0;
        }
        </style>
            <div class="navbar-text right">
                <#if user??>
                    <a class="nav-link active" aria-current="page" href="/users/edit/${user.id}"><b>${username}</b></a>
              <#else>
                    ${username}
                </#if>
            </div>
        </div>
    </div>
</nav>
