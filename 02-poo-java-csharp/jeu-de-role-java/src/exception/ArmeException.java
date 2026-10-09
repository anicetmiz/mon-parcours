/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author Utilisateur
 */
public class ArmeException extends Exception {
    public ArmeException(){
       super("le champ arme est obligatoire.");
   } 
   
   public ArmeException(String msg){
       super(msg);
   }
}
