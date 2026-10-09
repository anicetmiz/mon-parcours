/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author Utilisateur
 */
public class NomException extends Exception {
    public NomException (){
       super("le champ nom est obligatoire.");
   } 
   
   public NomException(String msg){
       super(msg);
   }
}
