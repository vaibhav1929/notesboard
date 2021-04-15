package com.ajava.notesboard.controllers;


import com.ajava.notesboard.models.NoteGroup;
import com.ajava.notesboard.models.Notes;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ajava.notesboard.models.User;
import java.util.ArrayList;
/*
 * @author "Vaibhav Sorathiya (19BCE529)"
 */
public class DBManager {
    Connection con;
    PreparedStatement insertAccount, updateAccount, loginAccount, emailAccount, notesInsert, notesUpdate, notesDelete,getNotesByUidStatment,getNotesByUidAndNidStatment;
    PreparedStatement getGroupByGidStatement, getGroupByUidStatement;
    
    public DBManager(){
        if(con == null){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/s6vt614Mzb?characterEncoding=latin1","s6vt614Mzb","mniGf6vI7L");
                
                // All queries will be compiled here by preparedStatement.
                if(con != null){
                    System.out.println("-----------------------CON SUCCESS---------------------------");
                }
                insertAccount = con.prepareStatement("INSERT INTO Users(name,email,password,pin) VALUES(?,?,?,?)");
                
                updateAccount = con.prepareStatement("UPDATE Users SET name = ?, email = ?, password = ?, pin = ?, isVerified = ?, vaultPassword = ? WHERE uid = ?");
                
                loginAccount = con.prepareStatement("SELECT * FROM Users WHERE email = ? AND password = ?");
                
                emailAccount = con.prepareStatement("SELECT * FROM Users WHERE email = ?");
                
                //---------------------------------NOTES----------------------------------------------------
                notesInsert = con.prepareStatement("INSERT INTO Notes(groupid,uid,title,type,content,colorcode) VALUES(?,?,?,?,?,?)",new String[]{"nid"});
                
                notesUpdate = con.prepareStatement("UPDATE Notes SET groupid = ?, uid = ?, title = ?, type = ?, content = ?, colorcode = ?, isdeleted = ?, whendeleted = ? WHERE nid = ?");
                
                notesDelete = con.prepareStatement("DELETE FROM Notes WHERE nid = ?");
                
                getNotesByUidStatment = con.prepareStatement("SELECT * FROM Notes WHERE uid = ? ORDER BY groupid");
                
                getNotesByUidAndNidStatment = con.prepareStatement("SELECT * FROM Notes WHERE uid = ? AND nid = ?");
               
                //--------------------------------GROUP-------------------------------------------------------
                getGroupByGidStatement = con.prepareStatement("SELECT * FROM NoteGroup WHERE groupid = ?");
                
                getGroupByUidStatement = con.prepareStatement("SELECT * FROM NoteGroup WHERE uid = ?");
               
            }catch(Exception e){
                System.err.println(e);
            }
            
        }
    }
    
    public ArrayList<Notes> getNotesByUid(int uid){
        ArrayList<Notes> ret=new ArrayList<Notes>();
        try {
            getNotesByUidStatment.setInt(1, uid);
            System.out.println(getNotesByUidStatment);
            ResultSet set = getNotesByUidStatment.executeQuery();
            while(set.next()){
                
                if(set.getTimestamp("whendeleted") != null){
                    Notes temp = new Notes(set.getInt("nid"),set.getInt("groupid"),set.getInt("uid"),set.getString("title"),set.getString("type"),set.getString("content"),set.getString("colorcode"),set.getBoolean("isdeleted"), set.getTimestamp("whendeleted").toString(), getGroupByGid(set.getInt("groupid")));
                    ret.add(temp);
                }
                else{
                    Notes temp = new Notes(set.getInt("nid"),set.getInt("groupid"),set.getInt("uid"),set.getString("title"),set.getString("type"),set.getString("content"),set.getString("colorcode"),set.getBoolean("isdeleted"), null, getGroupByGid(set.getInt("groupid")));
                    ret.add(temp);
                }
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    public Notes getNotesByUidAndNid(int uid,int nid){
        Notes temp=new Notes();
        try {
            getNotesByUidAndNidStatment.setInt(1, uid);
            getNotesByUidAndNidStatment.setInt(2, nid);
            ResultSet set = getNotesByUidAndNidStatment.executeQuery();
            while(set.next()){
                 temp=new Notes(set.getInt("nid"),set.getInt("groupid"),set.getInt("uid"),set.getString("title"),set.getString("type"),set.getString("content"),set.getString("colorcode"),set.getBoolean("isdeleted"),set.getTimestamp("whendeleted").toString(), getGroupByGid(set.getInt("groupid")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }
    
    
    
    public boolean deleteNote(int nid){
        try {
            notesDelete.setInt(1, nid);
            int rows = notesDelete.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return false;
    }
    
        public boolean updateNote(Notes note){
        try {
            notesUpdate.setInt(1, note.getGroupid());
            notesUpdate.setInt(2, note.getUid());
            notesUpdate.setString(3, note.getTitle());
            notesUpdate.setString(4, note.getType());
            notesUpdate.setString(5, note.getContent());
            notesUpdate.setString(6, note.getColorcode());
            notesUpdate.setBoolean(7, note.isIsdeleted());
            Long miliseconds = Long.valueOf(note.getWhendeleted());
            Timestamp ti = new Timestamp(miliseconds);
            notesUpdate.setTimestamp(8, ti);
            notesUpdate.setInt(9, note.getNid());
            int rows = notesUpdate.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return false;
    }
    
        
    
    
        public int addNewNote(Notes note){
        try {
            notesInsert.setInt(1, note.getGroupid());
            notesInsert.setInt(2, note.getUid());
            notesInsert.setString(3, note.getTitle());
            notesInsert.setString(4, note.getType());
            notesInsert.setString(5, note.getContent());
            notesInsert.setString(6, note.getColorcode());
            int rows = notesInsert.executeUpdate();
           
            boolean isAdded = rows > 0;
            if(isAdded){
                ResultSet set = notesInsert.getGeneratedKeys();
                set.next();
                return set.getInt(1);
            }
            else return -1;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return -1;
    }
    
        public NoteGroup getGroupByGid(int groupid){
            NoteGroup group = new NoteGroup();
        try {
            getGroupByGidStatement.setInt(1, groupid);
            ResultSet set = getGroupByGidStatement.executeQuery();
            while(set.next()){
                 group = new NoteGroup(set.getInt("groupid"),set.getInt("uid"),set.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
          return group;  
        }

        public ArrayList<NoteGroup> getGroupsByUid(int uid){
        ArrayList<NoteGroup> ret=new ArrayList<NoteGroup>();
        try {
            getGroupByUidStatement.setInt(1, uid);
            System.out.println(getGroupByUidStatement);
            ResultSet set = getGroupByUidStatement.executeQuery();
            while(set.next()){
                    NoteGroup temp = new NoteGroup(set.getInt("groupid"),set.getInt("uid"),set.getString("name"));
                    ret.add(temp);
 
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
        
            public boolean updateUser(User user){
        try {
            updateAccount.setString(1, user.getName());
            updateAccount.setString(2, user.getEmail());
            updateAccount.setString(3, user.getPassword());
            updateAccount.setInt(4, user.getPin());
            updateAccount.setBoolean(5, user.isIsVerified());
            updateAccount.setString(6, user.getVaultPassword());
            updateAccount.setInt(7, user.getUid());
            int rows = updateAccount.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return false;
    }
    
    public boolean addNewUser(User user){
        try {
            insertAccount.setString(1, user.getName());
            insertAccount.setString(2, user.getEmail());
            insertAccount.setString(3, user.getPassword());
            insertAccount.setInt(4, user.getPin());
            int rows = insertAccount.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return false;
    }
    

    
    
    public User getUserByEmail(String email){
        User user = new User();
        try {
            emailAccount.setString(1, email);
            ResultSet set = emailAccount.executeQuery();
            if(set.absolute(1)){
                user.setAllData(set);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
    public void makeUserVerified(int uid){
        Statement verify;
        try {
            verify = con.createStatement();
            verify.executeUpdate("UPDATE Users SET isVerified=true WHERE uid="+uid);
            
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public User authenticate(String email, String password){
        User user = null;
        try {
            loginAccount.setString(1, email);
            loginAccount.setString(2, password);
            System.out.println(loginAccount);
            ResultSet set = loginAccount.executeQuery();
            boolean found = set.next();
            if(found){
              user = new User();
              user.setAllData(set);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
   
}
