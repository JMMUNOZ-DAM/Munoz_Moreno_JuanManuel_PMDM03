package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.ui.pokemon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.databinding.PokeCardviewBinding;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.pokemon.Pokemon;

/**
 * Adaptador para RecyclerView que muestra una lista de Pokémon en una interfaz de usuario.
 * Este adaptador vincula la lista de Pokémon con sus vistas correspondientes, permitiendo la
 * interacción del usuario, como hacer clic en un elemento o en un botón de liberación.
 */
public class pokemonRVAdapter extends RecyclerView.Adapter<pokeViewHolder> {
    private final ArrayList<Pokemon> Pokemons;  // Lista de Pokémon a mostrar
    private final Context context;              // Contexto de la aplicación
    private final OnPokemonClickListener clickListener;  // Listener para manejar clics en los Pokémon

    /**
     * Interfaz para manejar los clics en los elementos del RecyclerView.
     */
    public interface OnPokemonClickListener {
        /**
         * Evento que se dispara cuando un Pokémon es clickeado.
         *
         * @param pokemon Pokémon seleccionado.
         * @param view    Vista en la que se realizó el clic.
         */
        void onPokemonClick(Pokemon pokemon, View view); // Clic en el elemento
        /**
         * Evento que se dispara cuando se hace clic en el botón para liberar un Pokémon.
         *
         * @param pokemon Pokémon seleccionado para liberar.
         * @param view    Vista en la que se realizó el clic en el botón de liberar.
         */
        void onLiberarClick(Pokemon pokemon, View view); // Clic en el botón liberar
    }
    /**
     * Constructor del adaptador.
     *
     * @param Pokemons Lista de Pokémon a mostrar.
     * @param context  Contexto de la aplicación.
     * @param clickListener Listener para manejar clics en los elementos.
     */
    public pokemonRVAdapter(ArrayList<Pokemon> Pokemons, Context context, pokemonRVAdapter.OnPokemonClickListener clickListener) {
        this.Pokemons = Pokemons;
        this.context = context;
        this.clickListener = clickListener;
    }
    /**
     * Método para crear y devolver un ViewHolder para mostrar los datos del Pokémon.
     *
     * @param parent   El ViewGroup al que el ViewHolder estará asociado.
     * @param viewType El tipo de vista.
     * @return Un ViewHolder que muestra los detalles de un Pokémon.
     */
    @NonNull
    public pokeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PokeCardviewBinding binding = PokeCardviewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new pokeViewHolder(binding);
    }
    /**
     * Método para vincular los datos del Pokémon con la vista del ViewHolder.
     *
     * @param holder   El ViewHolder que mostrará la información del Pokémon.
     * @param position La posición del elemento en la lista.
     */
    public void onBindViewHolder(@NonNull pokeViewHolder holder, int position) {
        Pokemon currentPk = this.Pokemons.get(position); // Obtener el Pokémon actual
        holder.bind(currentPk, context, clickListener); // Vincular los datos con la vista y el listener
    }
    /**
     * Devuelve el número total de elementos en la lista de Pokémon.
     *
     * @return El tamaño de la lista de Pokémon.
     */
    public int getItemCount() {
        return Pokemons.size();
    }
}
