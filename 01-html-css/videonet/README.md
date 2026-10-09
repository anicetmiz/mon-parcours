**Auteur :** Anicet Mizumbi · Cégep de Granby, Automne 2024 · Projet individuel

# 🎬 videoNet — Page d'accueil d'une plateforme de streaming

Une des toutes premières pages web que j'ai codées : la page d'accueil d'un site de films et de séries, inspirée des grandes plateformes de streaming.

---

## 📑 Sommaire

1. [Ce que fait la page](#1--ce-que-fait-la-page)
2. [Technologies](#2--technologies)
3. [Ouvrir le projet](#3--ouvrir-le-projet)
4. [Structure](#4--structure)
5. [Ce que j'ai appris](#5--ce-que-jai-appris)
6. [Idées pour aller plus loin](#6--idées-pour-aller-plus-loin)

---

## 1. 🖥️ Ce que fait la page

- Trois rangées d'affiches : **Plus regardé**, **Films** et **Séries**.
- Une mise en page construite avec la **grille de Bootstrap** (lignes et colonnes).
- Un **message de bienvenue** (*toast* Bootstrap) qui s'affiche à l'ouverture de la page.

## 2. 🧰 Technologies

| Outil | Rôle |
|---|---|
| **HTML5** | La structure de la page |
| **CSS3** | Les couleurs, les espacements, le fond sombre |
| **Bootstrap 5.1** (CDN) | La grille, les composants et le *toast* de bienvenue |

## 3. ▶️ Ouvrir le projet

Aucune installation : double-clique sur **`index.html`**, la page s'ouvre dans ton navigateur.

> 💡 Bootstrap est chargé depuis Internet (CDN). Sans connexion, la page s'affiche, mais sans la mise en forme de Bootstrap.

## 4. 📁 Structure

```
videonet/
├── index.html   ← toute la page
└── Images/      ← les affiches et les logos
```

## 5. 🧠 Ce que j'ai appris

- **La grille Bootstrap** : une ligne (`row`) se divise en 12 colonnes (`col-*`). On choisit combien de colonnes chaque élément occupe.
- **Les classes utilitaires** : `text-light`, `fs-4`, `p-3`… pour styliser sans écrire de CSS.
- **Les composants Bootstrap qui utilisent du JavaScript**, comme le *toast*, qu'on active avec une ligne de code :

```js
new bootstrap.Toast(document.getElementById('welcomeToast')).show();
```

## 6. 💡 Idées pour aller plus loin

- [ ] Ajouter des attributs `alt` descriptifs à chaque affiche (accessibilité)
- [ ] Donner des noms parlants aux images (`dune.jpg` plutôt que `img12.jpg`)
- [ ] Mettre la page en ligne avec **GitHub Pages**
