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
public class LoupGarou extends Personnage implements PersonnageHumain, PersonnageAnimal {

    private String statut;
    private String caractere;

    public LoupGarou() {
        statut = "inconnue";
        caractere = "inconnue";
    }

    public LoupGarou(String nom, int force, int intelligence, String statut, String caractere)
            throws NomException, ForceException, IntelligenceException, CaratereException, StatutException {
        super(nom, force, intelligence);

        if (caractere.isEmpty()) {
            throw new CaratereException();
        }
        if (statut.isEmpty()) {
            throw new StatutException();
        }
        this.statut = statut;
        this.caractere = caractere;
    }

    @Override
    public String getCaractere() {
        return caractere;
    }

    @Override
    public void setCaractere(String caractere) {
        this.caractere = caractere;
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
        return "\nloup-Garou: " + super.toString() + "\n est " + statut + " et " + caractere;
    }

}
