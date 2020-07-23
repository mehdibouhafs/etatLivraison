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
import java.util.ResourceBundle.Control;
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
        
        Locale locale = new Locale("en", "US");
		ResourceBundle bundle= ResourceBundle.getBundle("config",locale);
        //String host = "130.24.31.4";
		String host = bundle.getString("Host");
        database = bundle.getString("Database");
        String login = Encryption.decrypt(bundle.getString("Login"));
        String password =Encryption.decrypt(bundle.getString("Password"));
       
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
    
    public static ResultSet request(String req) throws SQLException{
        
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

	public static Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connection) {
		DBA.connection = connection;
	}
    
    

}
