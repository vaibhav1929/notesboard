<%-- 
    Document   : verify
    Created on : 11 Apr, 2021, 11:34:09 AM
    Author     : 91834
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="assets/css/style.css"/>
        <link rel="stylesheet" href="https://cdn.metroui.org.ua/v4/css/metro-all.min.css">
        
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <style>
            .anim-container{
                transform:translate(0px,-30px);
                animation: paper 2s;
            }
            .mail{
                animation: move 2s infinite;
            }
            @keyframes paper{
                0%{transform:translate(0px,25%)}
                100%{transform:translate(0px,-30px);}
            }
            @keyframes move {
                0%{margin-top: 50px}
                50%{margin-top: 60px}
                100%{margin-top: 50px}
            }
            @media only screen and (max-width: 992px) {
                .mail{visibility: hidden}
            }
        </style>
    </head>

    <body>
        <% 
            if(request.getAttribute("error") != null){
                out.println("<script>swal({text:'"+request.getAttribute("error").toString()+"', icon:'error'});</script>");
            }
            if(session.getAttribute("user")!=null){
                response.sendRedirect("dashboard.jsp");
            }
        %>

    <div class="container mt-5 p-4">

        <div class="row justify-content-center">

            <div class="col-md-12 row justify-content-center">
                <img src="assets/img/mail.svg" class="col-md-5 col-sm-6 col-xs-12 mail" style="position: absolute;transform:translate(0px,170px);z-index: 2"/>
                <img src="assets/img/mailCover.svg" class="col-md-5 col-sm-6 col-xs-6 mail" style="position: absolute;z-index: -1"/>

                <form  class="card anim-container col-md-4 text-center mb-5" action="AccountHandler" method="post" style="z-index: 1;padding-bottom:160px">

                    <div class="card-header card-header-success">VERIFICATION</div>
                    <p class="text-muted mt-1">You will need to verify your email to complete the registration</p>
                    <div class="row justify-content-center p-3">
                        <div class="input-group col-md-10 pb-1">
                            <div class="input-group-prepend">
                                <span class="input-group-text bg-info text-white" id="basic-addon1">@</span>
                            </div>
                            <input name="verify_email" id="verify_email" type="email" required class="form-control" placeholder="Email address" aria-label="Email">
                        </div>
                        <div class="input-group col-md-10 pb-1">
                            <div class="input-group-prepend">
                                <span class="input-group-text bg-info text-white" id="basic-addon1">*</span>
                            </div>
                            <input name="verify_otp" id="verify_otp" type="number" min="000000" max="999999" required class="form-control" placeholder="OTP" aria-label="otp"/>
                        </div>
                    </div>

                    <input type="submit" class="btn btn-success w-50" style="margin:auto;transform:translate(0px,-15px);z-index: 15" value="Verify" name="verify_submit"/>
                </form>
            </div>

        </div>

        </div>
    </body>
</html>
