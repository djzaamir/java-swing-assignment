/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBA;

import java.sql.Date;

/**
 *
 * @author Aamir
 */
public class Prescription_History {
    private int prescription_id; 
    private int patient_id;
    private String prescription;
    private Date prescription_timestamp;

    public Prescription_History(int prescription_id, int patient_id, String prescription, Date prescription_timestamp) {
        this.prescription_id = prescription_id;
        this.patient_id = patient_id;
        this.prescription = prescription;
        this.prescription_timestamp = prescription_timestamp;
    }

    public Prescription_History() {
    }

    public int getPrescription_id() {
        return prescription_id;
    }

    public void setPrescription_id(int prescription_id) {
        this.prescription_id = prescription_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public Date getPrescription_timestamp() {
        return prescription_timestamp;
    }

    public void setPrescription_timestamp(Date prescription_timestamp) {
        this.prescription_timestamp = prescription_timestamp;
    }
    
    
}
