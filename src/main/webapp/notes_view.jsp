<%-- 
    Document   : notes_view
    Created on : 12 Apr, 2021, 11:33:06 AM
    Author     : Vaibhav Sorathiya
--%>

<%@page import="com.ajava.notesboard.models.NoteGroup"%>
<%@page import="com.ajava.notesboard.models.Notes"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ajava.notesboard.models.User"%>
<%@page import="com.ajava.notesboard.controllers.DBManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <%! DBManager dBmanager = new DBManager(); %>
    <% 
       
       ArrayList<Notes> notes = dBmanager.getNotesByUid(((User)request.getSession().getAttribute("user")).getUid());
       ArrayList<NoteGroup> groups = dBmanager.getGroupsByUid(((User)request.getSession().getAttribute("user")).getUid());
       pageContext.setAttribute("notes", notes, PageContext.REQUEST_SCOPE);
       pageContext.setAttribute("groups", groups, PageContext.REQUEST_SCOPE);
    %>
    
    <script type="text/javascript" src="assets/js/ajax_controller.js"></script>
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
                          <div class="row">
                            <div class="col-lg-12">
                              <div class="form-group">
                                <label class="form-control-label" for="note_title">Title</label>
                                <input type="text" id="note_title" class="form-control" placeholder="give head to your note">
                              </div>
                            </div>
                            <div class="col-lg-12">
                              <div class="form-group">
                                <label class="form-control-label" for="note_content">Content</label>
                                <textarea rows="4" class="form-control" name="note_content" id="note_content" placeholder="A few words for your note"></textarea>
                              </div>
                            </div>
                              <div class="col-lg-12">
                              <div class="form-group">
                                <label class="form-control-label" for="note_groupid">Group</label>
                                <select class="form-control" name="note_groupid" id="note_groupid">
                                    <c:forEach items="${groups}" var="groupItem">
                                        <option value="${groupItem.getGroupid()}">${groupItem.getName()}</option>
                                    </c:forEach>
                                </select>
                               
                              </div>
                            </div>
                              <button class="btn btn-info ml-3" onclick="saveNote()"> Save note</button>
                          </div>
                          
                          
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
    <div class="container pl-5 pr-5 mt-4" id="NoteContainer">
        
        <c:set var="prevGroup" value="-1"/>
        
            
         <c:forEach items="${notes}" var="noteItem">
            
             <c:if test="${noteItem.getGroupid() != prevGroup}">
                
                 <c:if test="${prevGroup != -1}"></div></c:if>
                 <div class="row mt-4 mb-4 p-2 bg-default shadow rounded" id="${noteItem.getGroupid()}"> 
                     <div class="col-12"><h1><span class="badge badge-light">${noteItem.getGroup().getName()}</span></h1></div>
                 <c:set var="prevGroup" value="${noteItem.getGroupid()}"/>    
             </c:if>            
            <div class="col-md-4 col-sm-4 pb-2 pt-2 pl-2 pr-2 m-0">
                <div class="card m-0">
                    <div class="card-header p-2"><h1> <span class="badge badge-info"><c:out value="${noteItem.getTitle()}"/></span></h1></div>
                    <div class="card-body"><c:out value="${noteItem.getContent()}"/></div>   
                </div>
            </div>
        </c:forEach>
       
    </div>
    
    
</html>
