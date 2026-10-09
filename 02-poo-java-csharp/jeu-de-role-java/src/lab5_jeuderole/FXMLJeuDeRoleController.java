/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package lab5_jeuderole;

import exception.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import personnage.*;

/**
 *
 * @author Utilisateur
 */
public class FXMLJeuDeRoleController implements Initializable {

    @FXML
    private Button btnAjouter;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtForce;
    @FXML
    private TextField txtIntelligence;
    @FXML
    private TextField txtEnergie;
    @FXML
    private TextField txtArme;
    @FXML
    private TextField txtNbMal;
    @FXML
    private ToggleGroup rdStatut;
    @FXML
    private ToggleGroup rdCaractere;
    @FXML
    private Label lbErreur;
    @FXML
    private TextArea txtArea;
    @FXML
    private ComboBox<String> cbType;
    @FXML
    private RadioButton rdAmi;
    @FXML
    private RadioButton rdEnnemi;
    @FXML
    private RadioButton rdBon;
    @FXML
    private RadioButton rdMechant;

    private Partie maPartie;
    private String typePersonne;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbType.getItems().addAll("Guerrier",
                "Troll",
                "Centaure",
                "LoupGarou",
                "Sorcier",
                "Druide");
        desactivate();
        supprime();
        maPartie = new Partie(10);
    }

    public void supprime() {
        txtNom.setText("");
        txtForce.setText("");
        txtIntelligence.setText("");
        cbType.setValue("");
        lbErreur.setText("");
    }

    public void desactivate() {

        txtEnergie.setDisable(true);
        txtEnergie.setText("");
        txtArme.setDisable(true);
        txtArme.setText("");
        txtNbMal.setDisable(true);
        txtNbMal.setText("");
        rdAmi.setDisable(true);
        rdEnnemi.setDisable(true);
        rdBon.setDisable(true);
        rdMechant.setDisable(true);
        rdCaractere.selectToggle(null);
        rdStatut.selectToggle(null);
    }

    @FXML
    private void ajouter(ActionEvent event) {
        lbErreur.setText("");
        Personnage p = null;
        String nom, statut, caractere, arme;
        int force, intelligence, energieMagique, nbMalefices;
        typePersonne = cbType.getValue();

        try {
            nom = txtNom.getText();

            //force
            try {
                force = Integer.parseInt(txtForce.getText());
            } catch (NumberFormatException e) {
                throw new ForceException();
            }

            //intelligence
            try {
                intelligence = Integer.parseInt(txtIntelligence.getText());
            } catch (NumberFormatException e) {
                throw new IntelligenceException();
            }
            switch (typePersonne) {
                case "Troll":
                    if (rdStatut.getSelectedToggle() == null) {
                        throw new StatutException();
                    }
                    statut = ((RadioButton) (rdStatut.getSelectedToggle())).getText();
                    p = new Troll(nom, force, intelligence, statut);
                    break;
                case "centaure":
                    if (rdStatut.getSelectedToggle() == null) {
                        throw new StatutException();
                    }
                    statut = ((RadioButton) (rdStatut.getSelectedToggle())).getText();
                    p = new Centaure(nom, force, intelligence, statut);
                    break;
                case "Druide":
                    try {
                        energieMagique = Integer.parseInt(txtEnergie.getText());
                    } catch (NumberFormatException e) {
                        throw new EnergieException();
                    }

                    caractere = ((RadioButton) (rdCaractere.getSelectedToggle())).getText();

                    p = new Druide(nom, force, intelligence, energieMagique, caractere);
                    break;
                case "Guerrier":
                   // pas de parse sur des nombres donc pas besoin de lancer nombre formate try {
                        arme = txtArme.getText();
                   // } catch (NumberFormatException e) {
//                        throw new ArmeException();
//                    }

                    p = new Guerrier(nom, force, intelligence, arme);
                    break;
                case "LoupGarou":
                    if (rdCaractere.getSelectedToggle() == null) {
                        throw new CaratereException();
                    }
                    if (rdStatut.getSelectedToggle() == null) {
                        throw new StatutException();
                    }

                    statut = ((RadioButton) (rdStatut.getSelectedToggle())).getText();
                    caractere = ((RadioButton) (rdCaractere.getSelectedToggle())).getText();
                    p = new LoupGarou(nom, force, intelligence, statut, caractere);
                    break;
                case "Sorcier":
                    if (rdCaractere.getSelectedToggle() == null) {
                        throw new CaratereException();
                    }
                    try {
                        energieMagique = Integer.parseInt(txtEnergie.getText());
                    } catch (NumberFormatException e) {
                        throw new EnergieException();
                    }
                    try {
                        nbMalefices = Integer.parseInt(txtNbMal.getText());
                    } catch (NumberFormatException e) {
                        throw new MaleficesException();
                    }

                    caractere = ((RadioButton) (rdCaractere.getSelectedToggle())).getText();

                    p = new Sorcier(nom, force, intelligence, energieMagique, caractere, nbMalefices);
                    break;
            }
            if (p != null) {
                maPartie.ajouterAPartie(p);
                desactivate();
                supprime();
                txtArea.clear();
                txtArea.appendText(maPartie.listePersonnage());

            }

        } catch (Exception e) {
            lbErreur.setText(e.getMessage());
        }

    }

    @FXML
    private void actionType(ActionEvent event) {
        typePersonne = cbType.getValue();
        switch (typePersonne) {
            case "Troll":
                desactivate();
                rdAmi.setDisable(false);
                rdEnnemi.setDisable(false);
                break;
            case "centaure":
                desactivate();
                rdAmi.setDisable(false);
                rdEnnemi.setDisable(false);
                break;
            case "Druide":
                desactivate();
                txtEnergie.setDisable(false);
                rdBon.setDisable(false);
                rdMechant.setDisable(false);
                break;
            case "Guerrier":
                desactivate();
                txtArme.setDisable(false);
                break;
            case "LoupGarou":
                desactivate();
                rdAmi.setDisable(false);
                rdEnnemi.setDisable(false);
                rdBon.setDisable(false);
                rdMechant.setDisable(false);
                break;
            case "Sorcier":
                desactivate();
                rdBon.setDisable(false);
                rdMechant.setDisable(false);
                break;
        }
    }

}
