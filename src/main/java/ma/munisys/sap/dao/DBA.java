/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.munisys.sap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abassou
 */
public class DBA {
    
    static Connection connection = null;
    static String database =null;
    static ResultSet result=null;
    public static Connection connect(){
        
        
        //ResourceBundle bundle= ResourceBundle.getBundle("configuration.config",locale);
        String host = "130.24.31.12";
        database = "DB_MUNISYS";
        String login = Encryption.decrypt("FLFGRZ");
        String password =Encryption.decrypt("Nqzva2015");
       
        try {
        	 Class.forName("com.sap.db.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:sap://"+host+":30015",login,password);
        } catch (SQLException ex) {
            Logger.getLogger(DBA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return connection;
    }
    
    public static ResultSet request(String req){
        
        
        try {
            connect();
            Statement stmt=connection.createStatement();
            result = stmt.executeQuery(req);
            
        } catch (SQLException ex) {
        	ex.printStackTrace();
            Logger.getLogger(DBA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
        
    }

}
