**Auteur :** Anicet Mizumbi · Cégep de Granby, Automne 2025

> Travail réalisé en équipe de deux. Chacun a codé sa propre version, puis nous avons retenu la mienne : **le code présenté ici est entièrement écrit par moi**.

# 📚 Gestion de bibliothèque — C#, Entity Framework Core et LINQ

Une application **console** qui gère une bibliothèque : clients, auteurs, livres et emprunts. Les données sont enregistrées dans une base **SQL Server** grâce à **Entity Framework Core**, et interrogées avec **LINQ**.

---

## 📑 Sommaire

1. [Ce que fait l'application](#1--ce-que-fait-lapplication)
2. [Technologies](#2--technologies)
3. [Lancer le projet](#3--lancer-le-projet)
4. [Structure](#4--structure)
5. [Le modèle de données](#5--le-modèle-de-données)
6. [Les concepts expliqués](#6--les-concepts-expliqués)
7. [Idées pour aller plus loin](#7--idées-pour-aller-plus-loin)

---

## 1. 🖥️ Ce que fait l'application

Un menu propose 11 actions :

| # | Action | # | Action |
|---|---|---|---|
| 1 | Ajouter un client | 7 | Retourner un livre |
| 2 | Modifier un client | 8 | Afficher les livres populaires |
| 3 | Ajouter un auteur | 9 | Afficher les livres disponibles |
| 4 | Ajouter un livre | 10 | Afficher les auteurs avec leurs livres |
| 5 | Supprimer un livre | 11 | Afficher les emprunts en retard |
| 6 | Emprunter un livre | 0 | Quitter |

## 2. 🧰 Technologies

| Outil | Rôle |
|---|---|
| **C#** / **.NET 8** | Le langage et la plateforme |
| **Entity Framework Core 9** | L'ORM : transforme les classes C# en tables SQL |
| **SQL Server** | La base de données |
| **LINQ** | Les requêtes en C# (`Where`, `Include`, `Any`, `OrderBy`…) |

## 3. ▶️ Lancer le projet

1. Ouvre `Infrastructure/DbCont.cs` et remplace `ANICETPC\SQLEXPRESS` par le nom de **ton** serveur (par exemple `.\SQLEXPRESS`).
2. Crée la base dans la **Console du Gestionnaire de package** de Visual Studio :
   ```powershell
   Update-Database
   ```
3. *(Facultatif)* Exécute `donnees-de-test.sql` dans SSMS pour ajouter des auteurs, des clients et des livres.
4. Ouvre `tp2.sln` et appuie sur **F5**.

## 4. 📁 Structure

```
gestion-bibliotheque-csharp/
├── Program.cs              ← le menu et les saisies au clavier
├── Models/                 ← Auteur, Client, Livre, Emprunt
├── Services/               ← la logique et les requêtes LINQ (un service par entité)
├── Infrastructure/
│   └── DbCont.cs           ← le DbContext (lien avec SQL Server)
├── Migrations/             ← générées par Entity Framework
└── donnees-de-test.sql     ← données pour essayer l'application
```

## 5. 🗃️ Le modèle de données

```
Auteur 1 ──── * Livre 1 ──── * Emprunt * ──── 1 Client
```

- Un **auteur** écrit plusieurs livres.
- Un **livre** peut être emprunté plusieurs fois.
- Un **emprunt** relie un client et un livre, avec une date d'échéance et une date de retour.

Des **index uniques** empêchent les doublons : deux livres ne peuvent pas avoir le même titre, ni deux auteurs le même prénom et le même nom.

```csharp
[Index(nameof(Titre), IsUnique = true)]
internal class Livre { ... }
```

## 6. 🧠 Les concepts expliqués

### Séparer les responsabilités

`Program.cs` s'occupe **seulement** de l'affichage et des saisies. Toute la logique est dans les **services** (`LivreService`, `EmpruntService`…), et les données dans les **modèles**.

### Entity Framework Core et `using`

Chaque service ouvre un `DbContext` le temps d'une opération, puis le ferme automatiquement :

```csharp
using (DbCont dbContext = new DbCont())
{
    return dbContext.Auteurs.Include(a => a.Livres).ToList();
}
```

> 💡 `Include` charge aussi les livres de chaque auteur (une jointure). Sans lui, `auteur.Livres` serait vide.

### LINQ

Les requêtes s'écrivent en C# : `Any` (« existe-t-il ? »), `Where` (filtrer), `OrderByDescending` (trier), `Count` (compter)…

### Valider les saisies

`int.TryParse` vérifie que le choix du menu est bien un nombre, et un `try/catch` empêche le programme de planter en cas d'erreur.

## 7. 💡 Idées pour aller plus loin

- [ ] Le choix **0 (Quitter)** met `navigation` à `true` au lieu de `false` : le programme ne se ferme pas
- [ ] `ClientService.Modifier` ajoute un nouveau client au lieu de modifier celui qui existe
- [ ] Déplacer la chaîne de connexion dans un fichier de configuration
- [ ] Remplacer `DateTime.Parse` par `DateTime.TryParse` pour éviter un plantage si la date est mal écrite
