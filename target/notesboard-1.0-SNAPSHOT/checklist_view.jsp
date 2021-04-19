<%-- 
    Document   : checklist_view
    Created on : 19 Apr, 2021, 3:24:11 PM
    Author     : 91834
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ajava.notesboard.models.User"%>
<%@page import="com.ajava.notesboard.controllers.DBManager"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="header bg-primary pb-6">
    <div class="container">
        <div class="header-body">
            <div class="row">
               <div class="col-xl-6 col-md-6 mt-6">
                    <div class="card card-stats" id="groupFormContainer">
                      <!-- Card body -->
                      <div class="card-header">
                          <div class="icon icon-shape ni ni-collection bg-gradient-primary text-white rounded-circle shadow">

                            </div>
                          <span class="h2 font-weight-bold mb-0">Add new task</span>
                      </div>
                      <div class="card-body">
                        <div class="row">
                          <div class="col">
                                <div class="row justify-content-center">
                                  <div class="col-lg-12">
                                    <div class="form-group">
                                      <label class="form-control-label" for="task_title">Title</label>
                                      <input type="text" id="task_title" name="group_title" class="form-control" placeholder="Add your task to check-list">
                                    </div>
                                  </div>
                                    <button id="group_save_btn" data="save" class="btn btn-info ml-3"onclick="addToList()">Add Task</button>
                                </div>

                          </div>
                        </div>
                      </div>
                    </div>
                </div>  
            </div>
        </div>
    </div>    
    
</div>