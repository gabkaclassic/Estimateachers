<#macro token>

    <input type="hidden" name="_csrf" value="${_csrf.token}" />

</#macro>

<#macro captcha>

<input  id="g-recaptcha-response"
        name="g-recaptcha-response"
        class="g-recaptcha"
        type="hidden"
        value="6LeUZlweAAAAAGZDIIptpjEm4PDIomXITRiMra4c"
        data-sitekey="6LeUZlweAAAAAGZDIIptpjEm4PDIomXITRiMra4c"
        data-callback='onSubmit'
        data-action='submit' />

</#macro>