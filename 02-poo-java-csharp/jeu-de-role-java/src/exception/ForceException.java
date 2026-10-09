/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author Utilisateur
 */
public class ForceException extends Exception {
    public ForceException(){
       super(" La force doit être entre 1 et 20 inclusivement.");
   } 
   
   public ForceException(String msg){
       super(msg);
   }
}
