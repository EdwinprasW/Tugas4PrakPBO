/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas4praktikum;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.sql.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author User
 */
public class Register extends JFrame implements ActionListener{
    JLabel User, Pass, Title;
    JButton LogBtn, RegBtn; 
    final JTextField InRegU, InRegP;
    Connector konek = new Connector();
    
    public Register() {
        Title = new JLabel("Tugas 4 - Register");
        User = new JLabel("Username : ");
        Pass = new JLabel("Password : ");
        
        LogBtn = new JButton("Login");
        RegBtn = new JButton("Register");
        
        InRegU = new JTextField();
        InRegP = new JTextField();
        
        setLayout(null);
        add(Title);
        add(User);
        add(InRegU);
        add(Pass);
        add(InRegP);
        add(LogBtn);
        add(RegBtn);
            
       Title.setBounds(75, 5, 200, 20);
        User.setBounds(25, 50, 200, 20);
        InRegU.setBounds(25, 100, 200, 20);
        Pass.setBounds(25, 150, 200, 20);
        InRegP.setBounds(25, 200, 200, 20);
        
        RegBtn.setBounds(25, 275, 100, 20);
        LogBtn.setBounds(150, 275, 80, 20);
        
        LogBtn.addActionListener(this);
        RegBtn.addActionListener(this);
        
        
        setSize(300, 350);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource() == RegBtn){
          try{
              if(getInRegU() == null || getInRegU().trim().isEmpty()){
              throw new IllegalArgumentException("Username is empty");
              }
              if(getInRegP() == null || getInRegP().trim().isEmpty()){
              throw new IllegalArgumentException("Password is empty");
              }
              
               String query = "SELECT username FROM users WHERE username = '"+ getInRegU() +"'";
               konek.statement = konek.koneksi.createStatement();
               ResultSet result = konek.statement.executeQuery(query);
               if(result.next()){
                   JOptionPane.showMessageDialog(new JFrame(), "Username Telah Digunakan");
               }else{
                   String InQuery = "INSERT INTO users(username, password) VALUES ('"+getInRegU()+"', '" + enPassMD5(getInRegP())+ "')";
                   konek.statement = konek.koneksi.createStatement();
                   konek.statement.executeUpdate(InQuery);
                   JOptionPane.showMessageDialog(new JFrame(), "RegisterBerhasil");
               }
               konek.statement.close();
               
          }catch(Exception error){
              JOptionPane.showMessageDialog(new JFrame(), error.getMessage());
          }   
      }
      
      if(e.getSource() == LogBtn){
          setVisible(false);
          Login login = new Login();
          login.setLocationRelativeTo(null);
      }
    }

    private String enPassMD5(String message){
    String digest = null;
        try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] hash = md.digest(message.getBytes("UTF-8"));
                //merubah byte array ke dalam String Hexadecimal
                StringBuilder sb = new StringBuilder(2*hash.length);
                for(byte b : hash)
                {
                        sb.append(String.format("%02x", b&0xff));
                }
                digest = sb.toString();
            } catch (Exception e)
                {
                         e.printStackTrace();
                }
                      return digest;
    } 
    
    public String getInRegU() {
        return InRegU.getText();
    }

    public String getInRegP() {
        return InRegP.getText();
    }
    
    
    
    
    
}
