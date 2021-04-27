<%-- 
    Document   : vault_view
    Created on : 17 Apr, 2021, 5:22:26 PM
    Author     : 91834
--%>
<%@page import="com.ajava.notesboard.models.NoteGroup"%>
<%@page import="com.ajava.notesboard.models.Notes"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ajava.notesboard.models.User"%>
<%@page import="com.ajava.notesboard.controllers.DBManager"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="assets/css/style.css"/>
        
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/js/sweet_alert.js"></script>
        <script src="assets/js/ajax_controller.js"></script>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700">
        <link rel="stylesheet" href="assets/css/nucleo.css" type="text/css">
        <link rel="stylesheet" href="assets/css/fontaw-all.min.css" type="text/css">
        <link rel="stylesheet" href="assets/css/argon.css?v=1.2.0" type="text/css">
    </head>
<body>
    <script src="assets/js/sweet_alert.js"></script>
    <%! DBManager dBmanager = new DBManager();
        boolean passSetup = false;%>
    <%
        String password = ((User)request.getSession().getAttribute("user")).getVaultPassword();
        if(password == null){ %>
        <script type="text/javascript">
    async function setup(){
        const { value: password } = await Swal.fire({
            title: 'Setup your vault password',
            input: 'password',
            inputLabel: 'Password',
            inputPlaceholder: 'some strong password',
            inputAttributes: {
              maxlength: 10,
              autocapitalize: 'off',
              autocorrect: 'off'
            }
          });

        if(password) { 
            saveVaultPassword(password);
         }
    }        
    setup();
        </script>
        <% } 
        
else{%>
<script type="text/javascript">
    async function auth(){
        const { value: password } = await Swal.fire({
            title: 'enter your vault password',
            input: 'password',
            inputLabel: 'Password',
            inputPlaceholder: 'some strong password',
            inputAttributes: {
              maxlength: 10,
              autocapitalize: 'off',
              autocorrect: 'off'
            }
          });

        if(password) { 
            data = {action:"auth_password",password:password};
            console.log(data);
                $.ajax({
                url:"VaultController" ,
                data:data,
                method:"post",
                success:function(res){
                    if(res.result == "error"){
                        Swal.fire({text:'Wrong password!', icon:'error'});
                        auth();
                    }
                    else{
                        showHNotes();
                    } 
                },
                error:function(){Swal.fire({text:'Error occured!', icon:'error'});}

            });
         }
    }        
    auth();
        </script>
        
        <% } %>
        <div class="container ml-auto mr-auto mt-3">
            <div class="row justify-content-center">
                <div class="col-md-12">
                    
                    <div id="HidNoteContainer" class="row bg-dark rounded shadow">
                        <h2 class="text-white m-2 col-md-12">Hidden Notes</h2>
                    </div>
                </div>
                <div class="col-md-12">
                    <h2 class="m-2">Visible Notes</h2>
                        <div id="NormalNoteContainer" class="row"></div>
                </div>
            
            </div>
        </div>
</body>
</html>