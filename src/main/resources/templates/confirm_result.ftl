<#import "parts/main.ftl" as main>

<@main.page>

    <div class="alert alert-${status}" role="alert">
        ${text}
    </div>

</@main.page>