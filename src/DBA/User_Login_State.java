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
public class User_Login_State {
    
    private int user_id;
    private boolean isValid;
    private int user_level;
    
    //constructer
    public User_Login_State (){
        this.isValid = false;
    }
    
    
    
    //getters and setters
    public int getUserId(){
        return this.user_id;
    }
    public boolean isValid(){
        return this.isValid;
    }
    public int getUserLevel(){
        return this.user_level;
    }
    
    public void setUserId(int id){
        this.user_id  = id;
    }
    public void setIsValid(boolean state){
        this.isValid = state;
    }
    public void setUserLevel(int level){
        this.user_level =  level;
    }
    
}
