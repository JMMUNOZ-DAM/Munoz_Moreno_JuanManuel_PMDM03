package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.retrofit;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.pokemon.Pokemon;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.pokemon.PokemonDetail;

/**
 * Clase que representa la respuesta de la API PokeAPI al consultar la lista de Pokémon.
 */
public class PokemonResponse {
    /**
     * Lista de objetos `Pokemon` que contiene los resultados de la consulta.
     */
    @SerializedName("results")
    private List<Pokemon> results;
    /**
     * Representación de un solo Pokémon obtenido de la respuesta.
     * Puede estar presente si la respuesta solo contiene un Pokémon en lugar de una lista.
     */
    private Pokemon pokemon;

    /**
     * Obtiene la lista de Pokémon resultado de la consulta.
     *
     * @return Una lista de objetos `Pokemon` que representan los Pokémon obtenidos.
     */
    public List<Pokemon> getResults() {
        return results;
    }

    /**
     * Establece la lista de Pokémon resultado de la consulta.
     *
     * @param results La lista de objetos `Pokemon` que se desea establecer.
     */
    public void setResults(List<Pokemon> results) {
        this.results = results;
    }


}

