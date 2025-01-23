package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.ui.pokedex;


import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.R;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.databinding.PkdexCardviewBinding;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.pokemon.Pokemon;

/**
 * ViewHolder para manejar la visualización de un único item en el RecyclerView que muestra información sobre un Pokémon.
 * Esta clase se encarga de mostrar la imagen del Pokémon, manejar la acción de captura y configurar los listeners para
 * los clics en los elementos.
 */
public class pkViewHolder extends RecyclerView.ViewHolder {
    private final PkdexCardviewBinding binding;

    /**
     * Constructor del ViewHolder que inicializa el enlace a la vista usando el binding proporcionado.
     *
     * @param binding El binding que contiene los componentes de la vista.
     */
    public pkViewHolder(PkdexCardviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Método para enlazar los datos del Pokémon con la vista del ViewHolder.
     * Carga la imagen del Pokémon, muestra un ícono específico dependiendo si está capturado o no,
     * configura el nombre del Pokémon y los listeners para los clics en los elementos.
     *
     * @param pokemon    El objeto Pokémon que contiene la información a mostrar.
     * @param context    El contexto del adaptador o fragmento donde se encuentra el ViewHolder.
     * @param listener   El listener para manejar los clics en el Pokémon.
     * @param isCaptured Un booleano que indica si el Pokémon ya ha sido capturado o no.
     */
    public void bind(Pokemon pokemon, Context context, pokedexRVAdapter.OnPokemonClickListener listener, boolean isCaptured) {
        if (isCaptured) {
            // Mostrar la PokéBall si está capturado
            binding.image.setImageResource(R.drawable.pixelball);  // Define el recurso visual de la PokéBall
            binding.getRoot().setOnClickListener(view ->
                    Toast.makeText(context, R.string.allreadyCaptured, Toast.LENGTH_SHORT).show()  // Muestra un mensaje si ya está capturado
            );
        } else {
            Glide.with(context)
                    .load(pokemon.getImage())  // Carga la imagen del Pokémon desde su URL
                    .placeholder(R.drawable.pokeopen)  // Define un placeholder mientras carga la imagen
                    .into(binding.image);  // Muestra la imagen en el ImageView

            // Configurar la acción de captura al hacer clic en el item
            binding.getRoot().setOnClickListener(view -> listener.onPokemonClick(pokemon, view));
        }

        // Configurar el nombre del Pokémon
        binding.name.setText(pokemon.getName());
    }
}