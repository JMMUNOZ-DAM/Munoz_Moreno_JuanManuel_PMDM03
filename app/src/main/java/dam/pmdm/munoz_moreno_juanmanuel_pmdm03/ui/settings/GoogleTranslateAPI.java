package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.ui.settings;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interfaz para definir los endpoints de la API de Google Translate.
 */
public interface GoogleTranslateAPI {

    /**
     * Endpoint para realizar traducciones a través de la API de Google Translate.
     * Este método realiza una solicitud para traducir un texto desde un idioma de origen a un idioma de destino.
     *
     * @param sourceLang El código del idioma de origen.
     * @param targetLang El código del idioma de destino.
     * @param text       El texto que se desea traducir.
     * @return Un objeto `Call<ResponseBody>` que contiene la respuesta de la traducción.
     */

    @GET("translate_a/single?client=gtx&dt=t")
    Call<ResponseBody> traducir(
            @Query("sl") String sourceLang, // Idioma de origen
            @Query("tl") String targetLang, // Idioma de destino
            @Query("q") String text         // Texto a traducir
    );
}