/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ajava.notesboard.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DEEP MENPARA
 */
public class NoteGroup {
    
    int groupid;
    int uid;
    String name;
    
    public NoteGroup()
    {}
    public NoteGroup(int groupid, int uid, String name)
    {
        this.groupid = groupid;
        this.uid = uid;
        this.name = name;

    }
    
    public void setAllData(ResultSet set){
        try {

            this.groupid = set.getInt("groupid");
            this.name = set.getString("name");
            
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
    
    
}
