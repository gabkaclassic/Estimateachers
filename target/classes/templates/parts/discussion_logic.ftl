<#import "main.ftl" as main>
<#import "security.ftl" as security>
<#import "date.ftl" as date>

<#macro comment_view comment="NULL">

    <#if comment??>

        <div class="w-75 h-10 mt-2 card" style="width: 18rem height: 15rem;">
            <div class="card-body">
                <div class="card-title">
                    <#if comment.author.isAdmin()><b>${comment.author.username} &#10026;</b><#else><b>${comment.author.username}</b></#if>
                    <i>${comment.getDate()}</i>
                </div>

                <p class="card-text">${comment.text}</p>
                <div class="row">
                    <div class="col">
                        <#if comment.rating lt 0><font color="red"><b>Rating: ${comment.rating}</b></font><#elseif comment.rating gt 0><font color="green"><b>Rating: ${comment.rating}</b></font><#else><font color="gray"><b>Rating: ${comment.rating}</b></font></#if>
                    </div>
                    <div class="col">
                        <form method = "post" action = "/discussions/comment/like">
                            <input type="hidden" name = "commentId" value = ${comment.id} />
                            <input type="hidden" name = "discussionId" value="${discussion.id}" />
                            <@security.token />
                            <button class="btn btn-success" type="submit"> &#10084; Like</button>
                        </form>
                    </div>
                    <div class="col">
                        <form method = "post" action = "/discussions/comment/dislike">
                            <input type="hidden" name = "commentId" value = ${comment.id} />
                            <input type="hidden" name = "discussionId" value="${discussion.id}" />
                            <@security.token />
                            <button class="btn btn-danger" type="submit"> &#10008; Dislike</button>
                        </form>
                    </div>
                </div>
                <#if isAdmin??>
                    <#if isAdmin>
                        <form method = "post" action = "/discussions/comment/delete">
                            <input type="hidden" name = "commentId" value = ${comment.id} />
                            <input type="hidden" name = "discussionId" value="${discussion.id}" />
                            <@security.token />
                            <button class="btn btn-danger" type="submit"> &#10006; Delete</button>
                        </form>
                    </#if>
                </#if>
            </div>
        </div>
    </#if>

</#macro>

<#macro comment_send>

    <form method = "post" action = "/discussions/comment/send">
        <label for="text">Your comment text</label>
        <textarea class="form-control" id="text" name="text" rows="5"></textarea>
        <input type="hidden" name = "discussionId" value="${discussion.id}" />
        <@date.date />
        <@security.token />
        <button class="btn btn-primary" type="submit"> &#128386; Send</button>
    </form>

</#macro>