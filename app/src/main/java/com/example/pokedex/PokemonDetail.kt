package com.example.pokedex.models

data class PokemonDetail(
    val id: Int, // ID del Pokémon
    val name: String, // Nombre del Pokémon
    val height: Int, // Altura del Pokémon
    val weight: Int, // Peso del Pokémon
    val base_experience: Int, // Experiencia base al vencerlo
    val types: List<Type>, // Lista de tipos del Pokémon
    val abilities: List<Ability>, // Lista de habilidades del Pokémon
    val sprites: Sprites // Sprites del Pokémon
)

// Clase para representar los tipos de un Pokémon
data class Type(
    val slot: Int,
    val type: TypeDetail
)

data class TypeDetail(
    val name: String,
    val url: String
)

// Clase para representar las habilidades de un Pokémon
data class Ability(
    val ability: AbilityDetail,
    val is_hidden: Boolean,
    val slot: Int
)

data class AbilityDetail(
    val name: String,
    val url: String
)

// Clase para representar los sprites de un Pokémon
data class Sprites(
    val front_default: String,
    val front_shiny: String,
    val back_default: String,
    val back_shiny: String
)
