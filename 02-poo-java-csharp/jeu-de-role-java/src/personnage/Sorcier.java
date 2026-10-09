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
public class Sorcier extends Magicien {

    private int nbMalefices;

    public Sorcier() {
        super();
        nbMalefices = 0;
    }

    public Sorcier(String nom, int force, int intelligence, int energieMagique, String caractere, int nbMalefices)
            throws NomException, ForceException, IntelligenceException, CaratereException, EnergieException, MaleficesException {
        super(nom, force, intelligence, energieMagique, caractere);
        
        if (energieMagique < 1) {
            throw new EnergieException();
        }
        if (caractere.isEmpty()) {
            throw new CaratereException();
        }
        if(nbMalefices < 1)
            throw new MaleficesException();
        this.nbMalefices = nbMalefices;
    }

    public int getNbMalefices() {
        return nbMalefices;
    }

    public void setNbMalefices(int nbMalefices) {
        if (nbMalefices < 1) {
            System.out.println("nombre malefice incorrecte je met a 1");
            this.nbMalefices = 1;
        } else {
            this.nbMalefices = nbMalefices;
        }
    }

    @Override
    public String toString() {
        return "\nSorcier : \n" + super.toString()
                + "\nnombre malefices : " + nbMalefices + "\n-----------------------------------------------------";
    }

}
