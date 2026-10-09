/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package personnage;

import exception.*;

/**
 *
 * @author Utilisateur
 */
public class TestPartie {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
            throws NomException, ForceException, IntelligenceException, ArmeException, CaratereException, EnergieException, StatutException, MaleficesException {
        Guerrier gue1 = new Guerrier("Ajax", 17, 9, "lance");
        Guerrier gue2 = new Guerrier("Thor", 18, 8, "marteau");

        //Les 4 arguments dans les cas où il y a de l'énergie magique.
        Sorcier sor1 = new Sorcier("Merlin", 10, 18, 100, PersonnageHumain.MECHANT, 5);
        Sorcier sor2 = new Sorcier("Gandalf", 9, 19, 200, PersonnageHumain.BON, 10);
        Druide dru = new Druide("Larry", 11, 15, 100, PersonnageHumain.MECHANT);

        Centaure cen = new Centaure("Curly", 19, 11, PersonnageAnimal.AMI);
        Troll tro = new Troll("Moe", 19, 6, PersonnageAnimal.ENNEMI);
        LoupGarou lou = new LoupGarou("Harry", 18, 11, PersonnageHumain.BON, PersonnageAnimal.ENNEMI);

        Partie g = new Partie(10);

        g.ajouterAPartie(gue1);
        g.ajouterAPartie(gue2);
        g.ajouterAPartie(sor1);
        g.ajouterAPartie(sor2);
        g.ajouterAPartie(dru);
        g.ajouterAPartie(cen);
        g.ajouterAPartie(tro);
        g.ajouterAPartie(lou);

        g.listePersonnage();

        g.supprimerDePartie(sor2);
        g.supprimerDePartie(gue1);
        g.supprimerDePartie(cen);
        g.supprimerDePartie(tro);

//        System.out.println("Les personnages suivants sont morts : "
//                + sor2.getNom() + ", "
//                + gue1.getNom() + ", "
//                + + ", "
//                + tro.getNom() + "\n");

        g.listePersonnage();

        System.out.println("On rajoute le troll Moe : ");
        g.ajouterAPartie(tro);
        g.listePersonnage();

        System.out.println("On essaye de rajouter le troll Moe une seconde fois : ");
        g.ajouterAPartie(tro);
        g.listePersonnage();
        
        System.out.println();

        System.out.println("Le caractère de Merlin est " + sor1.getCaractere());
        lou.setStatut(PersonnageAnimal.AMI);
        System.out.println("Harry est un " + lou.getStatut());
        System.out.println("\nPersonnages = " + g.getNbPersonnages());

    }

}
