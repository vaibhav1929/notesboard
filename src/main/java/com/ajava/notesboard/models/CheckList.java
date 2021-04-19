/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ajava.notesboard.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DEEP MENPARA
 */
public class CheckList {
    
    int chklistid;
    String title;
    int uid;
    boolean state;
    
    
    public CheckList()
    {}
    public CheckList(int chklistid,String title,int uid,boolean state)
    {
        this.chklistid = chklistid;
        this.title = title;
        this.uid = uid;
        
        this.state = state;
        

    }
    
    public void setAllData(ResultSet set){
        try {

            this.chklistid = set.getInt("chklistid");
            this.title = set.getString("title");
            this.uid = set.getInt("uid");
            this.state = set.getBoolean("state");
            
          
            
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public int getChklistid() {
        return chklistid;
    }

    public void setChklistid(int chklistid) {
        this.chklistid = chklistid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    
}
