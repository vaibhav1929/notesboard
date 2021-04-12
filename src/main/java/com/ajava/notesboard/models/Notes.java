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
public class Notes {
    int nid;
    int groupid;
    int uid;
    String title;
    String type;
    String content;
    String colorcode;
    boolean isdeleted;
    String whendeleted;
    
    public Notes()
    {}
    public Notes(int nid,int groupid,int uid,String title,String type,String content,String colorcode,boolean isdeleted,String whendeleted)
    {
        this.nid = nid;
        this.groupid = groupid;
        this.uid = uid;
        this.title = title;
        this.type = type;
        this.content = content;
        this.colorcode = colorcode;
        this.isdeleted = isdeleted;
        this.whendeleted = whendeleted;

    }
    
    public void setAllData(ResultSet set){
        try {

            this.nid = set.getInt("nid");
            this.groupid = set.getInt("groupid");
            this.uid = set.getInt("uid");
            this.title = set.getString("title");
            this.type = set.getString("type");
            this.content = set.getString("content");
            this.colorcode = set.getString("colorcode");
            this.isdeleted = set.getBoolean("isdeleted");
            this.whendeleted = set.getTimestamp("whendeleted")+"";
            
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public int getNid() {
        return nid;
    }

    public int getGroupid() {
        return groupid;
    }

    public int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getColorcode() {
        return colorcode;
    }

    public boolean isIsdeleted() {
        return isdeleted;
    }

    public String getWhendeleted() {
        return whendeleted;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setColorcode(String colorcode) {
        this.colorcode = colorcode;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public void setWhendeleted(String whendeleted) {
        this.whendeleted = whendeleted;
    }
    
    
    
}
