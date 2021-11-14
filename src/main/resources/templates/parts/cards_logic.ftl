<#import "users_logic.ftl" as ul>

<#macro images photos=[]>

    <#if photos??>
        <#list photos as photo>
            <#if photo??>
                <p><img src = "/img/${photo}" /></p>
            <#else>
                None
            </#if> <br>
        </#list>
    </#if>


</#macro>