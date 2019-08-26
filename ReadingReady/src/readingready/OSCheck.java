/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

/**
 *
 * @author Hannah Saliot
 */
public class OSCheck {
    public static boolean isWindows;
    
    public static void set(){
        isWindows = System.getProperty("os.name").startsWith("Windows");
    }
}
