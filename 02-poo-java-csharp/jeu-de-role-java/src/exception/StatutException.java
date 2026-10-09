/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author Utilisateur
 */
public class StatutException extends Exception{
    public StatutException(){
       super("la selection du statut est obligatoire.Vous devez faire un choix entre Ami et Ennemi.");
   } 
   
   public StatutException(String msg){
       super(msg);
   }
}
