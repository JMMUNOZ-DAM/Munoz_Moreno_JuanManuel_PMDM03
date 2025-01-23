package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.retrofit;

import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.pokemon.PokemonDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interfaz que define los servicios de la API PokeAPI para obtener información sobre Pokémon.
 */
public interface PokeApiService {
    /**
     * Endpoint para obtener la lista de Pokémon.
     *
     * @param limit  El número máximo de Pokémon que se desea recuperar.
     * @param offset El número de Pokémon desde el cual se desea empezar la recuperación.
     * @return Un objeto `Call` que devuelve una respuesta de tipo `PokemonResponse` con la lista de Pokémon.
     */
    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);

    /**
     * Endpoint para obtener los detalles de un Pokémon por su ID o nombre.
     *
     * @param idOrName El ID o el nombre del Pokémon para el cual se desean recuperar los detalles.
     * @return Un objeto `Call` que devuelve una respuesta de tipo `PokemonDetail` con los detalles del Pokémon.
     */
    // Endpoint para obtener detalles de un Pokémon por su id o nombre
    @GET("pokemon/{id_or_name}/")
    Call<PokemonDetail> getPokemonDetail(@Path("id_or_name") String idOrName);
}