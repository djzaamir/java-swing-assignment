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
   private int sex; //0 for female 1 for male :P
   private Date dob;
   private int doctor_id; //This is related to Doctor table
   private String disease_history;
   private String prescription;

   //Constructer function
    public Patient(int patient_id, String patient_name, String patient_father_name, int sex, Date dob, int doctor_id, String disease_history, String prescription) {
        this.patient_id = patient_id;
        this.patient_name = patient_name;
        this.patient_father_name = patient_father_name;
        this.sex = sex;
        this.dob = dob;
        this.doctor_id = doctor_id;
        this.disease_history = disease_history;
        this.prescription = prescription;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
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
