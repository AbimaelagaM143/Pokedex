package com.example.pokedex.api  // Asegúrate de que el paquete sea correcto

import com.example.pokedex.models.Pokemon
import com.example.pokedex.models.PokemonDetail
import com.example.pokedex.models.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Define las clases de modelos de datos aquí
data class PokemonListResponse(
    val results: List<Pokemon>
)

data class Pokemon(
    val name: String,
    val url: String
)

// Define la interfaz de la API aquí
interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonListResponse>

    @GET("pokemon/{pokemonId}")
    suspend fun getPokemonDetails(
        @Path("pokemonId") pokemonId: String
    ): Response<PokemonDetail>
}
