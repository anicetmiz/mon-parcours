**Auteur :** Anicet Mizumbi · Cégep de Granby, Hiver 2025 · Projet individuel

> Mes requêtes SQL Server écrites **sans outil d'IA** pendant mes cours de bases de données, rassemblées dans un seul fichier et classées **du plus simple au plus difficile**. Ce README sert aussi de **plan d'apprentissage** : il explique ce que fait chaque section, pour qu'un débutant puisse apprendre le SQL dans le même ordre.

---

## 📑 Sommaire

1. [Le contenu du dépôt](#1--le-contenu-du-dépôt)
2. [Le plan d'apprentissage](#2--le-plan-dapprentissage)
3. [Lancer les requêtes](#3--lancer-les-requêtes)
4. [Chaque section expliquée](#4--chaque-section-expliquée)
5. [L'ordre d'exécution d'un SELECT](#5--lordre-dexécution-dun-select)
6. [Erreurs fréquentes](#6--erreurs-fréquentes)
7. [Idées pour aller plus loin](#7--idées-pour-aller-plus-loin)
8. [Corrections apportées lors de l'organisation](#8--corrections-apportées-lors-de-lorganisation)

---

## 1. 📁 Le contenu du dépôt

| Fichier | Contenu |
|---|---|
| `requetes-sql-avancees.sql` | Toutes les requêtes, en 8 sections numérotées, avec un commentaire au-dessus de chacune |
| `donnees/EVEMTL.csv` | Le fichier d'événements importé avec `BULK INSERT` dans la section 7 (normalisation) |
| `README.md` | Ce guide |

Le fichier commence par un **sommaire**. Chaque section commence par un bloc de commentaires qui explique la notion travaillée, et chaque requête a une ligne qui dit ce qu'elle fait.

---

## 2. 🗺️ Le plan d'apprentissage

Les sections sont classées par difficulté. Chaque étape utilise ce qu'on a appris dans les précédentes.

| # | Section | Difficulté | Ce qu'on apprend |
|---|---|---|---|
| 1 | Fonctions prédéfinies | ⭐ | Transformer une valeur : arrondir, changer la casse, découper du texte |
| 2 | Agrégation, GROUP BY, HAVING | ⭐⭐ | Compter, additionner et faire des moyennes par groupe |
| 3 | Création et contraintes | ⭐⭐ | Modifier une table et protéger les données (clé étrangère, CHECK, DEFAULT) |
| 4 | INSERT, UPDATE, DELETE | ⭐⭐⭐ | Modifier les données en toute sécurité grâce aux transactions |
| 5 | Sous-requêtes et ensembles | ⭐⭐⭐⭐ | Requêtes imbriquées, ALL, ANY, UNION, auto-jointures, 4 tables jointes |
| 6 | Vues | ⭐⭐⭐ | Enregistrer une requête complexe pour la réutiliser simplement |
| 7 | Normalisation 1FN → 3FN | ⭐⭐⭐⭐⭐ | Transformer un fichier CSV désordonné en tables propres et reliées |
| 8 | Déclencheurs (triggers) | ⭐⭐⭐⭐⭐ | Exécuter du code automatiquement à chaque modification |

---

## 3. ▶️ Lancer les requêtes

### Prérequis

- **SQL Server** (l'édition gratuite *Express* ou *Developer* suffit)
- **SQL Server Management Studio (SSMS)**

### Les bases de données

| Base | Utilisée dans | D'où elle vient |
|---|---|---|
| `GestionVOLS` | Sections 1, 2, 4, 5 | Fournie en classe : vols, avions, passagers, réservations |
| `EMPLOYE` | Sections 2, 3, 5, 8 | Fournie en classe : employés et services |
| `BD_CLIENTS_FOURNISSEURS` | Section 5 | Fournie en classe |
| `EVEMTL` | Section 7 | **Créée par le script** à partir de `donnees/EVEMTL.csv` (fourni) |

> ⚠️ Les scripts de création des bases du cours ne sont pas inclus dans ce dépôt, car ils appartiennent au cégep. Les requêtes montrent **comment** j'ai résolu chaque exercice. Pour les exécuter, il faut avoir ces bases.

### Préparer le fichier CSV (section 7)

La section 7 importe le fichier `EVEMTL.csv` avec `BULK INSERT`. Avant de l'exécuter :

1. Crée le dossier **`C:\SQL-BULKINSERT`** sur ton ordinateur.
2. Copie **`donnees/EVEMTL.csv`** de ce dépôt dans ce dossier, pour obtenir `C:\SQL-BULKINSERT\EVEMTL.csv`.

Tu préfères le laisser ailleurs ? Change le chemin dans la requête 7.0 :

```sql
BULK INSERT EVEMTL
FROM 'C:\SQL-BULKINSERT\EVEMTL.csv'   -- <- remplace par ton chemin
```

> 💡 C'est le **serveur SQL** qui lit le fichier, pas SSMS. Le chemin doit donc exister sur l'ordinateur où SQL Server est installé. Avec SQL Server Express sur ton propre PC, c'est ton ordinateur.

### Exécuter une seule requête

Dans SSMS, **sélectionne** les lignes d'une requête, puis appuie sur **F5**. N'exécute pas tout le fichier d'un coup : certaines sections suppriment et recréent des tables.

---

## 4. 🧠 Chaque section expliquée

### Section 1 — Fonctions prédéfinies ⭐

Les fonctions transforment une valeur **ligne par ligne**.

| Fonction | Rôle | Exemple |
|---|---|---|
| `ROUND(x, 2)` | Arrondir à 2 décimales | `ROUND(12.456, 2)` → `12.46` |
| `FLOOR` / `CEILING` | Arrondir vers le bas / vers le haut | `FLOOR(12.9)` → `12` |
| `CONVERT(INT, x)` / `CAST(x AS VARCHAR)` | Changer le type d'une valeur | `CONVERT(INT, 12.9)` → `12` |
| `UPPER` / `LOWER` | Majuscules / minuscules | `LOWER('BOS')` → `bos` |
| `REPLACE` | Remplacer du texte | `REPLACE('BRU', 'BRU', 'BRUXELLES')` |
| `CONCAT` | Assembler plusieurs valeurs | `CONCAT(912, ' Km/h')` → `912 Km/h` |
| `LEFT` / `SUBSTRING` / `LEN` | Découper du texte | `LEFT('ABCD', 2)` → `AB` |

À retenir : la requête 1.5 combine `LEFT`, `SUBSTRING`, `LEN` et `CAST` pour **insérer une lettre au milieu** d'un numéro.

### Section 2 — Agrégation, GROUP BY et HAVING ⭐⭐

Les fonctions d'agrégation (`COUNT`, `SUM`, `AVG`, `MIN`, `MAX`) calculent **un seul résultat pour un groupe de lignes**.

```sql
SELECT ID_VOL, COUNT(*) AS NOMBRE_RES
FROM RESERVATION
WHERE MODE_PAIE NOT IN ('COMPTANT', 'CHEQUE')   -- 1. filtre les LIGNES
GROUP BY ID_VOL                                  -- 2. forme les groupes
HAVING COUNT(*) IN (1, 4)                        -- 3. filtre les GROUPES
```

| | `WHERE` | `HAVING` |
|---|---|---|
| Filtre… | les lignes | les groupes |
| Quand ? | **avant** le `GROUP BY` | **après** le `GROUP BY` |
| Peut utiliser `COUNT`, `AVG`… ? | ❌ Non | ✅ Oui |

À retenir : la requête 2.9 regroupe sur une **expression `CASE`** (les décennies d'embauche). Le même `CASE` doit alors être répété dans le `GROUP BY`.

### Section 3 — Création et contraintes ⭐⭐

Les contraintes empêchent d'enregistrer des données incorrectes :

| Contrainte | Elle garantit que… |
|---|---|
| `FOREIGN KEY` | la valeur existe dans une autre table (ici, le supérieur d'un employé est lui-même un employé) |
| `CHECK` | la valeur respecte une condition (la commission est entre 0 et 1500) |
| `DEFAULT` | une valeur est mise automatiquement (la date d'entrée = aujourd'hui) |

À retenir : la requête 3.5 **doit échouer**. Elle sert à prouver que la contrainte `CHECK` fonctionne.

### Section 4 — INSERT, UPDATE, DELETE et transactions ⭐⭐⭐

```sql
BEGIN TRANSACTION   -- on commence un essai
    DELETE FROM PASSAGER WHERE NOM = 'BARIL'
ROLLBACK            -- on annule tout : rien n'est enregistré
```

👉 **Règle d'or :** avant un `UPDATE` ou un `DELETE`, exécute d'abord un `SELECT` avec le **même `WHERE`** pour voir quelles lignes seront touchées. Un `DELETE` sans `WHERE` vide toute la table.

À retenir :
- La requête 4.3 utilise un `CASE` dans un `UPDATE`. L'ordre des `WHEN` compte : `'DE LA%'` doit être testé avant `'DE%'`.
- La requête 4.4 met une **majuscule au début de chaque mot** avec `CHARINDEX`, `SUBSTRING`, `UPPER` et `LOWER`.
- La requête 4.5 **génère un identifiant** à partir de la date et d'un nombre aléatoire (`FORMAT`, `RAND`).

### Section 5 — Sous-requêtes, ensembles et auto-jointures ⭐⭐⭐⭐

**Une sous-requête** est une requête placée à l'intérieur d'une autre. Elle s'exécute en premier :

```sql
SELECT * FROM EMP
WHERE SALAIRE < (SELECT AVG(SALAIRE) FROM EMP)   -- les employés sous la moyenne
```

| Opérateur | Signification | Exemple dans le fichier |
|---|---|---|
| `IN` | égal à **une** des valeurs de la liste | 5.3 |
| `> ANY` | plus grand qu'**au moins une** valeur | 5.4 |
| `< ALL` | plus petit que **toutes** les valeurs | 5.5 |
| `UNION` | additionne les lignes de deux requêtes (sans doublons) | 5.11 à 5.15 |

**Une auto-jointure** relie une table **à elle-même**, avec deux alias (`A` et `B`). Par exemple, pour trouver les passagers qui ont le même prénom qu'un autre (5.3).

À retenir :
- 5.3 et 5.4 résolvent chacune le même problème **de deux façons** : avec une sous-requête et avec une auto-jointure.
- 5.10 relie **4 tables** et compare à une moyenne majorée de 10 %.
- Avec `UNION`, le `ORDER BY` se met **une seule fois, à la fin**, et utilise le nom des colonnes de la première requête.

### Section 6 — Vues ⭐⭐⭐

Une vue est une **requête enregistrée sous un nom**. On l'interroge ensuite comme une table, sans réécrire les 3 jointures :

```sql
SELECT * FROM ExempleVUE3 WHERE PEINTRE LIKE '%TOULOUSE%'
```

### Section 7 — Normalisation 1FN → 3FN ⭐⭐⭐⭐⭐

C'est la section la plus complète. On part d'un **fichier CSV** où tout est mélangé dans une seule table (événement, lieu, organisateur, participants), et on le découpe étape par étape :

```
EVEMTL (CSV, tout mélangé)
   │  1FN : une seule information par colonne + clé primaire
   ▼
EVEMTL1FN
   │  2FN : séparer ce qui dépend d'une PARTIE de la clé composée
   ▼
EVENTS2FN  +  PARTICIPANT2FN  +  EVEMTL2FN (table de liaison)
   │  3FN : séparer ce qui dépend d'une autre colonne que la clé
   ▼
EVENTS3FN  +  LIEU3FN  +  ORGANISATEURS3FN  +  vue EVENTS3FN_V
```

| Forme | La règle | Ce qu'on fait dans le script |
|---|---|---|
| **1FN** | Chaque colonne contient **une seule** information | « Emma William » devient `PRENOM` + `NOM` ; on ajoute une clé primaire |
| **2FN** | Rien ne dépend d'une **partie** de la clé composée | Les événements et les participants vont dans deux tables séparées |
| **3FN** | Rien ne dépend d'une colonne **qui n'est pas la clé** | Le téléphone de l'organisateur va dans la table des organisateurs |

Techniques utilisées : `BULK INSERT` (importer un CSV), `SELECT ... INTO` (créer une table à partir d'une requête), `IDENTITY` (numéro automatique), `UPDATE` avec jointure, et une **vue** qui recalcule le nombre de participants au lieu de le stocker.

### Section 8 — Déclencheurs (triggers) ⭐⭐⭐⭐⭐

Un déclencheur s'exécute **automatiquement** après un `INSERT`, un `UPDATE` ou un `DELETE`. Ici, chaque modification d'un employé copie son ancienne version dans la table `HIST_EMP`, ce qui garde un **historique**.

| Opération | Table `inserted` | Table `deleted` |
|---|---|---|
| `INSERT` | les nouvelles lignes | vide |
| `UPDATE` | les lignes **après** | les lignes **avant** |
| `DELETE` | vide | les lignes supprimées |

C'est grâce à ce tableau que la version 1 (8.2) sait si elle traite un `UPDATE` (les deux tables sont remplies) ou un `DELETE` (seule `deleted` l'est).

À retenir : `WHERE 1 = 2` est toujours faux. `SELECT ... INTO ... WHERE 1 = 2` copie donc **la structure** d'une table, sans aucune ligne.

---

## 5. 🔢 L'ordre d'exécution d'un SELECT

SQL n'exécute **pas** une requête dans l'ordre où on l'écrit :

| Ordre d'écriture | Ordre d'exécution |
|---|---|
| 1. `SELECT` | 5 |
| 2. `FROM` / `JOIN` | **1** |
| 3. `WHERE` | 2 |
| 4. `GROUP BY` | 3 |
| 5. `HAVING` | 4 |
| 6. `ORDER BY` | 6 |

C'est pour ça qu'on ne peut pas utiliser un alias du `SELECT` dans un `WHERE` (il n'existe pas encore), mais qu'on peut l'utiliser dans un `ORDER BY` (requête 2.6).

---

## 6. 🐞 Erreurs fréquentes

| Message d'erreur | Cause | Solution |
|---|---|---|
| `Column '...' is invalid in the select list because it is not contained in ... GROUP BY` | Une colonne du `SELECT` n'est ni regroupée ni dans une agrégation | Ajoute-la au `GROUP BY`, ou utilise `AVG(...) OVER ()` (requête 5.1) |
| `An aggregate may not appear in the WHERE clause` | `COUNT` ou `AVG` utilisé dans un `WHERE` | Utilise `HAVING` |
| `Invalid object name '...'` | Faute de frappe dans le nom de la table, ou mauvaise base | Vérifie l'orthographe et le `USE` au-dessus de la requête |
| `The ALTER TABLE statement conflicted with the CHECK constraint` | Des données existantes ne respectent pas la nouvelle contrainte | Corrige les données avant d'ajouter la contrainte |
| `There is already an object named '...'` | `SELECT ... INTO` vers une table qui existe déjà | Mets `DROP TABLE IF EXISTS` juste avant |
| `ORDER BY items must appear in the select list if the statement contains a UNION` | `ORDER BY` sur une expression après un `UNION` | Trie avec l'alias de la colonne (requête 5.15) |
| `Cannot bulk load because the file ... could not be opened` | Le fichier CSV n'est pas au bon endroit, ou SQL Server n'a pas le droit de le lire | Vérifie le chemin (section 3) ; place le fichier dans un dossier simple comme `C:\SQL-BULKINSERT` |
| Les accents s'affichent mal après l'import (`Montr�al`) | Mauvais encodage | Garde `CODEPAGE = 'ACP'` pour ce fichier ; utilise `'65001'` pour un fichier en UTF-8 |
| Une transaction reste ouverte | `BEGIN TRANSACTION` sans `COMMIT` ni `ROLLBACK` | Termine toujours une transaction |

---

## 7. 💡 Idées pour aller plus loin

- [ ] Ajouter une section sur les **droits d'accès** (`GRANT`, `REVOKE`, `DENY`)
- [ ] Apprendre les **procédures stockées** (`CREATE PROCEDURE`) et les **fonctions** personnalisées
- [ ] Explorer les **fonctions de fenêtrage** (`ROW_NUMBER()`, `RANK()`, `SUM() OVER (PARTITION BY ...)`)
- [ ] Remplacer certaines sous-requêtes par des **CTE** (`WITH ... AS`), plus lisibles
- [ ] Créer des **index** et comparer les plans d'exécution dans SSMS

---

## 8. 🔧 Corrections apportées lors de l'organisation

Les requêtes viennent de plusieurs fichiers de cours (labos 4 et 6, exercices, préparation à l'examen). En les regroupant dans un seul fichier avec l'aide de **Claude** (IA d'Anthropic), quelques bugs ont été corrigés, sans ajouter de nouvelles requêtes :

| Section | Correction |
|---|---|
| 3 | Le changement de type de `NO_SUP` est fait **avant** l'ajout de la clé étrangère (sinon il échoue) |
| 4 | Le format `'YY'` de `FORMAT` a été remplacé par `'yy'` (en majuscules, il affichait le texte « YY ») |
| 5 | `AVIONQW` corrigé en `AVION` ; `SELECT *, AVG(SALAIRE)` sans `GROUP BY` remplacé par `AVG(SALAIRE) OVER ()` |
| 5 | `ORDER BY COUNT(*)` après un `UNION` remplacé par l'alias de la colonne ; auto-jointure 5.14 filtrée sur `B.ID` au lieu de `A.ID` |
| 6 | Une phrase d'énoncé sans `--` causait une erreur de syntaxe |
| 7 | La table `ORGANISATEURS3FN` était créée à partir d'une table qui n'existait pas encore ; elle est refaite avec la même méthode que `LIEU3FN` |
| 8 | Ordre de création de `HIST_EMP`, transaction laissée ouverte, et les deux versions des déclencheurs actives en même temps |

La requête 5.11 est restée telle quelle, avec un commentaire « ⚠ À VÉRIFIER ».
