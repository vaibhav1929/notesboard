<%-- 
    Document   : dashboard
    Created on : 11 Apr, 2021, 12:52:36 PM
    Author     : 91834
--%>

<%@page import="com.ajava.notesboard.models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="assets/css/style.css"/>
        
        
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700">
        <link rel="stylesheet" href="assets/css/nucleo.css" type="text/css">
        <link rel="stylesheet" href="assets/css/fontaw-all.min.css" type="text/css">
        <link rel="stylesheet" href="assets/css/argon.css?v=1.2.0" type="text/css">
    </head>
    <body>
        
        <nav class="sidenav navbar navbar-vertical  fixed-left  navbar-expand-xs navbar-light bg-white" id="sidenav-main">
    <div class="scrollbar-inner">
      <div class="navbar-inner">
        
        <div class="collapse navbar-collapse" id="sidenav-collapse-main">
          <ul class="navbar-nav">
            <li class="nav-item">
              <a class="nav-link active" href="#notes">
                <i class="ni ni-single-copy-04 text-orange"></i>
                <span class="nav-link-text">Notes</span>
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#checklist">
                <i class="ni ni-check-bold text-primary"></i>
                <span class="nav-link-text">Checklist</span>
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#vault">
                <i class="ni ni-settings-gear-65 text-danger"></i>
                <span class="nav-link-text">Vault</span>
              </a>
            </li>
            
            <li class="nav-item">
              <a class="nav-link" href="#bin">
                <i class="ni ni-basket text-yellow"></i>
                <span class="nav-link-text">Recycle bin</span>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </nav>
  <!-- Main content -->
  <div class="main-content" id="panel">
    
    <nav class="navbar navbar-top navbar-expand navbar-dark bg-primary border-bottom">
      <div class="container-fluid">
          <h3 class="text-white">Notes</h3>
        
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav align-items-center  ml-md-auto ">
            <li class="nav-item d-xl-none">
              <!-- Sidenav toggler -->
              <div class="pr-3 sidenav-toggler sidenav-toggler-dark" data-action="sidenav-pin" data-target="#sidenav-main">
                <div class="sidenav-toggler-inner">
                  <i class="sidenav-toggler-line"></i>
                  <i class="sidenav-toggler-line"></i>
                  <i class="sidenav-toggler-line"></i>
                </div>
              </div>
            </li>
            
          </ul>
          <ul class="navbar-nav align-items-center  ml-auto ml-md-0 ">
            <li class="nav-item dropdown">
              <a class="nav-link pr-0" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <div class="media align-items-center">
                  <span class="avatar avatar-sm rounded-circle">
                    <img alt="Image placeholder" src="assets/img/profile.png">
                  </span>
                  <div class="media-body  ml-2  d-none d-lg-block">
                    <span class="mb-0 text-sm  font-weight-bold"><%= ((User)session.getAttribute("user")).getName()%></span>
                  </div>
                </div>
              </a>
              <div class="dropdown-menu  dropdown-menu-right ">
                <div class="dropdown-header noti-title">
                  <h6 class="text-overflow m-0">Welcome!</h6>
                </div>
                <a href="#!" class="dropdown-item">
                  <i class="ni ni-single-02"></i>
                  <span>My profile</span>
                </a>
                <div class="dropdown-divider"></div>
                <a href="#!" class="dropdown-item">
                  <i class="ni ni-user-run"></i>
                  <span>Logout</span>
                </a>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  <div class="header bg-primary pb-6">
      <div class="container">
        <div class="header-body">
          <div class="row">
            <div class="col-xl-6 col-md-6">
              <div class="card card-stats">
                <!-- Card body -->
                <div class="card-header">
                    <div class="icon icon-shape ni ni-single-copy-04 bg-gradient-orange text-white rounded-circle shadow">
                        <i class="ni ni-"></i>
                      </div>
                    <span class="h2 font-weight-bold mb-0">Create new Note</span>
                </div>
                <div class="card-body">
                  <div class="row">
                    <div class="col">
                      <form class="col-md-12" method="post" action="saveNote">
                          <div class="row">
                            <div class="col-lg-12">
                              <div class="form-group">
                                <label class="form-control-label" for="note_title">Title</label>
                                <input type="text" id="note_title" class="form-control" placeholder="give head to your note">
                              </div>
                            </div>
                            <div class="col-lg-12">
                              <div class="form-group">
                                <label class="form-contro-label" for="note_content">Content</label>
                                <textarea rows="4" class="form-control" name="note_content" id="note_content" placeholder="A few words for your note"></textarea>
                              </div>
                            </div>
                              <button class="btn btn-info ml-3">Save note</button>
                          </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
              
           <div class="col-xl-6 col-md-6 mt-6">
              <div class="card card-stats">
                <!-- Card body -->
                <div class="card-header">
                    <div class="icon icon-shape ni ni-collection bg-gradient-primary text-white rounded-circle shadow">
                        <i class="ni ni-"></i>
                      </div>
                    <span class="h2 font-weight-bold mb-0">Create new Group</span>
                </div>
                <div class="card-body">
                  <div class="row">
                    <div class="col">
                      <form class="col-md-12" method="post" action="saveGroup">
                          <div class="row">
                            <div class="col-lg-12">
                              <div class="form-group">
                                <label class="form-control-label" for="group_title">Title</label>
                                <input type="text" id="group_title" name="group_title" class="form-control" placeholder="give head to your group">
                              </div>
                            </div>
                              <button class="btn btn-info ml-3">Save group</button>
                          </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
              
          </div>
        </div>
      </div>
    </div>
                  <div class="header mt--4">
                      <div class="continer">
                      <jsp:include page="notes_view.jsp"/>
                      </div>
                  </div>
                  
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/js/popper.min.js"> </script>
        <script src="assets/js/bootstrap.min.js"> </script>
        <script src="assets/js/jquery.scrollbar.min.js"></script>
        <script src="assets/js/jquery-scrollLock.min.js"></script>
        <script src="assets/js/js.cookie.js"></script>
        <script src="assets/js/argon.js?v=1.2.0"></script>
    </body>
</html>
