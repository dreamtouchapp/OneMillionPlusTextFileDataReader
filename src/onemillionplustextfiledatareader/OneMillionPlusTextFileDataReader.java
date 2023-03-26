/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onemillionplustextfiledatareader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASIF
 */
public class OneMillionPlusTextFileDataReader {

    /**
     * @param args the command line arguments
     */
    private static Connection con;
    private static Statement stmt;

    public static void main(String[] args) {

        try {

            con = MySqlDBConnection.getInstance().getConnection();
            stmt = con.createStatement();

            try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\LENOVO\\Downloads\\1M-customers.txt"))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                try {
                    int count = 0;
                    int s1 = 0;
                    String formattedString = "";
                    while ((s1 = br.read()) != -1) {
                        line = br.readLine();
                        boolean containsChar = containsChar(line, ',');
                        if (containsChar) {
                            String[] customerinfoArray = line.split(",");
                            if (customerinfoArray.length == 8) {
                                String firstName = customerinfoArray[0].replaceAll("\\\\", "").trim();
                                String lastName = customerinfoArray[1];
                                String city = customerinfoArray[2];
                                String state = customerinfoArray[3];
                                String code = customerinfoArray[4].replaceAll("\\s", "").trim();
                                String phone = customerinfoArray[5].replaceAll("\\s", "").trim();
                                String mail = customerinfoArray[6];
                                String ip = customerinfoArray[7];

                                String query1 = "INSERT INTO `customers`(`firstname`,`lastname`,`phone`,`mail`,`city`,`state`,`code`,`ip`) "
                                        + "VALUES('" + firstName + "','" + lastName + "'," + phone + ",'" + mail + "','" + city + "','" + state + "','" + code + "','" + ip + "')";
                                stmt.executeUpdate(query1);

                            }
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (IOException ex) {
                Logger.getLogger(OneMillionPlusTextFileDataReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OneMillionPlusTextFileDataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean containsChar(String s, char search) {
        if (s.length() == 0) {
            return false;
        } else {
            return s.charAt(0) == search || containsChar(s.substring(1), search);
        }
    }

}
