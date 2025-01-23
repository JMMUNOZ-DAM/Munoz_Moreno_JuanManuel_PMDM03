package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.pokemon;

import com.google.gson.annotations.SerializedName;
import com.google.firebase.firestore.Exclude;


/**
 * Representa un Pokémon con sus características básicas.
 * Esta clase contiene información como el nombre, la URL, el dueño, la altura, el peso, los tipos, y otros detalles relacionados con los Pokémon.
 */
public class Pokemon {
    /**
     * El ID del Pokémon. Se extrae automáticamente de la URL.
     * Este atributo está excluido de la base de datos Firestore.
     */
    @SerializedName("id")
    private int id;
    /**
     * El nombre del Pokémon.
     */
    @SerializedName("name")
    private String name;
    /**
     * La URL donde se puede encontrar más información sobre el Pokémon.
     */
    private String url;
    /**
     * El nombre del dueño o entrenador del Pokémon.
     */
    private String owner;
    /**
     * La URL de la imagen del Pokémon.
     */
    private String image;
    /**
     * La altura del Pokémon.
     */
    private String height;
    /**
     * El peso del Pokémon.
     */
    private String weight;

    private String types;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * Obtiene los tipos del Pokémon.
     *
     * @return Los tipos del Pokémon.
     */
    public String getTypes() {
        return types;
    }

    /**
     * Establece los tipos del Pokémon.
     *
     * @param types Los nuevos tipos del Pokémon.
     */
    public void setTypes(String types) {
        this.types = types;
    }

    // Constructor vacío para Firestore
    public Pokemon() {
    }

    /**
     * Constructor para inicializar un Pokémon con nombre, URL y dueño.
     *
     * @param name  El nombre del Pokémon.
     * @param url   La URL del Pokémon.
     * @param owner El dueño o entrenador del Pokémon.
     */
    public Pokemon(String name, String url, String owner) {
        this.name = name;
        this.url = url;
        this.owner = owner;
    }

    /**
     * Constructor para inicializar un Pokémon con nombre, URL, dueño e imagen.
     *
     * @param name  El nombre del Pokémon.
     * @param url   La URL del Pokémon.
     * @param owner El dueño o entrenador del Pokémon.
     * @param image La URL de la imagen del Pokémon.
     */
    public Pokemon(String name, String url, String owner, String image) {
        this.name = name;
        this.url = url;
        this.owner = owner;
        this.image = image;
    }


    /**
     * Constructor para inicializar un Pokémon con nombre, URL, dueño, altura, peso y tipos.
     *
     * @param name   El nombre del Pokémon.
     * @param url    La URL del Pokémon.
     * @param owner  El dueño o entrenador del Pokémon.
     * @param height La altura del Pokémon.
     * @param weight El peso del Pokémon.
     * @param types  Los tipos del Pokémon.
     */
    public Pokemon(String name, String url, String owner, String height, String weight, String types) {
        this.name = name;
        this.url = url;
        this.owner = owner;
        this.height = height;
        this.weight = weight;
        this.types = types;
    }

    /**
     * Obtiene el nombre del Pokémon.
     *
     * @return El nombre del Pokémon.
     */

    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del Pokémon.
     *
     * @param name El nuevo nombre del Pokémon.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la URL del Pokémon.
     *
     * @return La URL del Pokémon.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL del Pokémon.
     *
     * @param url La nueva URL del Pokémon.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtiene el dueño o entrenador del Pokémon.
     *
     * @return El dueño del Pokémon.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Establece el dueño o entrenador del Pokémon.
     *
     * @param owner El nuevo dueño del Pokémon.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Obtiene el ID del Pokémon a partir de la URL.
     * Este método es excluido de la persistencia en Firestore.
     *
     * @return El ID del Pokémon extraído de la URL, o 0 si la URL es nula o vacía.
     */
    @Exclude
    public int getId() {
        if (url != null && !url.isEmpty()) {
            String[] segments = url.split("/");
            this.id = Integer.parseInt(segments[segments.length - 1]);
            return id;
        }
        return 0; // Valor predeterminado si no hay URL
    }

    /**
     * Obtiene la URL de la imagen del Pokémon.
     * Este método es excluido de la persistencia en Firestore.
     *
     * @return La URL de la imagen del Pokémon.
     */
    @Exclude
    public String getImage() {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + getId() + ".png";
    }

    /**
     * Establece la URL de la imagen del Pokémon.
     */
    public void setImage() {
        this.image = image;
    }

    /**
     * Representación en formato string del objeto Pokémon.
     *
     * @return Una cadena que representa el Pokémon.
     */
    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", owner='" + owner + '\'' +
                ", image='" + image + '\'' +
                ", id=" + id +
                '}';
    }
}