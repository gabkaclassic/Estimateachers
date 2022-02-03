<#import "parts/main.ftl" as main>
<#import "parts/security.ftl" as security>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/cards_logic.ftl" as cl>
<#import "parts/discussion_logic.ftl" as dl>

<@main.page>
    <div class="row">
        <div class="col-3 w-80 ml-12 mt-5 sticky-top">
            <h2><span>${discussion.title}</span></h2>
        </div>
        <div class="col-3 w-80 ml-12 mt-5 sticky-top">
            <div class="col">
                <form class="d-flex" method="post" action="/discussion/comment/search/text">
                    <input class="form-control me-2" value="${text!''}" name="text" type="search" placeholder="Comment's text" aria-label="Search">
                    <input type="hidden" name="discussionId" value="${discussion.id}" />
                    <button class="btn btn-outline-success" type="submit">Search</button>
                    <@security.token />
                </form>
            </div>
            <div class="col">
                <form class="d-flex" method="post" action="/discussion/comment/search/author">
                    <input class="form-control me-2" value="${author!''}" name="author" type="search" placeholder="Comment's author nick" aria-label="Search">
                    <input type="hidden" name="discussionId" value="${discussion.id}" />
                    <button class="btn btn-outline-primary" type="submit">Search</button>
                    <@security.token />
                </form>
            </div>
        </div>
    </div>
    <div class="row mt-3 w-80">
        <#if comments??>
            <#if comments?has_content>
                <#list comments as comment>
                    <div class="row mt-2 fluid">
                        <@dl.comment_view comment=comment />
                    </div>
                </#list>
            </#if>
        </#if>
    </div>
</@main.page>