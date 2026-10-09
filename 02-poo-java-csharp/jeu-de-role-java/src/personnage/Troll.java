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
public class Troll extends Personnage implements PersonnageAnimal {

    private String statut;

    public Troll() {
        statut = "inconnue";
    }

    public Troll(String nom, int force, int intelligence, String statut)
            throws NomException, ForceException, IntelligenceException, StatutException {
        super(nom, force, intelligence);
        
        if (statut.isEmpty()) {
            throw new StatutException();
        }
        this.statut = statut;
    }

    @Override
    public String getStatut() {
        return statut;
    }

    @Override
    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "\nTroll :\n" + super.toString() + "\nil est " + statut + "\n-----------------------------------------------------";
    }
}
