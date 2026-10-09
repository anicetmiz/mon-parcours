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
public abstract class Personnage {

    private String nom;
    private int force;
    private int intelligence;

    public Personnage() {
        nom = "inconnue";
        force = 1;
        intelligence = 1;
    }

    public Personnage(String nom, int force, int intelligence)
        throws NomException, ForceException, IntelligenceException {
        if (nom.isEmpty()) {
            throw new NomException();
        }
        if (force < 0 || force > 20) {
            throw new ForceException();
        }
        if (intelligence < 0 || force > 20) {
            throw new IntelligenceException();
        }

        this.nom = nom;
        this.force = force;
        this.intelligence = intelligence;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getForce() {
        return force;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getIntelligente() {
        return intelligence;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Personnage perso = (Personnage) obj;

        return (this.getNom().equalsIgnoreCase(perso.getNom())
                && this.getForce() == (perso.getForce())
                && this.getIntelligente() == (perso.getIntelligente()));
    }

    @Override
    public String toString() {
        return "nom personnage : " + nom + "\nforce du personnage : " + force
                + "\nintelligence du personnage : " + intelligence;
    }

}
