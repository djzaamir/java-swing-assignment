/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Support;

import java.sql.Date;

/**
 *
 * @author djzaamir
 */
public class Support {
    //This class contains supporting methods and other resources for the overall project
    public static long getTimeStamp(){
       return new java.util.Date().getTime();
    }
}
