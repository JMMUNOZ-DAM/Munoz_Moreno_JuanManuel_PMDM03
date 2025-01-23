package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.ui.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.databinding.PkdexCardviewBinding;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.pokemon.Pokemon;

/**
 * Adaptador para el RecyclerView que muestra la lista de Pokémon en la Pokédex.
 * Se encarga de vincular los datos del modelo con la vista en cada ítem del RecyclerView.
 */
public class pokedexRVAdapter extends RecyclerView.Adapter<pkViewHolder> {
    private final ArrayList<Pokemon> Pokemons;  // Lista de Pokémon a mostrar
    private final Context context;  // Contexto en el que se ejecuta el adaptador
    private final OnPokemonClickListener clickListener;  // Escucha los clics en los Pokémon
    private final Map<String, Boolean> capturedPokemons;  // Mapa para rastrear Pokémon que ya han sido capturados

    /**
     * Interfaz para manejar el clic en un Pokémon.
     */
    public interface OnPokemonClickListener {
        /**
         * Método que se llama cuando un Pokémon es clickeado.
         *
         * @param pokemon El Pokémon que fue clickeado.
         * @param view    La vista del ítem que fue clickeado.
         */
        void onPokemonClick(Pokemon pokemon, View view);
    }

    /**
     * Constructor del adaptador.
     *
     * @param Pokemons Lista de Pokémon a mostrar.
     * @param context  El contexto en el que se ejecuta el adaptador.
     * @param clickListener Listener para manejar los clics en los Pokémon.
     */
    public pokedexRVAdapter(ArrayList<Pokemon> Pokemons, Context context, OnPokemonClickListener clickListener) {
        this.Pokemons = Pokemons;
        this.context = context;
        this.clickListener = clickListener;
        this.capturedPokemons = new HashMap<>(); // Inicializar el mapa
    }

    /**
     * Se llama cuando se crea un nuevo ViewHolder.
     *
     * @param parent   El grupo padre donde se colocará el View.
     * @param viewType El tipo de vista que se debe crear.
     * @return Un nuevo ViewHolder para el ítem del RecyclerView.
     */
    @NonNull
    @Override
    public pkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PkdexCardviewBinding binding = PkdexCardviewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new pkViewHolder(binding);
    }

    /**
     * Se llama para vincular los datos a cada ViewHolder.
     *
     * @param holder   El ViewHolder en el cual se deben vincular los datos.
     * @param position La posición del ítem en el RecyclerView.
     */

    @Override
    public void onBindViewHolder(@NonNull pkViewHolder holder, int position) {
        Pokemon currentPk = Pokemons.get(position);  // Pokémon actual en esa posición
        boolean isCaptured = capturedPokemons.getOrDefault(currentPk.getName(), false);  // Comprobación si está capturado
        holder.bind(currentPk, context, clickListener, isCaptured);  // Vincula los datos al ViewHolder
    }
    /**
     * Devuelve el número total de elementos en el dataset.
     *
     * @return El número de Pokémon en la lista.
     */
    @Override
    public int getItemCount() {
        return Pokemons.size();
    }

    /**
     * Actualiza el mapa de Pokémon capturados y notifica los cambios en el RecyclerView.
     *
     * @param captured Mapa de nombres de Pokémon con su estado de captura.
     */
    public void updateCapturedPokemons(Map<String, Boolean> captured) {
        capturedPokemons.clear();  // Limpiar los Pokémon previos
        capturedPokemons.putAll(captured);  // Actualizar con los nuevos datos
        notifyDataSetChanged();  // Notifica los cambios al adaptador
    }
}