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
public class Access {
    private String username;
    private String datetime;
    
    public static String getCreateStatement(){
        return "CREATE TABLE IF NOT EXISTS Sthefany_access_history("
                + "username VARCHAR(50) NOT NULL,"
                + "datetime VARCHAR(25) NOT NULL"
                + ")";
    }

    public Access(String username, String datetime) {
        this.username = username;
        this.datetime = datetime;
    }
    
    public static ArrayList<Access> getAccess() throws Exception{
        ArrayList<Access> list = new ArrayList<>();
        Connection con = SthefanyDbListener.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from Sthefany_access_history");
        while(rs.next()){
            String username = rs.getString("username");
            String datetime = rs.getString("datetime");
            list.add(new Access(username, datetime));
        }
        rs.close();
        stmt.close();
        con.close();
        return list;
    }
    
    public static ArrayList<Access> getMyAccess(String usuario) throws Exception{
        ArrayList<Access> list = new ArrayList<>();
        Connection con = SthefanyDbListener.getConnection();
        String sql = "SELECT * from Sthefany_access_history WHERE username=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, usuario);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            String username = rs.getString("username");
            String datetime = rs.getString("datetime");
            list.add(new Access(username, datetime));
        }
        rs.close();
        stmt.close();
        con.close();
        return list;
    }
    
    public static void insertAccess(String username, String datetime) throws Exception{
        Connection con = SthefanyDbListener.getConnection();
        String sql = "INSERT INTO Sthefany_access_history(username, datetime) "
                + "VALUES(?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(3, datetime);
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    
    
}
