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
public class Disease_History {
 private int history_id;
 private int patient_id;
 private String disease_history;
 private Date history_timestamp;
 
 public Disease_History(int history_id, int patient_id, String disease_history, Date history_timestamp) {
        this.history_id = history_id;
        this.patient_id = patient_id;
        this.disease_history = disease_history;
        this.history_timestamp = history_timestamp;
    }

    public Disease_History() {
    }

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getDisease_history() {
        return disease_history;
    }

    public void setDisease_history(String disease_history) {
        this.disease_history = disease_history;
    }

    public Date getHistory_timestamp() {
        return history_timestamp;
    }

    public void setHistory_timestamp(Date history_timestamp) {
        this.history_timestamp = history_timestamp;
    }
    
 }
