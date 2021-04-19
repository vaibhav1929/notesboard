/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ajava.notesboard.controllers;

import com.ajava.notesboard.models.CheckList;
import com.ajava.notesboard.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 91834
 */
public class checklistController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    DBManager dBManager;
    @Override
    public void init()
            throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        this.dBManager = new DBManager();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(request.getParameter("action")!=null){
                User user = (User)request.getSession().getAttribute("user");
                switch(request.getParameter("action")){
                    case "save_task":
                        CheckList chklist = new CheckList();
                        chklist.setTitle(request.getParameter("title"));
                        chklist.setUid(user.getUid());
                        int id = dBManager.addNewChkList(chklist);
                        chklist.setChklistid(id);
                        
                        Gson gson = new Gson();
                        System.out.println(gson.toJson(chklist));
                        out.println(gson.toJson(chklist));
                        
                        break;
                    case "state_task":
                        CheckList chkList = dBManager.getChkListByUidAndNid(user.getUid(), Integer.parseInt(request.getParameter("chklistid")));
                        chkList.setState(Boolean.parseBoolean(request.getParameter("state")));
                        boolean isEdit = dBManager.updateChkList(chkList);
                        if(isEdit){
                          gson = new Gson();
                          JsonElement jelem = gson.fromJson("{'result':'success'}", JsonElement.class);
                          out.println(gson.toJson(jelem));
                        } 
                        else{
                            gson = new Gson();
                            JsonElement jelem = gson.fromJson("{'result':'error'}", JsonElement.class);
                            out.println(gson.toJson(jelem));
                        }
                        break;
                    case "remove_task":
                        boolean isDel = dBManager.deleteChkList(Integer.parseInt(request.getParameter("chklistid")));
                        if(isDel){
                          gson = new Gson();
                          JsonElement jelem = gson.fromJson("{'result':'success'}", JsonElement.class);
                          out.println(gson.toJson(jelem));
                        } 
                        else{
                            gson = new Gson();
                            JsonElement jelem = gson.fromJson("{'result':'error'}", JsonElement.class);
                            out.println(gson.toJson(jelem));
                        }
                        break;
                }
            }
            else{
                Gson gson = new Gson();
                JsonElement jelem = gson.fromJson("{'error':'invalid arguments!'}", JsonElement.class);
                out.println(gson.toJson(jelem));
                out.flush();
           }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
