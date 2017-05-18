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

    private int tryToParseInt(String parseInt) {
       boolean good_to_parse = true;
        for (int i = 0; i < parseInt.length(); i++) {
            if (parseInt.charAt(i) >= '0' && parseInt.charAt(i) <= '9') {
               //do nothing
            }else{
                good_to_parse = false;
                break;
            }
        }
        
        if (good_to_parse) {
            return  Integer.parseInt(parseInt);
        }else{
            return 0;
        }
   }
    public static enum SEARCH {BY_ID , BY_NAME  , BY_DISEASE , BY_DOCTOR_NAME , BY_DOB};
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
    
    
    
    //verstile search function
    public LinkedList<Patient> searchPatient(SEARCH search_type ,  Object test_content) throws SQLException{
        
        initDatabaseConnection();
        
        LinkedList<Patient> to_return =  new LinkedList<>();
        String query_param = "";
        String casted_test_content_str = "";
        int casted_test_content_int = 0;
       
       
        
        //Now checking the type of Search Query and adjusting search parameter accordingly
        if (search_type == SEARCH.BY_ID) {
            query_param = "patient_id";
            casted_test_content_int = tryToParseInt((String)test_content);
        }else if(search_type == SEARCH.BY_NAME){
            query_param = "patient_name";
            casted_test_content_str = (String)test_content;
        }else if(search_type == SEARCH.BY_DOCTOR_NAME){
            query_param = "doctor_name";
            casted_test_content_str = (String)test_content;
        }else if(search_type == SEARCH.BY_DOB){
            query_param = "dob";
            casted_test_content_int =tryToParseInt((String)test_content);
        }
        
        
        
        
        
        String query = "";
        if (search_type == SEARCH.BY_ID) {
            query = "SELECT * FROM `Patient` WHERE `"+query_param+"`=?";
        }else if(search_type == SEARCH.BY_DOB){
            query = "SELECT * FROM `Patient`";
        }else{
            query = "SELECT * FROM `Patient` WHERE `"+query_param+"` LIKE ?";
        }
        
        PreparedStatement statement =  conn.prepareStatement(query);
        
        //Now inserting data into the prepared statment
        //Based on type of insertion we will use two different inserting methods
        //use Integer Insertion
        
        if (search_type == SEARCH.BY_ID) {
            statement.setInt(1, casted_test_content_int);
        }
        else if(search_type == SEARCH.BY_DOB){
           // do nothing in this case , although its such terrible style of coding
        }
        //Otherwise the we will use String insertion
        else{
            statement.setString(1, casted_test_content_str);
        }
        
        
        
         ResultSet set =  statement.executeQuery();
          while (set.next()) {            
             Patient p =  new Patient();
             p.setPatient_id(set.getInt("patient_id"));
             p.setPatient_name(set.getString("patient_name"));
             p.setPatient_father_name(set.getString("patient_father_name"));
             p.setSex(set.getBoolean("sex"));
             p.setDob(set.getDate("dob"));
             p.setDoctor_name(set.getString("doctor_name"));
         
             to_return.add(p);
          }
        
        return to_return;
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
   
     
    //function to update a patient
    public boolean updatePatient(Patient update_patient , int pk) throws SQLException{
        
        initDatabaseConnection();
        
        //update query
        String query = "UPDATE `Patient` SET `patient_name`=? ,`patient_father_name`=? , `sex`=? , `dob`=? , `doctor_name`=? WHERE `patient_id`=?";
        
        PreparedStatement statement  =  conn.prepareStatement(query);
        
        //Inserting data into the statement
        statement.setString(1, update_patient.getPatient_name());
        statement.setString(2, update_patient.getPatient_father_name());
        statement.setBoolean(3,update_patient.getSex());//here false means female and True Means Male
        //java.sql.Date sqlDate = new java.sql.Date(new_patient.getDob().getTime());
        statement.setDate(4, update_patient.getDob());
        statement.setString(5, update_patient.getDoctor_name());
        
        //insert pk into the statement
        statement.setInt(6, pk);
        
        //Executing statment here
        return statement.execute() == false;
        
    }
    
    
    //function to update disease History
    public boolean updateDiseaseHistory(String disease_h , int pk) throws SQLException{
        initDatabaseConnection();
        
        //update query
        String query = "UPDATE `Disease_History` SET `disease_history`=? WHERE `patient_id`=?";
        
        PreparedStatement statement  =  conn.prepareStatement(query);
        
        //inserting data into the statement
        statement.setString(1, disease_h);
        statement.setInt(2, pk);
        
        return statement.execute() == false;
    }
    
    
    //function to update prescription 
    public boolean updatePrescription(String prescription_h , int pk) throws SQLException{
        initDatabaseConnection();
        
        //update query
        String query = "UPDATE `Prescription_History` SET `prescription`=? WHERE `patient_id`=?";
        
        PreparedStatement statement = conn.prepareStatement(query);
        
        //inserting data into statement
        
        
        statement.setString(1,prescription_h);
        statement.setInt(2, pk);
        
        return statement.execute() == false;
    }
    
    
    //function to update doctor record
    public boolean updateDoctor(Doctor d ,  int pk) throws SQLException{
        initDatabaseConnection();
        
        String query = "UPDATE `Doctor` SET `doctor_name`=? , `doctor_specialization`=? WHERE `doctor_id`=?";
        
        PreparedStatement statement = conn.prepareStatement(query);
        
        //insert data into statement
        statement.setString(1, d.getDoctor_name());
        statement.setString(2, d.getDoctor_specialization());
        statement.setInt(3, pk);
        return statement.execute() == false;
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
    public LinkedList<Doctor> getDoctors() throws SQLException{
        
        //init connection with the Db
        initDatabaseConnection();
        
        LinkedList<Doctor> doctors = new LinkedList<>();
        
        String Query =  "SELECT * FROM `Doctor`";
        Statement statement = conn.createStatement();
        ResultSet set  = statement.executeQuery(Query);
        
        if (set != null) {
            while (set.next()){
                Doctor new_doctor = new Doctor(set.getInt("doctor_id") , set.getString("doctor_specialization") , set.getString("doctor_name"));
                doctors.add(new_doctor);
            }
        }
        return doctors;
    }
    
    //function to get a single doctor from database
    public Doctor getDoctor(int pk) throws SQLException{
        initDatabaseConnection();
        Doctor d =  new Doctor();
        String query = "SELECT * FROM `Doctor` WHERE `doctor_id`=?";
        
        PreparedStatement statement  =  conn.prepareStatement(query);
        
        //insert into query
        statement.setInt(1, pk);
        
        ResultSet set = statement.executeQuery();
        
        while(set.next()){
            d.setDoctor(set.getInt(pk));
            d.setDoctor_name(set.getString("doctor_name"));
            d.setDoctor_specialization_id(set.getString("doctor_specialization"));
        }
        return d;
    }
    
    //function to get dcotors based upon field of specialization
    public LinkedList<Doctor> searchDoctorBasedUponSpecialization(String specialization) throws SQLException{
        LinkedList<Doctor> to_return  =  new LinkedList<>();
        
        initDatabaseConnection();
        
        String query =  "SELECT * FROM `Doctor` WHERE `doctor_specialization`=?";
        
        PreparedStatement statement =  conn.prepareStatement(query);
        
        //insert values into query
        
        statement.setString(1, specialization);
        
        ResultSet set = statement.executeQuery();
        
        while(set.next()){
            Doctor d =  new Doctor();
            d.setDoctor(set.getInt("doctor_id"));
            d.setDoctor_name(set.getString("doctor_name"));
            d.setDoctor_specialization_id(set.getString("doctor_specialization"));
            to_return.add(d);
        }
        
        return to_return;
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
    
    public Patient getPatient(int pk) throws SQLException{
        
        initDatabaseConnection();
        
        Patient to_return =  new Patient();
        
        String query = "SELECT * FROM `Patient` WHERE `patient_id`=?";
        
        //Initiating PrepareStatement
        PreparedStatement statement  =  conn.prepareStatement(query);
        
        //inseting values into preparestatement
        statement.setInt(1, pk);
        
        ResultSet set =  statement.executeQuery();
        
        while(set.next()){
            
            to_return.setPatient_id(pk);
            to_return.setPatient_name(set.getString("patient_name"));
            to_return.setPatient_father_name(set.getString("patient_father_name"));
            to_return.setSex(set.getBoolean("sex"));
            to_return.setDob(set.getDate("dob"));
            to_return.setDoctor_name(set.getString("doctor_name"));
            
        }
        
        return to_return;
    }
 
    
    public Disease_History getDisease_History(int pk) throws SQLException{
        initDatabaseConnection();
        
        Disease_History disease_History  =  new Disease_History();
        String query = "SELECT * FROM `Disease_History` WHERE `patient_id`=?";
        
        PreparedStatement statement =  conn.prepareStatement(query);
        
        statement.setInt(1, pk);
        ResultSet set =  statement.executeQuery();
        
        while(set.next()){
            disease_History.setHistory_id(set.getInt("history_id"));
            disease_History.setPatient_id(set.getInt("patient_id"));
            disease_History.setDisease_history(set.getString("disease_history"));
            disease_History.setHistory_timestamp(set.getDate("history_timestamp"));
        }
        return disease_History;
    } 
    
    
    public Prescription_History getPrescription_History(int pk) throws SQLException{
        initDatabaseConnection();
        
        Prescription_History pres_History  =  new Prescription_History();
        String query = "SELECT * FROM `Prescription_History` WHERE `patient_id`=?";
        
        PreparedStatement statement =  conn.prepareStatement(query);
        
        statement.setInt(1, pk);
        ResultSet set =  statement.executeQuery();
        
        while(set.next()){
            pres_History.setPrescription_id(set.getInt("prescription_id"));
            pres_History.setPatient_id(pk);
            pres_History.setPrescription(set.getString("prescription"));
            pres_History.setPrescription_timestamp(set.getDate("prescription_timestamp"));
        }
        return pres_History;
    }
    
    //function to delete a single patient from patient table
    public boolean delPatient(int id_to_del ) throws SQLException{
        
        initDatabaseConnection();
        
        String query  ="DELETE FROM `Patient` WHERE `patient_id`=?";
        
        PreparedStatement statement  =  conn.prepareStatement(query);
        
        //inserting data into the prepared statement 
        statement.setInt(1, id_to_del);
      
        return statement.execute()==false;
        }
}
