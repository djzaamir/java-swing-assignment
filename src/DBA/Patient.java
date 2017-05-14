/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBA;

import java.sql.Date;

/**
 *
 * @author djzaamir
 */
public class Patient {
   
   private int patient_id;
   private String patient_name;
   private String patient_father_name;
   private boolean sex; //0 for female 1 for male :P
   private java.sql.Date dob;
   private String doctor_name; //This is related to Doctor table
   private String disease_history;
   private String prescription;

   //Constructer function
    public Patient(int patient_id, String patient_name, String patient_father_name, boolean sex, java.sql.Date dob, String doctor_name, String disease_history, String prescription) {
        this.patient_id = patient_id;
        this.patient_name = patient_name;
        this.patient_father_name = patient_father_name;
        this.sex = sex;
        this.dob = dob;
        this.doctor_name = doctor_name;
        this.disease_history = disease_history;
        this.prescription = prescription;
    }

    public Patient() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
   
    

    //Getter and setters
    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_father_name() {
        return patient_father_name;
    }

    public void setPatient_father_name(String patient_father_name) {
        this.patient_father_name = patient_father_name;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public java.sql.Date getDob() {
        return dob;
    }

    public void setDob(java.sql.Date dob) {
        this.dob = dob;
    }

    public String getDoctor_name() {
        return this.doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDisease_history() {
        return disease_history;
    }

    public void setDisease_history(String disease_history) {
        this.disease_history = disease_history;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
   
   
   
   
}
