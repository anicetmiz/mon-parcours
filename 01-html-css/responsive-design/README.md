**Auteur :** Anicet Mizumbi · Cégep de Granby, Automne 2024 · Projet individuel

# 📱 Responsive Design — Une page qui s'adapte à tous les écrans

Une page web en **HTML et CSS pur**, sans framework, qui change de mise en page selon la taille de l'écran : téléphone, tablette ou ordinateur.

---

## 📑 Sommaire

1. [Technologies](#1--technologies)
2. [Ouvrir le projet](#2--ouvrir-le-projet)
3. [Structure](#3--structure)
4. [Ce que j'ai appris](#4--ce-que-jai-appris)
5. [Idées pour aller plus loin](#5--idées-pour-aller-plus-loin)

---

## 1. 🧰 Technologies

| Outil | Rôle |
|---|---|
| **HTML5** | La structure de la page |
| **CSS3** | Toute la mise en page, avec **Flexbox** et des **media queries** |

Aucune librairie : tout le style est écrit à la main dans `STYLES/ResponsiveD_Anicet.css`.

## 2. ▶️ Ouvrir le projet

Double-clique sur **`Index.html`**. Pour voir l'effet *responsive*, rétrécis la fenêtre du navigateur, ou appuie sur **F12** puis sur l'icône 📱 pour simuler un téléphone.

## 3. 📁 Structure

```
responsive-design/
├── Index.html
├── IMAGES/
└── STYLES/
    └── ResponsiveD_Anicet.css
```

## 4. 🧠 Ce que j'ai appris

### Flexbox

(Exemple simplifié.) `display: flex` aligne des éléments sur une ligne (ou une colonne) et répartit l'espace entre eux :

```css
.conteneur {
  display: flex;
  justify-content: space-between; /* espace entre les éléments */
  flex-wrap: wrap;                /* passe à la ligne s'il manque de place */
}
```

### Les media queries

Une *media query* applique des règles CSS **seulement** pour certaines largeurs d'écran. La page en utilise trois :

| Media query | Écran visé |
|---|---|
| `@media screen and (max-width: 680px)` | Téléphone |
| `@media screen and (min-width: 681px) and (max-width: 1279px)` | Tablette |
| `@media screen and (min-width: 1280px)` | Ordinateur |

Chaque bloc redéfinit la taille, les couleurs et la disposition Flexbox des conteneurs pour ce type d'écran. La page utilise aussi des **animations CSS** (`animation-name`).

### La balise viewport

Sans cette ligne dans le `<head>`, un téléphone affiche la page comme sur un ordinateur, en tout petit :

```html
<meta name="viewport" content="width=device-width, initial-scale=1.0">
```

## 5. 💡 Idées pour aller plus loin

- [ ] Refaire une section avec **CSS Grid** et comparer avec Flexbox
- [ ] Suivre l'approche **mobile first** : écrire d'abord le style mobile, puis utiliser `min-width`
- [ ] Mettre la page en ligne avec **GitHub Pages**
