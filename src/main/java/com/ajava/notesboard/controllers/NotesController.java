/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ajava.notesboard.controllers;

import com.ajava.notesboard.models.Notes;
import com.ajava.notesboard.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
public class NotesController extends HttpServlet {

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if(request.getParameter("action") != null){
                
                if(dBManager == null){
                    Gson gson = new Gson();
                    JsonElement jelem = gson.fromJson("{'error':'invalid arguments!'}", JsonElement.class);
                    out.println(gson.toJson(jelem));
                    return;
                }
                String action = request.getParameter("action");
                
                switch(action){
                    case "save_note":
                        Notes note = new Notes();
                        note.setTitle(request.getParameter("title"));
                        note.setContent(request.getParameter("content"));
                        note.setGroupid(Integer.parseInt(request.getParameter("groupid")));
                        note.setUid(((User)request.getSession().getAttribute("user")).getUid());
                        note.setColorcode("#FFFFFF");
                        note.setType("NOTE");                       
                        int id = dBManager.addNewNote(note);
                        if(id != -1){
                            Gson gson = new Gson();
                            note.setNid(id);
                            System.out.println(gson.toJson(note));
                            out.println(gson.toJson(note));
                        }
                        else{
                            Gson gson = new Gson();
                            JsonElement jelem = gson.fromJson("{'error':'THIS Error occured!'}", JsonElement.class);
                            out.println(gson.toJson(jelem));    
                        }
                        
                        break;
 //---------------------------------------------------------------------------------------------------------------
                        
                    case "delete_note":
                        boolean deleted = dBManager.deleteNote(Integer.parseInt(request.getParameter("nid")));
                        if(deleted){
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
                        
                    case "edit_note":
                        Notes oldNote = new Notes();
                        oldNote.setNid(Integer.parseInt(request.getParameter("nid")));
                        oldNote.setTitle(request.getParameter("title"));
                        oldNote.setContent(request.getParameter("content"));
                        oldNote.setGroupid(Integer.parseInt(request.getParameter("groupid")));
                        oldNote.setUid(((User)request.getSession().getAttribute("user")).getUid());
                        oldNote.setColorcode("#FFFFFF");
                        oldNote.setType("NOTE"); 
                         System.out.println(oldNote);
                        boolean edited = dBManager.updateNote(oldNote);
                        if(edited){
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
