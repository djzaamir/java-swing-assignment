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
public class Disease {
    
    //vars section
    private String disease_name;
    private String disease_description;
    //end of vars

    public Disease(String disease_name, String disease_description) {
        this.disease_name = disease_name;
        this.disease_description = disease_description;
    }

    public Disease() {
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public String getDisease_description() {
        return disease_description;
    }

    public void setDisease_description(String disease_description) {
        this.disease_description = disease_description;
    }
    
    
    
    
    
}
