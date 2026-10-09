**Équipe :** Anicet Mizumbi, Mamadou Ba et Charles-Antoine Ertug · Cégep de Granby, Automne 2025

> Projet d'équipe. Chacun a d'abord travaillé de son côté, puis nous avons mis notre code en commun : j'ai donc touché à toutes les parties du projet. Le professeur avait fourni un **code de départ** (la structure du projet et une configuration de base) et des **tests unitaires**, qui ne sont pas inclus ici.

# 🚌 API de trajets d'autobus — ASP.NET Core, Entity Framework Core et LINQ

Une **API REST** qui gère des trajets d'autobus et leurs points d'arrêt. Elle reçoit des requêtes HTTP (GET, POST, DELETE), lit et écrit dans une base **SQL Server** avec **Entity Framework Core**, et renvoie des réponses en **JSON**.

---

## 📑 Sommaire

1. [Les routes de l'API](#1--les-routes-de-lapi)
2. [Technologies](#2--technologies)
3. [Lancer le projet](#3--lancer-le-projet)
4. [Tester l'API](#4--tester-lapi)
5. [Structure](#5--structure)
6. [Les concepts expliqués](#6--les-concepts-expliqués)
7. [Idées pour aller plus loin](#7--idées-pour-aller-plus-loin)

---

## 1. 🛣️ Les routes de l'API

| Méthode | Route | Ce qu'elle fait | Réponses possibles |
|---|---|---|---|
| `GET` | `/trajets` | Liste tous les trajets | 200 |
| `GET` | `/trajets/{id}?includeArrets=true` | Un trajet, avec ou sans ses arrêts | 200, 404 |
| `POST` | `/trajets` | Crée un trajet (le nom est obligatoire) | 201, 400 |
| `POST` | `/trajets/{id}/arrets` | Ajoute des arrêts à un trajet | 200, 404 |
| `GET` | `/trajets/{id}/arrets` | Liste les arrêts d'un trajet | 200 |
| `DELETE` | `/trajets/{id}` | Supprime un trajet | 204, 404 |
| `GET` | `/arrets/{id}` | Un point d'arrêt | 200, 404 |
| `DELETE` | `/arrets/{id}` | Supprime un point d'arrêt | 204, 404 |
| `DELETE` | `/trajets` | Vide la base (utile pour les tests) | 200 |

## 2. 🧰 Technologies

| Outil | Rôle |
|---|---|
| **C#** / **ASP.NET Core** (Minimal API) | Le serveur web et les routes |
| **Entity Framework Core** + **SQL Server LocalDB** | La base de données, avec 3 migrations |
| **LINQ** | Les requêtes dans les services |
| **Insomnia** | Le logiciel utilisé pour envoyer des requêtes de test |

## 3. ▶️ Lancer le projet

1. Ouvre **`TP3.WebAPI.Etudiant.sln`** dans Visual Studio 2022.
2. La chaîne de connexion est dans `Étudiant/appsettings.json`. Elle utilise **LocalDB**, qui est installé avec Visual Studio : rien à changer.
3. Crée la base dans la **Console du Gestionnaire de package** :
   ```powershell
   Update-Database
   ```
4. Appuie sur **F5**. L'adresse de l'API s'affiche dans la console (par exemple `https://localhost:7xxx`).

## 4. 🧪 Tester l'API

Deux options, toutes les deux fournies :

- **`Étudiant/TP3.http`** : ouvre-le dans Visual Studio, puis clique sur **Send Request** au-dessus d'une requête.
- **`TP4-EFCore-Insomnia-Collection.yaml`** : dans [Insomnia](https://insomnia.rest/), fais **Import**, puis choisis ce fichier. Toutes les requêtes et leurs variables sont importées.

Exemple : créer un trajet.

```http
POST https://localhost:7xxx/trajets
Content-Type: application/json

{ "nom": "Ligne 1 - Centre-ville" }
```

## 5. 📁 Structure

```
api-trajets-autobus/
├── TP3.WebAPI.Etudiant.sln
├── TP4-EFCore-Insomnia-Collection.yaml   ← requêtes de test pour Insomnia
└── Étudiant/
    ├── Program.cs           ← la configuration et TOUTES les routes
    ├── TrajetDbContext.cs   ← le lien avec SQL Server
    ├── Models/              ← Trajet, Arret, Chauffeur (+ un diagramme UML)
    ├── Services/            ← TrajetsService, ArretsService : la logique et LINQ
    ├── Migrations/          ← l'historique de la base
    └── TP3.http             ← requêtes de test pour Visual Studio
```

`Models/Models.svg` montre le diagramme des classes.

## 6. 🧠 Les concepts expliqués

### REST et les codes HTTP

Une API REST utilise **la méthode HTTP** pour dire quoi faire, et **un code de réponse** pour dire comment ça s'est passé :

| Code | Signification | Quand l'API le renvoie |
|---|---|---|
| `200 OK` | Tout va bien | Lecture réussie |
| `201 Created` | Ressource créée | Après un `POST /trajets` |
| `204 No Content` | Réussi, rien à renvoyer | Après une suppression |
| `400 Bad Request` | La requête est mal formée | Trajet sans nom |
| `404 Not Found` | La ressource n'existe pas | Mauvais identifiant |

### Minimal API

Chaque route est une ligne `app.MapGet`, `app.MapPost` ou `app.MapDelete` dans `Program.cs` :

```csharp
app.MapGet("/trajets/{idTrajet:int}", (int idTrajet, bool? includeArrets, TrajetsService service) =>
{
    var unTrajet = service.GetTrajetById(idTrajet, includeArrets);
    return unTrajet != null ? Results.Ok(unTrajet) : Results.NotFound();
});
```

- `{idTrajet:int}` : un paramètre de route, obligatoirement un nombre.
- `bool? includeArrets` : un paramètre facultatif de l'URL (`?includeArrets=true`).
- `TrajetsService service` : fourni automatiquement par l'**injection de dépendances**.

### L'injection de dépendances

Les services sont enregistrés **une seule fois** au démarrage, puis ASP.NET Core les donne à chaque route qui en a besoin :

```csharp
builder.Services.AddScoped<TrajetsService>();
```

### Éviter les boucles infinies en JSON

Un trajet contient des arrêts, et chaque arrêt pointe vers son trajet. Pour que la conversion en JSON ne tourne pas en rond, le projet utilise :

```csharp
options.SerializerOptions.ReferenceHandler = ReferenceHandler.IgnoreCycles;
```

### Comparer des objets : `IEqualityComparer`

`ArretEqualityComparer` explique à C# quand deux arrêts sont « égaux » (mêmes coordonnées), pour éviter d'ajouter deux fois le même arrêt à un trajet.

## 7. 💡 Idées pour aller plus loin

- [ ] Ajouter **Swagger** pour avoir une page de documentation interactive de l'API
- [ ] Ajouter les routes pour gérer les **chauffeurs** (le modèle existe déjà)
- [ ] Rendre les services **asynchrones** (`ToListAsync`, `SaveChangesAsync`)
- [ ] Protéger la route `DELETE /trajets` : en production, vider toute la base ne devrait pas être possible
