**Auteur :** Anicet Mizumbi · Cégep de Granby, Hiver 2026 · Projet individuel

# 🎮 Bibliothèque de jeux vidéo — Application Android en Kotlin

Une application Android pour gérer sa collection de jeux vidéo : consulter la liste, voir le détail d'un jeu, modifier sa progression et choisir l'ordre de tri. Le tri choisi est **sauvegardé** et retrouvé au prochain lancement.

---

## 📑 Sommaire

1. [Ce que fait l'application](#1--ce-que-fait-lapplication)
2. [Technologies](#2--technologies)
3. [Lancer le projet](#3--lancer-le-projet)
4. [Structure](#4--structure)
5. [L'architecture](#5--larchitecture)
6. [Les concepts expliqués](#6--les-concepts-expliqués)
7. [Idées pour aller plus loin](#7--idées-pour-aller-plus-loin)

---

## 1. 📱 Ce que fait l'application

| Écran | Ce qu'on y fait |
|---|---|
| **Mes jeux** (liste) | Voir les 8 jeux avec leur plateforme, leur statut et leur note ; le tri actif est affiché en haut |
| **Détail** | Changer le statut (Pas commencé, En cours, Terminé), la note (curseur de 1 à 5) et le temps de jeu estimé |
| **Paramètres** | Trier par titre, par statut ou par note ; le choix est enregistré |

## 2. 🧰 Technologies

| Outil | Rôle |
|---|---|
| **Kotlin** | Le langage |
| **Jetpack Compose** + **Material 3** | L'interface, écrite en code (pas de XML) |
| **ViewModel** + **StateFlow** | L'état de l'application, qui survit aux rotations d'écran |
| **Navigation 3** (`NavDisplay`) | La navigation entre les 3 écrans |
| **DataStore Preferences** | La sauvegarde du tri choisi |
| **Android Studio** | L'environnement de développement (API minimale : 26) |

## 3. ▶️ Lancer le projet

1. Ouvre le dossier `bibliotheque-jeux-video` dans **Android Studio**.
2. Attends la fin de la synchronisation **Gradle** (la première fois, elle télécharge les dépendances).
3. Lance l'application sur un **émulateur** (bouton ▶️). La sauvegarde avec DataStore ne fonctionne que sur un émulateur ou un vrai téléphone, pas dans les aperçus `@Preview`.

## 4. 📁 Structure

```
app/src/main/java/com/example/tp_2/
├── MainActivity.kt         ← le point de départ et la navigation (NavDisplay)
├── data/                   ← Jeu (data class), Statut et TypeTri (enum), DataStore
├── viewmodels/
│   └── JeuxViewModel.kt    ← l'état de l'application et ses actions
└── ui/ui/
    ├── screens/            ← EcranListe, EcranDetail, EcranParametres
    ├── components/         ← la barre du haut, partagée par les 3 écrans
    └── theme/              ← couleurs et typographie
```

## 5. 🏗️ L'architecture

```
 Écrans (Compose)  ──── action ────►  JeuxViewModel  ──── écrit ────►  DataStore
       ▲                                  │                         (tri sauvegardé)
       └──────── StateFlow (état) ────────┘
```

Les écrans **n'ont pas d'état à eux** : ils affichent ce que le ViewModel leur donne, et lui envoient des actions (`changerTri`, `changerStatut`, `changerNoteJeu`…).

## 6. 🧠 Les concepts expliqués

### `data class` et `copy`

Un jeu est une `data class` **immuable**. Pour changer sa note, on crée une copie modifiée au lieu de modifier l'objet :

```kotlin
jeu.copy(note = note)
```

### StateFlow : un état qu'on peut observer

```kotlin
private val _jeux = MutableStateFlow(listJeux)          // modifiable, privé
val jeux: StateFlow<List<Jeu>> = _jeux.asStateFlow()    // lecture seule, public
```

L'écran l'observe avec `collectAsStateWithLifecycle()`. À chaque changement, Compose **redessine l'écran tout seul**.

### Navigation 3

Chaque écran est une destination (`NavKey`). On navigue en **ajoutant** une destination à la pile, et on revient en arrière en **retirant** la dernière :

```kotlin
backStack.add(DetailDestination(id))   // aller au détail du jeu
backStack.removeLastOrNull()           // revenir
```

### DataStore : sauvegarder une préférence

```kotlin
context.dataStore.edit { prefs -> prefs[CLE_TRI] = tri.name }
```

Au démarrage, le ViewModel relit cette valeur et trie la liste.

## 7. 💡 Idées pour aller plus loin

- [ ] Le statut des jeux de départ est écrit en texte (`"Terminé"`) alors que le reste du code utilise l'enum `Statut` (`TERMINE`) : utiliser l'enum partout
- [ ] Le champ « temps estimé » plante si on le vide (`toInt()` sur un texte vide) : utiliser `toIntOrNull()`
- [ ] Sauvegarder **toute la liste** des jeux, pas seulement le tri (avec **Room**)
- [ ] Ajouter un écran pour ajouter un nouveau jeu
