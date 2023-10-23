package com.example.pokedex

import PokemonAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.api.PokeApi
import com.example.pokedex.models.PokemonListResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.LinearLayoutManager



class MainActivity : AppCompatActivity() {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val pokeApi by lazy {
        retrofit.create(PokeApi::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.pokemon_list)
        recyclerView.layoutManager = LinearLayoutManager(this)  // Esta línea es nueva
        val adapter = PokemonAdapter(listOf())  // Lista vacía inicial
        recyclerView.adapter = adapter

        // Llamar a la función para obtener la lista de Pokémon
        getPokemonList(adapter)
    }

    private fun getPokemonList(adapter: PokemonAdapter) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = pokeApi.getPokemonList(20, 0)
            if (response.isSuccessful) {
                val pokemonListResponse: PokemonListResponse? = response.body()
                val pokemonList = pokemonListResponse?.results ?: listOf()
                runOnUiThread {
                    adapter.updateData(pokemonList)
                }
            }
        }
    }
}
