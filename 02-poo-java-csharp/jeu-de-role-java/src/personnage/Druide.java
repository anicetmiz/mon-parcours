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
public class Druide extends Magicien {

    public Druide(String nom, int force, int intelligence, int energieMagique, String caractere)
            throws NomException, ForceException, IntelligenceException, CaratereException, EnergieException {
        super(nom, force, intelligence, energieMagique, caractere);

        if (caractere.isEmpty()) {
            throw new CaratereException();
        }
    }

    @Override
    public String toString() {
        return "\nDuide : \n" + super.toString() + "\n-----------------------------------------------------";
    }

}
