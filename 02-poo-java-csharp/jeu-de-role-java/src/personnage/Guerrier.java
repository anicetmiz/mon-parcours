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
public class Guerrier extends Personnage {

    private String armeType;

    public Guerrier() {
        super();
        armeType = "";
    }

    public Guerrier(String nom, int force, int intelligence, String armeType)
            throws NomException, ForceException, IntelligenceException, ArmeException {
        super(nom, force, intelligence);
        
        if (armeType.isEmpty()) {
            throw new ArmeException();
        }
        this.armeType = armeType;
    }

    public void setArmeType(String armeType) {
        this.armeType = armeType;
    }

    public String getArmeType() {
        return armeType;
    }

    @Override
    public String toString() {
        return "\nGuerrier \n" + super.toString()
                + "\narmeType = " + armeType + "\n-----------------------------------------------------";
    }

}
