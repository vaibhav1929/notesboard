/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ajava.notesboard.controllers;

import com.ajava.notesboard.models.User;
import com.sun.mail.smtp.DigestMD5;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import sun.security.provider.MD5;

public class AccountHandler extends HttpServlet {
    DBManager dbManager;
    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        dbManager = new DBManager();
    }

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MessagingException {
        response.setContentType("text/html;charset=UTF-8");
        request.getSession().setAttribute("dbmanager", dbManager);
        try (PrintWriter out = response.getWriter()) {
            if(request.getParameter("signup_submit") != null){
                   performSignup(request,response,out);
            }
            else if(request.getParameter("verify_submit") != null){
                   performVerification(request,response);
            }
            else if(request.getParameter("login_submit") != null){
                performLogin(request,response);
            }
        }
    }

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (MessagingException ex) {
            Logger.getLogger(AccountHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (MessagingException ex) {
            Logger.getLogger(AccountHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles all account related business logic";
    }

    private void performSignup(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
            throws ServletException, IOException, MessagingException  {
        if(request.getParameter("signup_email") != null && request.getParameter("signup_name")!= null 
           && request.getParameter("signup_password") != null && request.getParameter("signup_repassword") != null){
 
                String enc_pass = DigestUtils.md5Hex(request.getParameter("signup_password"));  
                String pin = randomPin(); 
                
                User user = new User();
                user.setName(request.getParameter("signup_name"));
                user.setEmail(request.getParameter("signup_email"));
                user.setPassword(request.getParameter("signup_password"));
                user.setPin(Integer.parseInt(pin));
               
                dbManager.addNewUser(user);
                
                sendMail(user.getEmail(),pin);
                response.sendRedirect("verify.jsp");
            
        }
        else{
            request.setAttribute("error","Something went wrong! try again");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    private void performVerification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("verify_email") != null && request.getParameter("verify_otp") != null ){
            User user = dbManager.getUserByEmail(request.getParameter("verify_email"));
            if(user.getName() != null){
                if(user.getPin() == Integer.parseInt(request.getParameter("verify_otp"))){
                   dbManager.makeUserVerified(user.getUid());
                   response.sendRedirect("dashboard.jsp");
                }
                else{
                    request.setAttribute("error","Wrong otp! please try again");
                    request.getRequestDispatcher("verify.jsp").forward(request, response);
                }
            }
        }
        else{
            request.setAttribute("error","Something went wrong! try again");
            request.getRequestDispatcher("verify.jsp").forward(request, response);
        }
    }
    
    private void performLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = dbManager.authenticate(request.getParameter("login_email"), DigestUtils.md5Hex(request.getParameter("login_password")));
        if(user == null){
            request.setAttribute("error","Wrong email or password!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        else{
            if(user.isIsVerified()){
                System.out.println(user.isIsVerified());
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                
                response.sendRedirect("dashboard.jsp");
            }
            else response.sendRedirect("verify.jsp");
            
        }
    }
    
    

    private void sendMail(String emailAddress, String pin) throws AddressException, MessagingException {
        Properties prop = new Properties();
       
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        prop.setProperty("mail.user", getInitParameter("email_username"));
        prop.setProperty("mail.password", getInitParameter("email_password"));
        
        Session mailSession = Session.getInstance(prop, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                   return new PasswordAuthentication(getInitParameter("email_username"), getInitParameter("email_password"));
           }
         });
        MimeMessage mail = new MimeMessage(mailSession);  
        mail.setFrom(getInitParameter("email_username"));
        mail.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
        mail.setSubject("OTP for notesboard");
      
        mail.setContent("Hello there, <br/> <h2>OTP FOR VERIFICATION ON NOTESBOARD:<br><div style='padding:10px; color:white; background:#7e57c2; border-radius:10px'>"+pin+"</div></h2>","text/html");
        Transport.send(mail);
    }
    
    private String randomPin(){
        String[] numbers = {"1","2","3","4","5","6","7","8","9"};
        StringBuilder pin = new StringBuilder();
        for(int i = 0; i < 5; i++){
            pin.append(numbers[(int)(Math.random()*(numbers.length))]);
        }
        return pin.toString();
    }

}
