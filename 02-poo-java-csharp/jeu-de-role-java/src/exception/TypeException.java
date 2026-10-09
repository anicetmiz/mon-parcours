/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author Utilisateur
 */
public class TypeException extends Exception{
    public TypeException(){
       super("Le champ Type est obligatoire. choisissez un type.");
   } 
   
   public TypeException (String msg){
       super(msg);
   }
}
