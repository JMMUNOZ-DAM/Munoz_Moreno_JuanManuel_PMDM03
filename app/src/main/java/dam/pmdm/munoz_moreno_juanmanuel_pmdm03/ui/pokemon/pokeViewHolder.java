package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.ui.pokemon;



import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import java.util.Locale;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.databinding.PokeCardviewBinding;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.pokemon.Pokemon;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.ui.settings.Traductor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewHolder para manejar la visualización de un solo item del RecyclerView que muestra información sobre un Pokémon.
 * Incluye la lógica para enlazar los datos de un Pokémon con su correspondiente vista, gestionar las imágenes, los textos
 * y los eventos de clic en los elementos.
 */
public class pokeViewHolder extends RecyclerView.ViewHolder {

    private final PokeCardviewBinding binding;
    private final Traductor traductor = new Traductor();
    /**
     * Constructor del ViewHolder que inicializa el enlace a la vista usando el binding proporcionado.
     *
     * @param binding El binding que contiene los componentes de la vista.
     */
    public pokeViewHolder(@NonNull PokeCardviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
    /**
     * Método para enlazar los datos del Pokémon con la vista del ViewHolder.
     * Carga la imagen del Pokémon, muestra el nombre, verifica el idioma para traducir los tipos y configura los listeners
     * para los clics en el elemento.
     *
     * @param pokemon         El objeto Pokémon que contiene la información a mostrar.
     * @param context         El contexto del adaptador o fragmento donde se encuentra el ViewHolder.
     * @param clickListener   El listener para manejar los clics en el Pokémon o en el botón de liberar.
     */
    public void bind(Pokemon pokemon, Context context, pokemonRVAdapter.OnPokemonClickListener clickListener) {
        // Cargar la imagen del Pokémon usando Glide
        Glide.with(context)
                .load(pokemon.getImage())
                .into(binding.image);

        // Establecer el nombre del Pokémon en el TextView
        binding.name.setText(pokemon.getName());

        // Verificar el idioma actual del dispositivo para realizar traducciones
        if (Locale.getDefault().getLanguage().equals("es")) {
            // Traducir los tipos del Pokémon utilizando la clase Traductor
            traductor.traducir(pokemon.getTypes(), new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        try {
                            // Leer el cuerpo de la respuesta como una cadena
                            String json = response.body().string();

                            // Parsear el JSON para extraer la traducción
                            JSONArray jsonArray = new JSONArray(json);
                            String traduccion = jsonArray.getJSONArray(0).getJSONArray(0).getString(0);

                            // Establecer el texto traducido en el TextView
                            binding.types.setText(traduccion);
                        } catch (Exception e) {
                            Log.e("pokeViewHolder", "Error al parsear la respuesta", e);
                            binding.types.setText("Error en la traducción");
                        }
                    } else {
                        Log.e("pokeViewHolder", "Error en la respuesta del servidor");
                        binding.types.setText("No se pudo traducir");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("pokeViewHolder", "Falló la solicitud de traducción", t);
                    binding.types.setText("Error en la conexión");
                }
            });
        } else {
            // Si el idioma no es español, establecer los tipos originales del Pokémon
            binding.types.setText(pokemon.getTypes());
        }

        // Configurar el evento de clic en el elemento (para mostrar detalles del Pokémon)
        itemView.setOnClickListener(view -> clickListener.onPokemonClick(pokemon, view));

        // Configurar el evento de clic en el botón para liberar el Pokémon
        binding.liberarButton.setOnClickListener(view -> clickListener.onLiberarClick(pokemon, view));
    }
}