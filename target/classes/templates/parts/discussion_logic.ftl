<#import "main.ftl" as main>
<#import "security.ftl" as security>
<#import "date.ftl" as date>

<#macro comment_view comment="NULL">

    <#if comment??>

        <div class="w-75 h-10 mt-2 card" style="width: 18rem height: 15rem;">
            <div class="card-body">
                <h5 class="card-title"><#if comment.author.isAdmin()?c><b>${comment.author.username} &#10026;</b><#else>${comment.author.username}</#if></h5>
                <p class="card-text">${comment.text}</p>
                <div class="row">
                    <div class="col">
                        Rating: <#if message.rating lt 0><font color="red">${message.rating}</font><#elseif message.rating gt 0><font color="green">${message.rating}</font><#else><font color="gray">${message.rating}</font></#if>
                    </div>
                    <div class="col">
                        <form method = "post" action = "/discussion/comment/like">
                            <input type="hidden" name = "id" value = ${message.id} />
                            <@security.token />
                            <button class="btn btn-success" type="submit"> &#10084; Like</button>
                        </form>
                    </div>
                    <div class="col">
                        <form method = "post" action = "/discussion/comment/dislike">
                            <input type="hidden" name = "id" value = ${message.id} />
                            <@security.token />
                            <button class="btn btn-danger" type="submit"> &#10008; Dislike</button>
                        </form>
                    </div>
                </div>
                <#if isAdmin>
                    <form method = "post" action = "/discussion/comment/delete">
                        <input type="hidden" name = "id" value = ${comment.id} />
                        <@security.token />
                        <button class="btn btn-danger" type="submit"> &#10006; Delete</button>
                    </form>
                </#if>
            </div>
        </div>
    </#if>

</#macro>

<#macro comment_send>

    <form method = "post" action = "/discussion/comment/send">
        <input type="text" name = "text" placeholder="Your comment text" />
        <@date.date />
        <@security.token />
        <button class="btn btn-primary" type="submit"> &#9993; Send</button>
    </form>

</#macro>