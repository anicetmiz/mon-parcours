/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personnage;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Utilisateur
 */
public class Partie {

    private int maxPersonnage;
   // private Personnage[] personnages;
    private List<Personnage> personnages;
    private int nbPersonnages;

    public Partie() {
        maxPersonnage = 1;
        //Personnage[] personnages = new Personnage[20];
        this.personnages=new ArrayList<>(20);
        nbPersonnages = 0;
    }

    public Partie(int maxPersonnage) {
        this.maxPersonnage = maxPersonnage;
       // this.personnages = new Personnage[20];
       this.personnages = new ArrayList<>(20);
    }

    public int getNbPersonnages() {
        return personnages.size();
    }

    public boolean ajouterAPartie(Personnage unPersonnage) {
      if (!personnages.contains(unPersonnage)) {
            personnages.add(unPersonnage);
            System.out.println(unPersonnage.getNom()+"est ajouter");
            return true;
        } else {
            return false;
        }
    }

    public boolean supprimerDePartie(Personnage unPersonnage) {
       if (personnages.contains(unPersonnage)) {
            personnages.remove(unPersonnage);
            System.out.println(unPersonnage.getNom()+"est supprimer");
            return true;
        } else {
            return false;
        }
    }

    public String listePersonnage() {
        String sortie = "";
        sortie += "*****************************************************\n Ces personnages sont dans la partie ";
        /*for (int i = 0; i < personnages.length; i++) {
            System.out.println(personnages[i].toString());
        }*/

        for (Personnage perso : personnages) {
            
                sortie += perso;
            
        }
        sortie += "\nIl ya " + personnages.size() + " dans la partie\n";
        return sortie;
    }

}
