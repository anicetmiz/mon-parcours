const DESCRIPTIONS = [
  "Quel produit de luxe!",
  "Simple, mais si efficace pour charmer votre tendre moité",
  "Wow, je n'ai jamais vu rien de si beau...",
  "Simplement extraordinaire!",
  "À couper le souffle! Il vous le faut!",
  "Magnifique produit, jamais vous n'aurez une 2e chance de retrouver ce bijou.",
  "Le meilleur de tous les produits. Même un fou ne pourrait se passer de l'acheter.",
  "Quel produit de luxe!",
  "Simple, mais si efficace pour charmer votre tendre moitié.",
  "Wow, je n'ai jamais vu rien de si beau...",
  "Simplement extraordinaire!",
  "Un véritable chef-d'œuvre de sophistication.",
  "L'élégance à l'état pur.",
  "Un incontournable pour les amateurs de qualité.",
  "Raffiné et intemporel.",
  "Un bijou de technologie.",
  "Le summum du confort et du style.",
  "Un véritable trésor pour les connaisseurs.",
  "La perfection incarnée.",
  "Un produit qui respire l'excellence.",
  "D'une beauté à couper le souffle.",
  "Un must-have pour les amateurs de luxe.",
  "Un mélange parfait de simplicité et d'élégance.",
  "Un produit qui dépasse toutes les attentes.",
  "Un chef-d'œuvre de design.",
  "Un produit qui fait tourner les têtes.",
  "L'alliance parfaite entre style et fonctionnalité.",
  "Un produit qui inspire l'admiration.",
  "Un classique revisité avec modernité.",
  "Un produit qui incarne le raffinement.",
  "Une véritable œuvre d'art.",
  "Un produit qui se distingue par son unicité.",
  "Un incontournable pour les passionnés de qualité.",
  "Un produit qui allie tradition et innovation.",
  "Un bijou de sophistication.",
  "Un produit qui évoque le luxe à chaque détail.",
  "Un produit qui redéfinit l'élégance.",
  "Un produit qui sublime votre quotidien.",
  "Un produit qui incarne la perfection.",
  "Un produit qui séduit par sa simplicité.",
  "Un produit qui inspire le respect.",
  "Un produit qui fait rêver.",
  "Un produit qui allie beauté et performance.",
  "Un produit qui enchante par son design.",
  "Un produit qui respire la classe.",
  "Un produit qui fait la différence.",
  "Un produit qui vous laisse sans voix.",
];

const DESCRIPTIONS_ALTERNATIF = [
  "Quel produit de luxe!",
  "Simple, mais si efficace pour charmer votre tendre moité",
  "Wow, je n'ai jamais vu rien de si beau...",
  "Simplement extraordinaire!",
  "À couper le souffle! Il vous le faut!",
  "Magnifique produit, jamais vous n'aurez une 2e chance de retrouver ce bijou.",
  "Le meilleur de tous les produits. Même un fou ne pourrait se passer de l'acheter.",
];

const CHEMIN_IMAGES = [
  "img/product1.jpg",
  "img/product2.jpg",
  "img/product3.jpg",
  "img/product4.jpg",
  "img/product5.jpg",
  "img/product6.jpg",
  "img/product7.jpg",
  "img/product8.jpg",
  "img/product9.jpg",
  "img/product10.jpg",
  "img/product11.jpg",
  "img/product12.jpg",
  "img/product13.jpg",
];

const CHEMIN_IMAGES_ALTERNATIF = [
  "img/product1.jpg",
  "img/product2.jpg",
  "img/product3.jpg",
  "img/product4.jpg",
];

let main = document.querySelector("main > div");
// Partie 1 - Création des cartes
for (let i = 0; i < DESCRIPTIONS.length; i++) {
  if (i % 3 === 0) {
    let parentDiv = document.createElement("div");
    parentDiv.className = "row mb-4";
    main.appendChild(parentDiv);
  }

  let enfant1 = document.createElement("div");
  enfant1.className = "col-4 d-flex align-items-stretch";

  main.lastChild.appendChild(enfant1);

  let enfant2 = document.createElement("div");
  enfant2.className = "card";
  enfant2.style.width = "18rem";

  enfant1.appendChild(enfant2);

  let image = document.createElement("img");
  image.className = "card-img-top img-fluid";
  image.src = CHEMIN_IMAGES[i % CHEMIN_IMAGES.length];
  image.alt = `Produit #${i + 1}`;
  enfant2.appendChild(image);

  let frereImage = document.createElement("div");
  frereImage.className = "card-body d-flex flex-column";

  enfant2.appendChild(frereImage);

  let textEnfant1 = document.createElement("h5");
  textEnfant1.className = "card-title text-center";
  textEnfant1.innerText = `Produit #${i + 1}`;

  frereImage.appendChild(textEnfant1);

  let textEnfant2 = document.createElement("p");
  textEnfant2.className = "card-text";
  textEnfant2.innerText = DESCRIPTIONS[i % DESCRIPTIONS.length];

  frereImage.appendChild(textEnfant2);

  let textEnfant3 = document.createElement("a");
  textEnfant3.className = "btn btn-primary mt-auto";
  textEnfant3.href = "#";
  textEnfant3.innerText = "Intéressé?";

  frereImage.appendChild(textEnfant3);
}

// Partie 2 - Boutons "Intéressé!"
let boutonInteresse = document.querySelectorAll(".card-body > a");
function annulCompDefaut(event) {
  event.preventDefault();
}
function evenBoutons() {
  // boucle sur mes boutons
  boutonInteresse.forEach((bouton) => {
    // retique le conportement par defaut
    bouton.addEventListener("click", annulCompDefaut);

    // change la couleur en changeamt des class
    bouton.addEventListener("click", function () {
      if (bouton.classList.contains("btn-danger")) {
        bouton.classList.remove("btn-danger");
      } else {
        bouton.classList.add("btn-danger");
      }
    });
  });

  //changer le texte du bouton
  boutonInteresse.forEach((bouton) => {
    bouton.addEventListener("click", function () {
      if (bouton.innerText === "Je le prends!") {
        bouton.innerText = "Intéressé?";
      } else {
        bouton.innerText = "Je le prends!";
      }
    });
  });
}
evenBoutons();

// Partie 3 - Affichage de la fenêtre modale.

//fonction pour afficher le modale
function modaleF() {
  let modale = new bootstrap.Modal(document.querySelector(".modal"));
  modale.show();
}

//fonction pour afficher le minuteur
function minuteur() {
  setTimeout(modaleF, 2000);
}

// ajout de l'evenement apres le minuteur
document.addEventListener("DOMContentLoaded", minuteur());

// RTFM: https://getbootstrap.com/docs/5.0/components/modal/#via-javascript

// Partie 4 - Footer

let foot = document.querySelector("footer > .text-center");
foot.innerText += " cégep de granby " + new Date().getFullYear();
