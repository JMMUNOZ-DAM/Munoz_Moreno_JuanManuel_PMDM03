package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.ui.pokemon;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.R;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.pokemon.Pokemon;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.databinding.FragmentPokemonBinding;

/**
 * Fragmento para mostrar y gestionar la lista de Pokémon pertenecientes al usuario.
 * Proporciona la funcionalidad para interactuar con los Pokémon, como hacer clic en un elemento
 * para ver detalles, liberar un Pokémon y sincronizar los datos desde Firebase Firestore.
 */
public class PokemonFragment extends Fragment {
    private final ArrayList<Pokemon> pks = new ArrayList<>();  // Lista de Pokémon que se mostrarán
    private FragmentPokemonBinding binding;  // Vínculo de la vista
    private pokemonRVAdapter adapter;  // Adaptador para el RecyclerView
    private static final String TAG = "PokemonFragment";  // Etiqueta para logs
    private FirebaseFirestore db = FirebaseFirestore.getInstance();  // Instancia de Firestore
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();  // Usuario actual de Firebase

    /**
     * Método llamado al crear la vista del fragmento.
     * Inicializa el adaptador y configuras el RecyclerView para mostrar los Pokémon.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PokemonViewModel pokemonViewModel =
                new ViewModelProvider(this).get(PokemonViewModel.class);
        binding = FragmentPokemonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Método llamado después de que la vista del fragmento ha sido creada.
     * Configura el RecyclerView, el adaptador y ejecuta la función para listar los Pokémon.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new pokemonRVAdapter(pks, getContext(), new pokemonRVAdapter.OnPokemonClickListener() {
            @Override
            public void onPokemonClick(Pokemon pokemon, View view) {
                // Navegar a los detalles del Pokémon
                pkClicked(pokemon, view);
            }

            @Override
            public void onLiberarClick(Pokemon pokemon, View view) {
                // Liberar al Pokémon
                liberaPokemon(view, pokemon);
            }
        });
        binding.pokeRecyler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.pokeRecyler.setAdapter(adapter);
        //Listamos los pokemons actuales del usuario
        fetchPokemons();
    }

    /**
     * Maneja el clic en un Pokémon mostrando los detalles del mismo.
     *
     * @param pk   El Pokémon que fue clickeado.
     * @param view La vista en la que se hizo clic.
     */

    public void pkClicked(Pokemon pk, View view) {
        // Crear un Bundle para pasar datos al PokeDetailFragment
        Bundle bundle = new Bundle();
        bundle.putString("name", pk.getName());  // Pasa el nombre del Pokémon
        bundle.putString("image", pk.getImage());  // Pasa la imagen del Pokémon
        bundle.putInt("number", pk.getId());  // Pasa el número del Pokémon
        bundle.putString("types", pk.getTypes());  // Pasa los tipos del Pokémon
        // Navegar al PjDetailFragment con el Bundle
        Navigation.findNavController(view).navigate(R.id.navigation_details, bundle);
    }

    /**
     * Obtiene la lista de Pokémon desde Firebase Firestore y los muestra en el RecyclerView.
     */
    private void fetchPokemons() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("pokemon")
                .whereEqualTo("owner", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<Pokemon> pokemonList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Pokemon pokemon = document.toObject(Pokemon.class);
                            pokemonList.add(pokemon);
                        }
                        // Actualiza la lista del adaptador y notifica los cambios
                        pks.clear();
                        pks.addAll(pokemonList);
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, "Error al obtener los Pokémon: ", task.getException());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Fallo en la consulta de Firebase", e);
                });
    }

    /**
     * Liberar un Pokémon si la función está habilitada por el switch.
     * Elimine el Pokémon correspondiente del Firestore si el switch de liberación está activado.
     *
     * @param view    La vista en la que se hizo clic para liberar el Pokémon.
     * @param pokemon El Pokémon que se quiere liberar.
     */
    private void liberaPokemon(View view, Pokemon pokemon) {
        SharedPreferences preferences = requireContext().getSharedPreferences("misPreferencias", MODE_PRIVATE);
        boolean isSwitchOn = preferences.getBoolean("deleteSwitchState", false);  // Estado del switch
        if (isSwitchOn) {
            db.collection("pokemon")
                    .whereEqualTo("name", pokemon.getName())
                    .whereEqualTo("owner", pokemon.getOwner())
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                db.collection("pokemon")
                                        .document(doc.getId())
                                        .delete()
                                        .addOnSuccessListener(aVoid ->
                                                Toast.makeText(requireContext(), getString(R.string.free), Toast.LENGTH_SHORT).show())
                                        .addOnFailureListener(e ->
                                                Toast.makeText(requireContext(), getString(R.string.errorFree), Toast.LENGTH_SHORT).show());
                            }
                            fetchPokemons();  // Actualiza la lista después de liberar un Pokémon
                        } else {
                            Toast.makeText(requireContext(), "Pokémon no encontrado en la colección", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(requireContext(), "Error buscando Pokémon: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(requireContext(), getString(R.string.cantFree), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método llamado cuando el fragmento es destruido.
     * Limpia las referencias para evitar filtraciones de memoria.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}