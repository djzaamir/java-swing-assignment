/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBA;
import java.sql.*;
import java.util.LinkedList;
import Support.*;
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
    
    //function to add a new patietn
    public int addPatient(Patient new_patient) throws SQLException{
        
        initDatabaseConnection();   
        
        String query = "INSERT INTO `Patient`  (`patient_name`,`patient_father_name` , `sex` , `dob` , `doctor_name`)   values(?,?,?,?,?)";
        PreparedStatement statement =  conn.prepareStatement(query , Statement.RETURN_GENERATED_KEYS);
        
        //Inserting data into prepared statement
        
        statement.setString(1, new_patient.getPatient_name());
        statement.setString(2, new_patient.getPatient_father_name());
        statement.setBoolean(3,new_patient.getSex());//here false means female and True Means Male
        //java.sql.Date sqlDate = new java.sql.Date(new_patient.getDob().getTime());
        statement.setDate(4, new_patient.getDob());
        statement.setString(5, new_patient.getDoctor_name());
        
        //Executing statment here
         statement.executeUpdate();
          ResultSet set =  statement.getGeneratedKeys();
          int id = 0;
          while(set.next()){
             id  =  set.getInt(1);
          }
          return id;
     }
    
    //function to add a doctor
    public boolean addDoctor(Doctor d) throws SQLException{
     
        initDatabaseConnection();
        
        String query = "INSERT INTO `Doctor` (`doctor_specialization`,`doctor_name`)"
                        + "VALUES(?,?)";
        PreparedStatement statement =  conn.prepareStatement(query);
        
        System.out.println(d.getDoctor_name());
        System.out.println(d.getDoctor_specialization());
        
        
        //inserting values into prepare statemtn
        statement.setString(1, d.getDoctor_specialization());
        statement.setString(2, d.getDoctor_name());
        
       
        return statement.execute() == false;
    }
    
    //function to insert disease history
    public boolean addDiseaseHistory(int new_p_id ,Patient p) throws SQLException {
        
        //init connection with db
       
        initDatabaseConnection();
        
        String d_query = "INSERT INTO `Disease_History` (`patient_id` , `disease_history` , `history_timestamp`) VALUES(?,?,?)";
        
        //Preparing statment
        PreparedStatement statement =  conn.prepareStatement(d_query);
        
        //Inserting data in statement
        statement.setInt(1,new_p_id);
        statement.setString(2,p.getDisease_history());
        statement.setDate(3, new java.sql.Date(Support.getTimeStamp()));
        
        return statement.execute() == false ? true:false;
     }
    
    //function to add a new Disease 
    public boolean addDisease(Disease d) throws SQLException{
        
        initDatabaseConnection();
        
        String query  = "INSERT INTO `disease` (`disease_name`,`disease_description`) VALUES(?,?)";
        
        PreparedStatement statement  =   conn.prepareStatement(query);
        
        //inserting data into the statement
        statement.setString(1, d.getDisease_name());
        statement.setString(2, d.getDisease_description());
        
     return   statement.executeUpdate() == 1;
    }
    
     //fuction to insert prescription histry
    public boolean addPrescriptionHistory(int new_p_id , Patient p) throws SQLException{
        
        //Now doing the same for prescription /inserting data for prescription
      
        initDatabaseConnection();
        
        String p_query = "INSERT INTO `Prescription_History` (`patient_id` , `prescription` , `prescription_timestamp`) VALUES(?,?,?)";
        
        //Preparing statment
        PreparedStatement statement_p =  conn.prepareStatement(p_query);
        
        
        //Inserting data in statement
        statement_p.setInt(1,new_p_id);
        statement_p.setString(2,p.getPrescription());
        statement_p.setDate(3, new java.sql.Date(Support.getTimeStamp()));
        return statement_p.execute() == false ? true:false;
    }
    
    //function to get All Diseases
    public LinkedList<String> getDiseases() throws SQLException{
        //Establish connection with Db
        initDatabaseConnection();
        
        LinkedList<String> diseases =  new LinkedList<>();
        
        String Query = "SELECT * FROM `Disease`";
        Statement statement =  conn.createStatement();
        
        ResultSet set = statement.executeQuery(Query);
        
        while(set.next()){
            diseases.add(set.getString("disease_name"));
        }
        
        return diseases;
    }
    
     //function to get all doctor names
    public LinkedList<String> getDoctors() throws SQLException{
        
        //init connection with the Db
        initDatabaseConnection();
        
        LinkedList<String> doctors = new LinkedList<>();
        
        String Query =  "SELECT * FROM `Doctor`";
        Statement statement = conn.createStatement();
        ResultSet set  = statement.executeQuery(Query);
        
        if (set != null) {
            while (set.next()){
                doctors.add(set.getString("doctor_name"));
            }
        }
        return doctors;
    }
    
    //function to get all patients from db
    public LinkedList<Patient> getPatients() throws SQLException{
        
        initDatabaseConnection();
        LinkedList<Patient> patients = new LinkedList<Patient>();
        
        String Query  ="SELECT * FROM `Patient";
        
        Statement statement = conn.createStatement();
        
        ResultSet set  = statement.executeQuery(Query);
        
        while(set.next()){
            Patient p =  new Patient();
            p.setPatient_id(set.getInt("patient_id"));
            p.setPatient_name(set.getString("patient_name"));
            p.setPatient_father_name(set.getString("patient_father_name"));
            p.setSex(set.getBoolean("sex"));
            p.setDob(set.getDate("dob"));
            p.setDoctor_name(set.getString("doctor_name"));
            
            //Append this doctor to list
            patients.add(p);
            
        }
        return patients;
    }
    
}
