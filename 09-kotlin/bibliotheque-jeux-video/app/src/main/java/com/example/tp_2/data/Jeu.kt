package com.example.tp_2.data

data class Jeu(
    val id:Int,
    val titre : String,
    val platforme: String,
    val statut : String,
    val note : Int?,
    val tempsEstimer : Int?
)

val listJeux: List<Jeu> = listOf(
    Jeu(
        1,
        "Elden Ring",
        "PC",
        "Terminé",
        5,
        80
    ),
    Jeu(
        2,
        "Hollow Knight ",
        "PC",
        "En cours",
        null,
        40
    ),Jeu(
        3,
        "The Legend of Zelda : Tears of the \n" +
                "Kingdom ",
        "Switch",
        "Pas commencé",
        null,
        null
    ),Jeu(
        4,
        "God of War Ragnarök",
        "PS5",
        "Terminé",
        4,
        35
    ),Jeu(
        5,
        "Hades II",
        "PC",
        "Terminé",
        5,
        25
    ),Jeu(
        6,
        "Cyberpunk 2077 ",
        "PS5",
        "En cours",
        3,
        60
    ),Jeu(
        7,
        "Celeste",
        "Switch",
        "Pas commencé",
        null,
        null
    ),Jeu(
        8,
        "Stardew Valley",
        "PC",
        "En cours",
        null,
        20
    ),
)
