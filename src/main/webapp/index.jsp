<%-- 
    Document   : index
    Created on : 21 Mar, 2021, 6:24:58 PM
    Author     : 91834
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
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
        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-md-6 col-sm-6 col-xs-6 mt-3  order-md-2 ">
                    <div id='default_container'> <img src="assets/img/header-background.gif" width="95%"/> </div>
                    <div id='signup_container' style="width:95%"><%@include file="signup_banner.jsp" %></div>
                    <div id='login_container' style="width:95%"><%@include file="login_banner.jsp" %></div>
                </div>
                <div class="col-md-6 col-sm-6 col-xs-6">
                        
                    <div class="signup-card mt-1">
                        <form action="AccountHandler" method="post">
                                <div style="position:absolute;width:100%;height:100%; background:linear-gradient(-120deg,#7E57C2,black);opacity:0.8;backdrop-filter:blur(200px)"></div>
                                <div class="col-md-8 p-2 underline-bacon col-sm-12 col-xs-12 text-center"><p class=''><span  style="font-size: 30px">Create an account for <span style="text-shadow:2px 1px 5px blue,-2px 1px 5px green;">NotesKeeper</span></div>
                                    
                                <div class="row p-4">
                                    <div class="form-group col-md-6 col-sm-12 col-xs-12">
                                        <label for="signup_name">Your good name</label>
                                        <input type="text" class="form-control" required name="signup_name" id="signup_name">
                                    </div>
                                    
                                    <div class="form-group col-md-6 col-sm-12 col-xs-12">
                                        <label for="signup_email">give your email</label>
                                        <input type="email" class="form-control" required name="signup_email" id="signup_email">
                                    </div>
                                    
                                    <div class="form-group col-md-6 col-sm-12 col-xs-12">
                                        <label for="signup_password">Type your tricky password</label>
                                        <input type="password" class="form-control" required name="signup_password" id="signup_password">
                                    </div>
                                    <div class="form-group col-md-6 col-sm-12 col-xs-12">
                                        <label for="signup_repassword">Type same password again</label>
                                        <input type="password" class="form-control" required name="signup_repassword" id="signup_repassword">
                                    </div> 
                                    <div class="form-group col-md-12 col-sm-12 col-xs-12 text-center">
                                        <button class="btn btn-lg btn-success" required name="signup_submit" value="signup" id="signup_submit">SIGNUP </button>
                                    </div>
                                </div>
                        </form>
                        <form method="post" action="testServlet">
                                <div class="col-md-8 underline-bacon col-sm-12 col-xs-12 text-center"><p class=''>already have an account?<br><span  style="font-size: 30px">Login</span> </p></div>
                                <div class="row p-2 justify-content-center">
                                   
                                    <div class="form-group col-md-8 col-sm-12 col-xs-12">
                                        <label for="login_email">Email</label>
                                        <input type="email" class="form-control" required name="login_email" id="login_email">
                                    </div>
                                    <div class="form-group col-md-8 col-sm-12 col-xs-12">
                                        <label for="login_password">Password</label>
                                        <input type="password" class="form-control" required name="login_password" id="login_password">
                                    </div>
                                    <div class="form-group col-md-12 col-sm-12 text-center col-xs-12">
                                        <button onclick="login()"  class="btn btn-lg btn-danger" required name="login_submit" id="login_submit">LOGIN </button>
                                    </div>
                                </div>
                        </form>
                </div>
                
                
            </div>
        </div>
        
       
        
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/js/popper.min.js"> </script>
        <script src="assets/js/bootstrap.min.js"> </script>
        
        <script type='text/javascript'>
            var signupToggle = false;
            
            $(document).ready(function(){
                $('#signup_container').hide();
                $('#login_container').hide();
            }
            );
            
            $("#signup_name,#signup_email,#signup_password,#signup_repassword").focus(()=>{
                $("#default_container,#login_container").fadeOut( "slow", function() {
                    $("#signup_container").show();
                }); 
            });
            
           $("#login_email,#login_password").focus(()=>{
                $("#default_container,#signup_container").fadeOut( "slow", function() {
                    $("#login_container").show();
                }); 
            });
            $("#signup_name").focus(()=>{
                $("#message_ui_container").hide().fadeIn();
                  $('#message_container').html("Let's start with your good name");   
            });
            
            $("#signup_email").focus(()=>{
                $("#message_ui_container").hide().fadeIn();
                  $('#message_container').html( "Give your email address");   
            });
            $("#signup_password").focus(()=>{
                
                  $("#message_ui_container").hide().fadeIn();
                  $('#message_container').html("Make sure you type a strong password");   
            });
            
            $("#signup_repassword").focus(()=>{
                $("#message_ui_container").hide().fadeIn();
                  $('#message_container').html("Can you confirm your password again");   
            });
           
           $("#login_email").focus(()=>{
               console.log(document.getElementById("login_message_container"));
                $("#login_message_ui_container").hide().fadeIn();
                  $('#login_message_container').html( "Ok, Let's enter registered email address");   
            });
            $("#login_password").focus(()=>{
                       $("#login_message_ui_container").hide().fadeIn();
                  $('#login_message_container').html("Ooo password, you remembered right?");   
            });
        </script>
      
    </body>
</html>
