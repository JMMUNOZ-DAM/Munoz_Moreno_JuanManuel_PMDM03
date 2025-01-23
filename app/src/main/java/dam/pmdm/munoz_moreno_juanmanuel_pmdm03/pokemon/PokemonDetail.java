package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.pokemon;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Clase que representa los detalles de un Pokémon, incluyendo su nombre, ID, peso, altura y tipos asociados.
 */
public class PokemonDetail {
    /**
     * El nombre del Pokémon.
     */
    private String name;
    /**
     * El ID del Pokémon.
     */
    private Integer id;
    /**
     * El peso del Pokémon en unidades específicas.
     */
    private Integer weight;
    /**
     * La altura del Pokémon en unidades específicas.
     */
    private Integer height;
    /**
     * Una lista de objetos que representan los tipos del Pokémon.
     */
    @SerializedName("types")
    private List<PokemonType> types;

    /**
     * Obtiene el nombre del Pokémon.
     *
     * @return El nombre del Pokémon.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el ID del Pokémon.
     *
     * @return El ID del Pokémon.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Obtiene el peso del Pokémon.
     *
     * @return El peso del Pokémon.
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * Obtiene la altura del Pokémon.
     *
     * @return La altura del Pokémon.
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * Obtiene la lista de tipos asociados al Pokémon.
     *
     * @return Una lista de objetos `PokemonType` que representan los tipos del Pokémon.
     */
    public List<PokemonType> getTypes() {
        return types;
    }

    /**
     * Clase interna que representa los tipos de un Pokémon.
     */
    public static class PokemonType {
        /**
         * El slot en el que el tipo aparece (generalmente relacionado con posiciones específicas).
         */
        @SerializedName("slot")
        private Integer slot;
        /**
         * Información del tipo, que incluye el nombre y URL del tipo.
         */
        @SerializedName("type")
        private TypeInfo type;

        /**
         * Obtiene el slot del tipo del Pokémon.
         *
         * @return El número del slot en el que aparece el tipo.
         */
        public Integer getSlot() {
            return slot;
        }

        /**
         * Obtiene la información del tipo del Pokémon.
         *
         * @return Un objeto `TypeInfo` con detalles del tipo.
         */
        public TypeInfo getType() {
            return type;
        }

        /**
         * Clase interna que contiene la información del tipo (nombre y URL).
         */
        public static class TypeInfo {
            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public String getUrl() {
                return url;
            }
        }
    }
}
