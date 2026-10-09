/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personnage;

import exception.*;

/**
 *
 * @author Utilisateur
 */
public class Centaure extends Personnage implements PersonnageAnimal{
    
    private String statut;
    
    public Centaure(){
        statut = "inconnue";
    }
    
    public Centaure(String nom, int force, int intelligence,String statut) 
        throws IntelligenceException, NomException, ForceException, StatutException{
        super(nom, force, intelligence);
        if(statut.isEmpty())
            throw new StatutException();
    
        this.statut= statut;
    }
    
    @Override
    public String getStatut(){
        return statut;
    }
    
    @Override
    public void setStatut(String statut){
        this.statut = statut;
    }
    
    @Override
    public String toString(){
      return "\nCentaure :\n"+super.toString()+"il est "+statut+"\n-----------------------------------------------------";
    }
}
