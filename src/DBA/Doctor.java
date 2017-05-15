/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBA;

/**
 *
 * @author djzaamir
 */
public class Doctor {
    private int doctor_id;
    private String doctor_specialization;
    private String doctor_name;

    public Doctor() {
    }
    public Doctor(int doctor_id, String doctor_specialization, String doctor_name) {
        this.doctor_id = doctor_id;
        this.doctor_specialization = doctor_specialization;
        this.doctor_name = doctor_name;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_specialization() {
        return doctor_specialization;
    }

    public void setDoctor_specialization_id(String doctor_specialization) {
        this.doctor_specialization = doctor_specialization;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    
}
