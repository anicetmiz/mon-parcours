/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author Utilisateur
 */
public class EnergieException extends Exception {
    public EnergieException(){
       super("L'energie doit être supérieure à 1");
   } 
   
   public EnergieException(String msg){
       super(msg);
   }
}
