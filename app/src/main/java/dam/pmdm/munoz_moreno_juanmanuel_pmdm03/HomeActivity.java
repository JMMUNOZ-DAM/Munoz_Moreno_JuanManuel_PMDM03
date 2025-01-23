package dam.pmdm.munoz_moreno_juanmanuel_pmdm03;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Locale;

import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.databinding.ActivityHomeBinding;

/**
 * Actividad principal para la pantalla de inicio de la aplicación.
 */
public class HomeActivity extends AppCompatActivity {
    // Vínculo con el diseño de la actividad generado por ViewBinding
    private ActivityHomeBinding binding;

    /**
     * Método llamado al crear la actividad.
     * Aplica el idioma guardado y configura la interfaz de usuario.
     *
     * @param savedInstanceState Estado previamente guardado de la actividad, puede ser null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applySavedLanguage();
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Configura las secciones principales de navegación como destinos de nivel superior
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_pokemon, R.id.navigation_pokedex, R.id.navigation_settings)
                .build();
        //// Obtener instancia del contexto
        Context context = getApplicationContext();


        // Obtén el controlador de navegación
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        // Vincula el BottomNavigationView con el NavController
        NavigationUI.setupWithNavController(navView, navController);
    }

    /**
     * Método para aplicar el idioma guardado en las preferencias compartidas.
     */
    private void applySavedLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("misPreferencias", MODE_PRIVATE);
        String savedLanguage = sharedPreferences.getString("idiomaDefecto", "en"); // Idioma por defecto: inglés
        setLocale(savedLanguage);
    }

    /**
     * Método para configurar el idioma utilizando el código del idioma.
     *
     * @param languageCode Código del idioma que se desea aplicar.
     */
    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}