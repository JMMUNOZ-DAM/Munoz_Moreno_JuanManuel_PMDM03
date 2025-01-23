package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.ui.pokemon;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.databinding.PokeDetailFragmentBinding;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.R;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.pokemon.Pokemon;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.pokemon.PokemonDetail;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.retrofit.PokeApiService;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.retrofit.PokemonResponse;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.retrofit.RetrofitClient;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.ui.settings.Traductor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class PokeDetailFragment extends Fragment {
    private PokeDetailFragmentBinding binding;
    PokeApiService service = RetrofitClient.getRetrofitInstance().create(PokeApiService.class);
    String currentLanguage = Locale.getDefault().getLanguage(); // Idioma del dispositivo
    private final ArrayList<Pokemon> pks = new ArrayList<>();
    private final Traductor traductor = new Traductor();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        binding = PokeDetailFragmentBinding.inflate(inflater, container, false);
        binding.backButton.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.navigation_pokemon));
        return binding.getRoot();
    }

    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String image = getArguments().getString("image");
            String name = getArguments().getString("name");
            int number = getArguments().getInt("number");

            TextView numberTextView = view.findViewById(R.id.number);

            numberTextView.setText(getString(R.string.number) + " " + number);
            // ImageView para mostrar la imagen del personaje
            ImageView imageView = view.findViewById(R.id.image);
            if (image != null) {
                Glide.with(this)
                        .load(image)
                        .placeholder(R.drawable.pixelball)  // Imagen de carga mientras carga la imagen
                        .error(R.drawable.ic_notifications_black_24dp)  // Imagen de error en caso de que falle
                        .into(imageView);
            }

            Call<PokemonDetail> call = service.getPokemonDetail(name);
            call.enqueue(new Callback<PokemonDetail>() {
                @Override
                public void onResponse(Call<PokemonDetail> call, Response<PokemonDetail> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        PokemonDetail pokemonDetail = response.body();
                        // Obtén referencias a los TextView
                        TextView nameTextView = view.findViewById(R.id.name);
                        TextView heightTextView = view.findViewById(R.id.height);
                        TextView weightTextView = view.findViewById(R.id.weight);
                        TextView typesTextView = view.findViewById(R.id.types);
                        nameTextView.setText(name);

                        // Asigna los valores obtenidos de la API a los TextView
                        heightTextView.setText(String.format(getString(R.string.height) + "%d", pokemonDetail.getHeight()));
                        weightTextView.setText(String.format(getString(R.string.weight) + "%d", pokemonDetail.getWeight()));

                        // Para los tipos, crea una cadena concatenada
                        StringBuilder typesStringBuilder = new StringBuilder();
                        for (PokemonDetail.PokemonType type : pokemonDetail.getTypes()) {
                            typesStringBuilder.append(type.getType().getName()).append(", ");
                        }
                        // Elimina la última coma y espacio
                        if (typesStringBuilder.length() > 0) {
                            typesStringBuilder.setLength(typesStringBuilder.length() - 2);
                        }

                        String typesString = typesStringBuilder.toString();

                        // Verificar si el idioma actual es español y traducir si es necesario
                        if ("es".equals(currentLanguage)) {
                            traductor.traducir(typesString, new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        try {
                                            // Leer el cuerpo de la respuesta como una cadena
                                            String json = response.body().string();

                                            // Parsear el JSON para extraer la traducción
                                            JSONArray jsonArray = new JSONArray(json);
                                            String traduccion = jsonArray.getJSONArray(0).getJSONArray(0).getString(0);

                                            // Establecer la traducción en el TextView
                                            typesTextView.setText(getString(R.string.types) + " " + traduccion);
                                        } catch (Exception e) {
                                            Log.e("Error", "No se pudo parsear la respuesta", e);
                                            typesTextView.setText(getString(R.string.types) + " " + typesString);
                                        }
                                    } else {
                                        Log.e("Error", "Error en la respuesta del servidor");
                                        typesTextView.setText(getString(R.string.types) + " " + typesString);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.e("Error", "Falló la solicitud de traducción", t);
                                    typesTextView.setText(getString(R.string.types) + " " + typesString);
                                }
                            });
                        } else {
                            // Si el idioma no es español, establece los tipos originales
                            typesTextView.setText(getString(R.string.types) + " " + typesString);
                        }
                    } else {
                        Log.e("Error", "Error en la respuesta: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<PokemonDetail> call, Throwable t) {
                    Log.e("Error", "Error al obtener los detalles del Pokémon", t);
                }
            });

        }
    }
}



