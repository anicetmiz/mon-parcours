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
public abstract class Magicien extends Personnage implements PersonnageHumain {
    private int energieMagique;
    private String caractere;

    public Magicien() {
        super();
        energieMagique = 1;
        caractere = "inconnue";
    }

    public Magicien(String nom, int force, int intelligence,int energieMagique,String caractere)
        throws NomException, ForceException, IntelligenceException, CaratereException, EnergieException {
        super(nom, force, intelligence);
       
        if(energieMagique<1)
            throw new EnergieException();
        if(caractere.isEmpty())
            throw new CaratereException();
        this.energieMagique = energieMagique;
        this.caractere = caractere;
    }
    

    public int getEnergieMagique() {
        return energieMagique;
    }
    
    public void setEnergieMagique(int energiMagique){
        if(energieMagique < 1){
            System.out.println("energie trop bas je met a 1");
            this.energieMagique=1;
        } else 
        this.energieMagique = energiMagique;
    }
    
    @Override
    public String getCaractere(){
        return caractere;
    }
    
    @Override
    public void setCaractere(String caractere){
        this.caractere = caractere;
    }
    
    @Override
    public String toString(){
       return " qui est un Magicien :\n "+super.toString()+"\nCaractere : "+caractere
               + "\nenergie magique : "+energieMagique;
    }
    
    
    
    
}
