/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import web.SthefanyDbListener;

/**
 *
 * @author danto
 */
public class User {
    private String username;
    private String password;
    private String name;

    public User(String username, String name) {
        this.username = username;
        this.name = name;
    }
    
    public static String getCreateStatement(){
        return "CREATE TABLE IF NOT EXISTS Sthefany_users("
                + "username VARCHAR(50) UNIQUE NOT NULL,"
                + "password LONG NOT NULL"
                + "name VARCHAR(200) NOT NULL,"
                + ")";
    }
    
    public static ArrayList<User> getUsers() throws Exception{
        ArrayList<User> list = new ArrayList<>();
        Connection con = SthefanyDbListener.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from Sthefany_users");
        while(rs.next()){
            String username = rs.getString("username");
            String password = rs.getString("password");
            String name = rs.getString("name");
            list.add(new User(username, name));
        }
        rs.close();
        stmt.close();
        con.close();
        return list;
    }
    
    public static User getUser(String usuario, String password) throws Exception{
        User user = null;
        Connection con = SthefanyDbListener.getConnection();
        String sql = "SELECT * from Sthefany_users WHERE login=? AND password=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, usuario);
        stmt.setLong(2, password.hashCode());
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            String username = rs.getString("username");
            String name = rs.getString("name");
            user = new User(username, name);
        }
        rs.close();
        stmt.close();
        con.close();
        return user;
    }
    
    public static void insertUser(String username, String password, String name) throws Exception{
        Connection con = SthefanyDbListener.getConnection();
        String sql = "INSERT INTO Sthefany_users(username, password, name) "
                + "VALUES(?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setLong(2, password.hashCode());
        stmt.setString(3, name);
        stmt.execute();
        stmt.close();
        con.close();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
