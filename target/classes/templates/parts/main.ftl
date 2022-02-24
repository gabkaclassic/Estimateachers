<#import "users_logic.ftl" as ul>

<#macro page>
    <html lang = "en">

        <head>
            <style>img[alt="www.000webhost.com"] {display: none;}</style>
            <meta charset="UTF-8">
            <title>Esimateachers</title>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />

            <script defer src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
            <script defer src="https://cdnjs.cloudflare.com/ajax/libs/turbolinks/5.2.0/turbolinks.js" integrity="sha512-G3jAqT2eM4MMkLMyQR5YBhvN5/Da3IG6kqgYqU9zlIH4+2a+GuMdLb5Kpxy6ItMdCfgaKlo2XFhI0dHtMJjoRw==" crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
            <link rel="stylesheet" href="/static/bootstrap.css">

            <script src="https://www.google.com/recaptcha/api.js"></script>
            <script src="https://www.google.com/recaptcha/api.js?render=6LeUZlweAAAAAGZDIIptpjEm4PDIomXITRiMra4c"></script>
            <script>
                    grecaptcha.ready(function() {
                      grecaptcha.execute('6LeUZlweAAAAAGZDIIptpjEm4PDIomXITRiMra4c', {action: 'submit'}).then(function(token) {
                            document.getElementById('g-recaptcha-response').value=token;
                      });
                    });

          </script>
        </head>

        <body>
            <#include "navigation_bar.ftl">

            <div class="container mt-10">
                <div class="row">

                </div>
                <div class="row">
                    <div class="col">
                        <#nested>
                    </div>
                </div>
            </div>
        </body>

    </html>
</#macro>

