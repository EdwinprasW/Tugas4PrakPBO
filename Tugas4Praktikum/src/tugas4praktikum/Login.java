/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas4praktikum;
import javax.swing.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
/**
 *
 * @author User
 */
public class Login extends JFrame implements ActionListener{
    JLabel User, Pass, Title;
    JButton LogBtn, RegBtn; 
    final JTextField InUser, InPass;
    Connector konek = new Connector();
    
    public Login() {
        Title = new JLabel("Tugas 4 - Login");
        User = new JLabel("Username : ");
        Pass = new JLabel("Password : ");
        
        LogBtn = new JButton("Login");
        RegBtn = new JButton("Register");
        
        InUser = new JTextField();
        InPass = new JTextField();
        
        setLayout(null);
        
        add(Title);
        add(User);
        add(InUser);
        add(Pass);
        add(InPass);
        add(LogBtn);
        add(RegBtn);
            
        Title.setBounds(75, 5, 200, 20);
        User.setBounds(25, 50, 200, 20);
        InUser.setBounds(25, 100, 200, 20);
        Pass.setBounds(25, 150, 200, 20);
        InPass.setBounds(25, 200, 200, 20);
        
        LogBtn.setBounds(25, 275, 80, 20);
        RegBtn.setBounds(125, 275, 100, 20);
        
        LogBtn.addActionListener(this);
        RegBtn.addActionListener(this);
        
        
        setSize(300, 350);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource() == LogBtn){
          try{
              if(getInUser() == null || getInUser().trim().isEmpty()){
              throw new IllegalArgumentException("Username is empty");
              }
              if(getInPass() == null || getInPass().trim().isEmpty()){
              throw new IllegalArgumentException("Password is empty");
              }
              
               String query = "SELECT password FROM users WHERE username = '"+ getInUser() +"'";
               konek.statement = konek.koneksi.createStatement();
               ResultSet result = konek.statement.executeQuery(query);
               if(result.next()){
                   String pw = result.getString("password");
                   if(!pw.equals(enPassMD5(getInPass()))){
                       JOptionPane.showMessageDialog(new JFrame(), "Password Salah");
                   }else{
                        JOptionPane.showMessageDialog(new JFrame(), "Berhasil Login");
                    }
               }else{
                   JOptionPane.showMessageDialog(new JFrame(), "Username Tidak Ditemukan");
               }
               konek.statement.close();
               
          }catch(Exception error){
              JOptionPane.showMessageDialog(new JFrame(), error.getMessage());
          }   
      }
      
      if(e.getSource() == RegBtn){
          setVisible(false);
          Register reg = new Register();
          reg.setLocationRelativeTo(null);
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
 

    public String getInUser() {
        return InUser.getText();
    }

    public String getInPass() {
        return InPass.getText();
    }
    
    
    
    
    
}
