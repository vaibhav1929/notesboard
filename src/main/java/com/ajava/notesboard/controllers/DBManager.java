package com.ajava.notesboard.controllers;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ajava.notesboard.models.User;
/*
 * @author "Vaibhav Sorathiya (19BCE529)"
 */
public class DBManager {
    Connection con;
    PreparedStatement insertAccount, updateAccount, loginAccount, emailAccount;
    
    public DBManager(){
        if(con == null){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/s6vt614Mzb?characterEncoding=latin1","s6vt614Mzb","mniGf6vI7L");
                
                // All queries will be compiled here by preparedStatement.
                if(con != null){
                    System.out.println("-----------------------HERE IS A PROBLEM---------------------------");
                }
                insertAccount = con.prepareStatement("INSERT INTO Users(name,email,password,pin) VALUES(?,?,?,?)");
                
                updateAccount = con.prepareStatement("UPDATE Users SET name = ?, password = ? WHERE uid = ?");
                
                loginAccount = con.prepareStatement("SELECT * FROM Users WHERE email = ? AND password = ?");
                
                emailAccount = con.prepareStatement("SELECT * FROM Users WHERE email = ?");
               
            }catch(Exception e){
                System.err.println(e);
            }
            
        }
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
