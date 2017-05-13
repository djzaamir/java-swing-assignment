/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBA;
import java.sql.*;
/**
 *
 * @author djzaamir
 */
public class DBA {
    
    //vars section
   
    private Connection conn = null;
    private Statement statment = null;
    private final String dbms_url = "jdbc:mysql://localhost:3306/clinic";
    private final String username = "root";
    private final String password = "";
    //end of vars
    
    
    //Constructor function
    public DBA() throws SQLException{
       //do nothing in the constructor
      
    }
    
    //Function to establish connection with the DB server
    private void initDatabaseConnection() throws SQLException{
        try{
            
        //open a connection with the database
        conn =  DriverManager.getConnection(dbms_url, username,password);
        
        }catch(SQLException ex){
            conn.close();
            System.out.println("Error connecting to database");
        }
        
    }
    
    //function to get all records
    public ResultSet selectAll(String table_name) throws SQLException{
        
        //Estabalish connection with DB
        initDatabaseConnection();
        
        //General Query structure for retriing all records
        ResultSet set = null;
        String query = "SELECT * FROM "+table_name;
        Statement statement;
        statement = conn.createStatement();
        set = statement.executeQuery(query);
         //Return the result
        return set;
    }
    
    //function to validate user login , this function returns a tuple about the user credentials
    public User_Login_State userValidate(String username , String password , int type) throws SQLException{
        initDatabaseConnection();
        
        
        //Preparing query 
        ResultSet set = null;
        String query = "SELECT * FROM `User` WHERE `username`=? AND `password`=? AND `user_level`=?";
        PreparedStatement p_statement;
        p_statement = conn.prepareStatement(query);
        
        //Inserting variables into the statement
        p_statement.setString(1, username);
        p_statement.setString(2, password);
        p_statement.setInt(3, type);
        
        
        //Executing query
        set  = p_statement.executeQuery();
        
      
        //Vars to store data and make decisions
        User_Login_State state =  new User_Login_State();
        boolean record_found = false;        

        
        //This is very imp , ResultSet should always be traversed inside a while loop
        while(set.next()){
            record_found = true;
            
            
            state.setIsValid(true);
            state.setUserId(set.getInt("user_id"));
            state.setUserLevel(set.getInt("user_level"));
        }
        
        //if the result set was empty then set the state as false
        if (!record_found) {
            state.setIsValid(false);
        }
        
        
        return state; //this is basically a tuple containing data about the user , like valid or invalid etc
    }
}
