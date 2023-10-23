package com.example.pokedex

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pokedex.api.PokeApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_POKEMON_ID = "extra_pokemon_id"
    }

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
        setContentView(R.layout.activity_pokemon_detail)

        val pokemonId = intent.getStringExtra(EXTRA_POKEMON_ID) ?: return

        getPokemonDetails(pokemonId)
    }

// ...

    private fun getPokemonDetails(pokemonId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = pokeApi.getPokemonDetails(pokemonId)
            if (response.isSuccessful) {
                val pokemonDetails = response.body()
                runOnUiThread {
                    val nameTextView: TextView = findViewById(R.id.pokemon_name_detail)
                    val imageView: ImageView = findViewById(R.id.pokemon_image_detail)
                    val heightTextView: TextView = findViewById(R.id.pokemon_height_detail)
                    val weightTextView: TextView = findViewById(R.id.pokemon_weight_detail)
                    val baseExperienceTextView: TextView =
                        findViewById(R.id.pokemon_base_experience_detail)
                    val typesTextView: TextView = findViewById(R.id.pokemon_types_detail)
                    val abilitiesTextView: TextView = findViewById(R.id.pokemon_abilities_detail)

                    nameTextView.text = pokemonDetails?.name
                    val imageUrl = pokemonDetails?.sprites?.front_default
                    Glide.with(this@PokemonDetailActivity).load(imageUrl).into(imageView)
                    heightTextView.text = "Height: ${pokemonDetails?.height}"
                    weightTextView.text = "Weight: ${pokemonDetails?.weight}"
                    baseExperienceTextView.text =
                        "Base Experience: ${pokemonDetails?.base_experience}"

                    // Concatenar los nombres de los tipos y habilidades
                    val types = pokemonDetails?.types?.joinToString(", ") { it.type.name }
                    val abilities =
                        pokemonDetails?.abilities?.joinToString(", ") { it.ability.name }

                    typesTextView.text = "Types: $types"
                    abilitiesTextView.text = "Abilities: $abilities"
                }
            }
        }
    }
}
