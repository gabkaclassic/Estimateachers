<#import "parts/main.ftl" as main>
<#import "parts/security.ftl" as security>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/cards_logic.ftl" as cl>
<#import "parts/discussion_logic.ftl" as dl>

<@main.page>
    <div class="row">
        <div class="col ml-12 mt-5 sticky-top">

            <h2><span>${discussion.title}</span></h2>
        </div>
        <div class="col-3 w-80 ml-12 mt-5 sticky-top" style="position: fixed; top: 150px; right: 20px;">
            <form class="d-flex w-30" method="post" action="/discussions/comment/search/text">
                <input class="form-control me-2" value="${text!''}" name="text" type="search" placeholder="Comment's text" aria-label="Search">
                <input type="hidden" name="discussionId" value="${discussion.id}" />
                <button class="btn btn-outline-success" type="submit">Search</button>
                <@security.token />
            </form>
            <form class="d-flex w-30" method="post" action="/discussions/comment/search/author">
                <input class="form-control me-2" value="${username!''}" name="username" type="search" placeholder="Comment's author nick" aria-label="Search">
                <input type="hidden" name="discussionId" value="${discussion.id}" />
                <button class="btn btn-outline-primary" type="submit">Search</button>
                <@security.token />
            </form>
        </div>
        <div class="col-3 w-80 ml-12 mt-5 sticky-top" style="position: fixed; top: 150px; left: 20px;">
            <form class="d-flex w-30" method="get" action="/discussions/sorted/asc/${discussion.id}">
                <button class="btn btn-outline-danger" type="submit"> &#11015; Order by rating</button>
                <@security.token />
            </form>
            <form class="d-flex w-30" method="get" action="/discussions/sorted/desc/${discussion.id}">
                <button class="btn btn-outline-success" type="submit"> &#11014; Order by rating</button>
                <@security.token />
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col mt-3 ml-5 w-80 fixed-right">
            <#if comments??>
                <#if comments?has_content>
                    <#list comments as comment>
                        <div class="row mt-2">
                            <@dl.comment_view comment=comment />
                        </div>
                    </#list>
                </#if>
            </#if>
        </div>
        <div class="col-3 w-80" style="position: fixed; bottom: 10px; right: 20px;">
            <@dl.comment_send />
        </div>
    </div>
</@main.page>