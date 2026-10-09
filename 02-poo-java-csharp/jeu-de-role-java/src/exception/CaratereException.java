/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author Utilisateur
 */
public class CaratereException extends Exception{
    public CaratereException(){
       super("la selection du caractère est obligatoire.Vous devez faire un choix entre Bon et Mechant.");
   } 
   
   public CaratereException(String msg){
       super(msg);
   }
}
