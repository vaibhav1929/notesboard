
package com.ajava.notesboard.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author "Vaibhav Sorathiya (19BCE529)"
 */
public class User {
    int uid;
    String name;
    String email;
    String password;
    int pin;
    boolean isVerified;
    String vaultPassword;
 
    public User() {
    }

    public User(int uid, String name, String email, String password, int pin, boolean isVerified, String vaultPassword) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.pin = pin;
        this.isVerified = isVerified;
        this.vaultPassword = vaultPassword;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public boolean isIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getVaultPassword() {
        return vaultPassword;
    }

    public void setVaultPassword(String vaultPassword) {
        this.vaultPassword = vaultPassword;
    }
    
    public void setAllData(ResultSet set){
        try {
            this.uid = set.getInt("uid");
            this.name = set.getString("name");
            this.email = set.getString("email");
            this.password = set.getString("password");
            this.pin = set.getInt("pin");
            this.isVerified = set.getBoolean("isVerified");
            this.vaultPassword = set.getString("vaultPassword");
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
