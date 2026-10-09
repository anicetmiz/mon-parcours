/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author Utilisateur
 */
public class MaleficesException extends Exception{
    public MaleficesException(){
       super("le malefice doit être superieur à 1.");
   } 
   
   public MaleficesException(String msg){
       super(msg);
   }
}
