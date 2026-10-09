**Auteur :** Anicet Mizumbi · Cégep de Granby, Hiver 2025 · Projet individuel

# 🛍️ Catalogue de produits — JavaScript pur

Un catalogue de produits **généré entièrement en JavaScript** : aucune carte n'est écrite dans le HTML. Le script crée les cartes, réagit aux clics, ouvre une fenêtre modale après 2 secondes et met à jour le pied de page.

---

## 📑 Sommaire

1. [Ce que fait la page](#1--ce-que-fait-la-page)
2. [Technologies](#2--technologies)
3. [Ouvrir le projet](#3--ouvrir-le-projet)
4. [Structure](#4--structure)
5. [Le code expliqué, partie par partie](#5--le-code-expliqué-partie-par-partie)
6. [Idées pour aller plus loin](#6--idées-pour-aller-plus-loin)

---

## 1. 🖥️ Ce que fait la page

1. **Crée 47 cartes de produits**, 3 par ligne, avec une image, un titre et une description.
2. **Bouton « Intéressé? »** : un clic le fait passer au rouge et change son texte en « Je le prends! » (un 2e clic le remet comme avant).
3. **Fenêtre modale** : elle s'ouvre automatiquement 2 secondes après le chargement.
4. **Pied de page** : l'année en cours est ajoutée automatiquement.

## 2. 🧰 Technologies

| Outil | Rôle |
|---|---|
| **JavaScript** (sans framework) | Toute la logique, dans `tp.js` |
| **HTML5** / **CSS3** | La page de base et le style |
| **Bootstrap 5.3** (CDN) | Le style des cartes et la fenêtre modale |
| **normalize.css** | Librairie externe qui uniformise le style par défaut des navigateurs |

## 3. ▶️ Ouvrir le projet

Double-clique sur **`index.html`**. Une connexion Internet est nécessaire pour charger Bootstrap.

## 4. 📁 Structure

```
catalogue-produits/
├── index.html          ← la page (le <main> est vide au départ)
├── carte.html          ← un modèle de carte, pour référence
├── tp.js               ← tout le JavaScript
├── style.css
├── reset.local.css
├── normalize.css       ← librairie externe
└── img/                ← les images des produits
```

## 5. 🧠 Le code expliqué, partie par partie

### Partie 1 — Créer des éléments avec le DOM

On ne touche pas au HTML : on **crée** chaque élément en JavaScript, puis on l'ajoute à la page.

```js
let image = document.createElement("img");    // 1. créer
image.className = "card-img-top img-fluid";   // 2. configurer
image.src = CHEMIN_IMAGES[i % CHEMIN_IMAGES.length];
enfant2.appendChild(image);                   // 3. ajouter à la page
```

- `i % 3 === 0` : une nouvelle ligne est créée toutes les 3 cartes.
- `i % CHEMIN_IMAGES.length` : le **modulo** fait recommencer la liste d'images quand on arrive à la fin.

### Partie 2 — Les événements

```js
bouton.addEventListener("click", function () {
  bouton.classList.contains("btn-danger")
    ? bouton.classList.remove("btn-danger")
    : bouton.classList.add("btn-danger");
});
```

- `event.preventDefault()` empêche le lien `#` de faire remonter la page en haut.
- `classList` ajoute ou retire une classe CSS, ce qui change l'apparence du bouton.

### Partie 3 — Le minuteur

```js
setTimeout(modaleF, 2000); // exécute modaleF dans 2000 ms
```

### Partie 4 — La date

```js
foot.innerText += " cégep de granby " + new Date().getFullYear();
```

## 6. 💡 Idées pour aller plus loin

- [ ] `addEventListener("DOMContentLoaded", minuteur())` appelle `minuteur` **tout de suite** : il faudrait écrire `minuteur`, sans les parenthèses
- [ ] Remplacer les deux `forEach` sur les boutons par un seul (un seul écouteur par bouton)
- [ ] Charger les produits depuis un fichier JSON avec `fetch()`
- [ ] Ajouter un panier qui compte les produits choisis
