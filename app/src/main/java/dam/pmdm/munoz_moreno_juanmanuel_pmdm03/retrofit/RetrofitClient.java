package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Clase que maneja la configuración de Retrofit para realizar solicitudes a la API PokeAPI.
 */
public class RetrofitClient {
    /**
     * La URL base de la API PokeAPI.
     */
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";
    /**
     * Instancia única de Retrofit.
     */
    private static Retrofit retrofit;
    /**
     * Obtiene la instancia de Retrofit configurada con la base URL y el convertidor para deserializar las respuestas.
     *
     * @return Una instancia de Retrofit configurada.
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}