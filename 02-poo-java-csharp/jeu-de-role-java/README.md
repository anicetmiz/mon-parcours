**Auteur :** Anicet Mizumbi · Cégep de Granby, Hiver 2025 · Projet individuel

# ⚔️ Jeu de rôle — Programmation orientée objet en Java

Un petit jeu de rôle où l'on crée des personnages (guerrier, magicien, loup-garou…) et où on les ajoute à une partie. Le but du projet est de pratiquer les grands principes de la **programmation orientée objet (POO)**, avec une interface graphique **JavaFX**.

---

## 📑 Sommaire

1. [Technologies](#1--technologies)
2. [Lancer le projet](#2--lancer-le-projet)
3. [Structure](#3--structure)
4. [La hiérarchie des classes](#4--la-hiérarchie-des-classes)
5. [Les concepts POO expliqués](#5--les-concepts-poo-expliqués)
6. [Idées pour aller plus loin](#6--idées-pour-aller-plus-loin)

---

## 1. 🧰 Technologies

| Outil | Rôle |
|---|---|
| **Java** | Le langage |
| **JavaFX** + **FXML** | L'interface graphique |
| **NetBeans** | L'environnement de développement (fichiers `build.xml` et `nbproject/`) |

## 2. ▶️ Lancer le projet

1. Installe **Apache NetBeans** et un **JDK** compatible avec JavaFX.
2. Dans NetBeans : **File → Open Project**, puis choisis le dossier `jeu-de-role-java`.
3. Appuie sur **F6** (Run).

## 3. 📁 Structure

```
jeu-de-role-java/
└── src/
    ├── personnage/      ← les classes du jeu (Personnage, Guerrier, Magicien, Partie…)
    ├── exception/       ← 9 exceptions personnalisées (NomException, ForceException…)
    └── lab5_jeuderole/  ← l'interface JavaFX (FXML + contrôleur)
```

## 4. 🌳 La hiérarchie des classes

```
                      Personnage  (classe abstraite)
        ┌──────────┬───────┴──────┬──────────┬──────────┐
     Guerrier   Magicien       Centaure    Troll     LoupGarou
                  │                │          │       │      │
             ┌────┴────┐           └─ PersonnageAnimal ┘      │
           Druide   Sorcier          (interface)              │
                                                              │
   Magicien et LoupGarou ──────────► PersonnageHumain (interface)
```

- **Héritage** : `Druide` et `Sorcier` héritent de `Magicien`, qui hérite de `Personnage`.
- **Interfaces** : `LoupGarou` implémente **deux** interfaces, car il est à la fois humain et animal.

## 5. 🧠 Les concepts POO expliqués

### Classe abstraite

`Personnage` est `abstract` : on ne peut pas créer « un personnage » tout court, seulement un guerrier, un magicien… Elle regroupe ce que tous les personnages ont en commun (nom, force, intelligence).

### Héritage et `super`

```java
public class Guerrier extends Personnage {
    public Guerrier(String nom, int force, int intelligence, String armeType) throws ... {
        super(nom, force, intelligence); // appelle le constructeur de Personnage
        this.armeType = armeType;
    }
}
```

### Interfaces

Une interface est un **contrat** : la classe qui l'implémente promet d'avoir certaines méthodes.

```java
public interface PersonnageHumain {
    String BON = "bon";
    String MECHANT = "mechant";
    String getCaractere();
    void setCaractere(String caractere);
}
```

### Encapsulation

Les attributs sont `private`. On y accède seulement par des **getters et setters**, qui peuvent vérifier les valeurs.

### Exceptions personnalisées

Chaque règle du jeu a sa propre exception. Le constructeur la lance si une valeur est invalide :

```java
if (force < 0 || force > 20) {
    throw new ForceException(); // « La force doit être entre 1 et 20 inclusivement. »
}
```

### Polymorphisme et collections

La classe `Partie` garde tous les personnages dans **une seule** `ArrayList<Personnage>`, peu importe leur type réel. Chaque objet utilise ensuite sa propre version de `toString()`.

## 6. 💡 Idées pour aller plus loin

- [ ] Dans `Personnage`, la validation de l'intelligence teste `force > 20` au lieu de `intelligence > 20`
- [ ] Ajouter des tests unitaires **JUnit** pour chaque exception
- [ ] Ajouter une méthode `attaquer()` redéfinie différemment dans chaque classe (polymorphisme)
