/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ajava.notesboard.controllers;

import com.ajava.notesboard.models.Notes;
import com.ajava.notesboard.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 91834
 */
public class VaultController extends HttpServlet {

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
           if(request.getParameter("action") != null){
               User user = (User) request.getSession().getAttribute("user");
               switch(request.getParameter("action")){
                   case "save_password":
                       
                       user.setVaultPassword(request.getParameter("password"));
                       dBManager.updateUser(user);
                       break;
                   case "auth_password":
                      String pass = user.getVaultPassword();
                      if(pass.equals(request.getParameter("password"))){
                          Gson gson = new Gson();
                          JsonElement jelem = gson.fromJson("{'result':'success'}", JsonElement.class);
                          out.println(gson.toJson(jelem));
                      } 
                      else{
                          Gson gson = new Gson();
                          JsonElement jelem = gson.fromJson("{'result':'error'}", JsonElement.class);
                          out.println(gson.toJson(jelem));
                      }
                      break;
                   case "show_hnotes":
                       ArrayList<Notes> notes = dBManager.getHiddenNotesByUid(user.getUid());
                       JsonObject jsonObj = new JsonObject();
                       JsonArray jsonArray = new Gson().toJsonTree(notes).getAsJsonArray();
                       jsonObj.add("notes",jsonArray);
                       System.out.println(jsonObj.toString());
                       out.println(jsonObj.toString());
                       break;
                       
                   case "visible_note":
                       Notes hidNote = dBManager.getNotesByUidAndNid(user.getUid(), Integer.parseInt(request.getParameter("nid")));
                       hidNote.setHidden(false);
                       System.err.println(hidNote);
                       boolean isEdit = dBManager.updateNote(hidNote);
                       if(isEdit){
                          Gson gson = new Gson();
                          JsonElement jelem = gson.fromJson("{'result':'success'}", JsonElement.class);
                          out.println(gson.toJson(jelem));
                      } 
                      else{
                          Gson gson = new Gson();
                          JsonElement jelem = gson.fromJson("{'result':'error'}", JsonElement.class);
                          out.println(gson.toJson(jelem));
                      }
                       break;
                   
                   case "h_note":
                       Notes shNote = dBManager.getNotesByUidAndNid(user.getUid(), Integer.parseInt(request.getParameter("nid")));
                       shNote.setHidden(true);
                       boolean isEdit1 = dBManager.updateNote(shNote);
                       if(isEdit1){
                          Gson gson = new Gson();
                          JsonElement jelem = gson.fromJson("{'result':'success'}", JsonElement.class);
                          out.println(gson.toJson(jelem));
                      } 
                      else{
                          Gson gson = new Gson();
                          JsonElement jelem = gson.fromJson("{'result':'error'}", JsonElement.class);
                          out.println(gson.toJson(jelem));
                      }
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
