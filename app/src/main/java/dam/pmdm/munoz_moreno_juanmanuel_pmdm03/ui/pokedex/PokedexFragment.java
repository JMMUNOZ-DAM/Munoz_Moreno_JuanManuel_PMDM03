package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.ui.pokedex;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.R;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.pokemon.PokemonDetail;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.retrofit.PokeApiService;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.databinding.FragmentPokedexBinding;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.pokemon.Pokemon;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.retrofit.PokemonResponse;

/**
 * Fragmento que muestra la Pokédex, donde se listan los Pokémon y se manejan las acciones de captura.
 * Este fragmento interactúa con una API para obtener los detalles de los Pokémon, muestra la lista en un RecyclerView
 * y se comunica con Firestore para capturar Pokémon.
 */
public class PokedexFragment extends Fragment {

    private final ArrayList<Pokemon> pks = new ArrayList<>();  // Lista de Pokémon a mostrar
    private FragmentPokedexBinding binding;  // Binding para acceder a los elementos de la vista
    private pokedexRVAdapter adapter;  // Adaptador para el RecyclerView que muestra la Pokédex
    private static final String TAG = "PokedexFragment";  // Etiqueta para loguear mensajes
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();  // Usuario autenticado en Firebase
    private String height = "";  // Almacena la altura del Pokémon
    private String weight = "";  // Almacena el peso del Pokémon
    private String types = "";  // Almacena los tipos del Pokémon

    /**
     * Método que se llama al crear la vista del fragmento.
     * Inflará la vista desde el layout `fragment_pokedex.xml` y la devolverá.
     *
     * @param inflater           Inflador para el layout.
     * @param container          El contenedor donde se inflará la vista.
     * @param savedInstanceState Estado guardado del fragmento.
     * @return La vista inflada del fragmento.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPokedexBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Método que se llama después de que la vista ha sido creada.
     * Configura el RecyclerView, el adaptador y hace una llamada a la API para obtener la lista de Pokémon.
     *
     * @param view               La vista creada.
     * @param savedInstanceState Estado guardado del fragmento.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Configura el RecyclerView con un adaptador y un administrador de diseño
        adapter = new pokedexRVAdapter(pks, getContext(), this::pkClicked);
        binding.pokeRecyler.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        binding.pokeRecyler.setAdapter(adapter);
        // Llama a la API para obtener la lista de Pokémon
        fetchPokemons();
    }

    /**
     * Método para realizar la llamada a la API y obtener la lista de Pokémon.
     * Luego consulta Firebase Firestore para verificar los Pokémon que ya han sido capturados.
     */
    private void fetchPokemons() {
        PokeApiService service = RetrofitClient.getRetrofitInstance().create(PokeApiService.class);
        Call<PokemonResponse> call = service.getPokemonList(51, 0);  // Llama la API para obtener los primeros 51 Pokémon
        call.enqueue(new Callback<PokemonResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Pokemon> pokemonList = response.body().getResults();
                    pks.clear();  // Limpiar lista previa
                    pks.addAll(pokemonList);  // Agregar los nuevos Pokémon a la lista

                    // Consulta Firestore para verificar los Pokémon capturados
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("pokemon")
                            .whereEqualTo("owner", user.getUid())  // Verifica los Pokémon del usuario actual
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                Map<String, Boolean> captured = new HashMap<>();
                                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                    Pokemon capturedPokemon = doc.toObject(Pokemon.class);
                                    captured.put(capturedPokemon.getName(), true);  // Guarda los Pokémon capturados en un mapa
                                }
                                adapter.updateCapturedPokemons(captured);  // Actualiza el adaptador con los Pokémon capturados
                                adapter.notifyDataSetChanged();  // Notifica cambios al adaptador
                            })
                            .addOnFailureListener(e -> Log.e(TAG, "Error fetching captured Pokémon", e));
                } else {
                    Log.e(TAG, "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.e(TAG, "Error al obtener los Pokémon", t);
            }
        });
    }

    /**
     * Método que se ejecuta cuando se hace clic en un ítem del RecyclerView.
     * Obtiene detalles del Pokémon desde la API y luego lo captura.
     *
     * @param pk   El Pokémon que fue clickeado.
     * @param view La vista del ítem que fue clickeado.
     */
    public void pkClicked(Pokemon pk, View view) {
        PokeApiService service = RetrofitClient.getRetrofitInstance().create(PokeApiService.class);
        Call<PokemonDetail> call = service.getPokemonDetail(pk.getName());
        call.enqueue(new Callback<PokemonDetail>() {
            @Override
            public void onResponse(Call<PokemonDetail> call, Response<PokemonDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PokemonDetail pokemonDetail = response.body();
                    // Asigna los valores obtenidos de la API a los TextView
                    height = String.valueOf(pokemonDetail.getHeight());  // Asigna la altura del Pokémon
                    weight = String.valueOf(pokemonDetail.getWeight());  // Asigna el peso del Pokémon

                    // Para los tipos, crea una cadena concatenada
                    StringBuilder typesStringBuilder = new StringBuilder("");
                    for (PokemonDetail.PokemonType type : pokemonDetail.getTypes()) {
                        typesStringBuilder.append(type.getType().getName()).append(", ");
                    }

                    // Elimina la última coma y espacio
                    if (typesStringBuilder.length() > 0) {
                        typesStringBuilder.setLength(typesStringBuilder.length() - 2);
                    }
                    types = typesStringBuilder.toString();  // Guarda los tipos como cadena

                } else {
                    Log.e(TAG, "Error en la respuesta: " + response.code());
                }
                Toast.makeText(getContext(), pk.getName(), Toast.LENGTH_SHORT).show();  // Muestra el nombre del Pokémon en un Toast
                Pokemon poke = new Pokemon(pk.getName(), pk.getUrl(), user.getUid());
                poke.setHeight(height);  // Establece la altura del Pokémon en la base de datos
                poke.setWeight(weight);  // Establece el peso del Pokémon en la base de datos
                poke.setTypes(types);  // Establece los tipos del Pokémon en la base de datos
                capturaPokemon(view, poke);  // Llama al método para capturar el Pokémon
                fetchPokemons();  // Vuelve a listar los Pokémon tras la captura
            }

            @Override
            public void onFailure(Call<PokemonDetail> call, Throwable t) {
                Log.e(TAG, "Error al obtener los detalles del Pokémon", t);
            }
        });
    }

    /**
     * Método para capturar un Pokémon en Firestore.
     * Almacena el objeto Pokémon en la colección de Firestore bajo el UID del usuario autenticado.
     *
     * @param view    La vista desde la que se realiza la captura.
     * @param pokemon El Pokémon a capturar.
     */
    private void capturaPokemon(View view, Pokemon pokemon) {
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        db.collection("pokemon")
                .add(pokemon)
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(requireContext(), getString(R.string.captured), Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(requireContext(), getString(R.string.errorCapture) + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    /**
     * Método llamado al destruir la vista del fragmento. Aquí liberamos el binding.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
