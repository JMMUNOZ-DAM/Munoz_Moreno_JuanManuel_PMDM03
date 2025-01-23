package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.ui.settings;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Clase encargada de realizar traducciones utilizando la API de Google Translate.
 */
public class Traductor {

    // Instancia de la API de Google Translate
    private final GoogleTranslateAPI api;

    /**
     * Constructor que configura la comunicación con la API de Google Translate.
     * Se utiliza Retrofit para realizar las solicitudes HTTP.
     */
    public Traductor() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://translate.googleapis.com/") // Base URL de la API
                .addConverterFactory(GsonConverterFactory.create()) // Conversor JSON
                .build();

        api = retrofit.create(GoogleTranslateAPI.class);
    }

    /**
     * Método para realizar la traducción del texto.
     * Traduce siempre del inglés al español.
     *
     * @param text El texto que se desea traducir.
     * @param callback El callback que manejará la respuesta de la traducción.
     */
    public void traducir(String text, Callback<ResponseBody> callback) {
        String sourceLang = "en"; // Idioma de origen
        String targetLang = "es"; // Idioma de destino

        // Realiza la solicitud usando Retrofit
        api.traducir(sourceLang, targetLang, text).enqueue(callback);
    }
}