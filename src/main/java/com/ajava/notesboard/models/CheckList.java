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
    String items;
    String states;
    String colorcode;
    boolean isdeleted;
    String whendeleted;
    
    
    public CheckList()
    {}
    public CheckList(int chklistid,String title,int uid,String items,String states,String colorcode,boolean isdeleted,String whendeleted)
    {
        this.chklistid = chklistid;
        this.title = title;
        this.uid = uid;
        this.items = items;
        this.states = states;
        this.colorcode = colorcode;
        this.isdeleted = isdeleted;
        this.whendeleted = whendeleted;
      

    }
    
    public void setAllData(ResultSet set){
        try {

            this.chklistid = set.getInt("chklistid");
            this.title = set.getString("title");
            this.uid = set.getInt("uid");
            this.items = set.getString("items");
            this.states = set.getString("states");
            this.colorcode = set.getString("colorcode");
            this.isdeleted = set.getBoolean("isdeleted");
            this.whendeleted = set.getTimestamp("whendeleted")+"";
            
          
            
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

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getColorcode() {
        return colorcode;
    }

    public void setColorcode(String colorcode) {
        this.colorcode = colorcode;
    }

    public boolean isIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getWhendeleted() {
        return whendeleted;
    }

    public void setWhendeleted(String whendeleted) {
        this.whendeleted = whendeleted;
    }

    
    
    
}
