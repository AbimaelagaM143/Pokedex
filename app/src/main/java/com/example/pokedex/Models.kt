package com.example.pokedex.models  // Asegúrate de que el paquete sea correcto

data class PokemonListResponse(val results: List<Pokemon>)
data class Pokemon(val name: String, val url: String)

