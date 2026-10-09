/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author Utilisateur
 */
public class IntelligenceException extends Exception {
    public IntelligenceException(){
       super("L'intelligence doit être entre 1 et 20 inclusivement.");
   } 
   
   public IntelligenceException(String msg){
       super(msg);
   }
}
