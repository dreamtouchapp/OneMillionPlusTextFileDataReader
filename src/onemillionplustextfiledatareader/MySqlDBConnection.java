/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onemillionplustextfiledatareader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author ASIF
 */
public class MySqlDBConnection {
     
    private static MySqlDBConnection dbinstance;
    private Connection mysqlConnection;
    private MySqlDBConnection() throws SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.mysqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/customersdatabank","root","");
        }catch (ClassNotFoundException ex){
            System.out.println("ERROR ON DB CONNECTION : " + ex.getMessage());
        }
    }
    public Connection getConnection(){
        return mysqlConnection;
    }
    public static MySqlDBConnection getInstance() throws SQLException{
        if(dbinstance == null){
            dbinstance = new MySqlDBConnection();
        }else if(dbinstance.getConnection().isClosed()){
            dbinstance = new MySqlDBConnection();
        }
        return dbinstance;
    }
}