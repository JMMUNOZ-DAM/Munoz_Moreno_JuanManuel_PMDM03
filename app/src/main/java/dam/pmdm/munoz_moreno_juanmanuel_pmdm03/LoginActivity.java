package dam.pmdm.munoz_moreno_juanmanuel_pmdm03;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.content.Intent;

import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

/**
 * Actividad de inicio para manejar el proceso de autenticación de usuario.
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * Método llamado al crear la actividad.
     * Se encarga de aplicar el idioma guardado previamente y realiza el comportamiento básico de inicio.
     *
     * @param savedInstanceState Estado previamente guardado de la actividad, puede ser null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        applySavedLanguage(); // Aplica el idioma guardado
        super.onCreate(savedInstanceState);
    }

    /**
     * Método para aplicar el idioma guardado en las preferencias compartidas.
     */
    private void applySavedLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("misPreferencias", MODE_PRIVATE);
        String savedLanguage = sharedPreferences.getString("idiomaDefecto", "en");  // Por defecto el idioma es inglés
        setLocale(savedLanguage);  // Establece el idioma basado en las preferencias
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

    /**
     * Método para iniciar el flujo de autenticación del usuario.
     * Presenta una lista de proveedores de autenticación (Email y Google).
     */

    private void startSignin() {
        // Lista de proveedores de autenticación disponibles
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Crea e inicia el intent para la autenticación
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.pokemon)  // Logo a mostrar durante la autenticación
                .build();
        signInLauncher.launch(signInIntent);  // Lanza el proceso de inicio de sesión
    }

    /**
     * Lanzador de resultados para manejar la respuesta de la actividad de autenticación de Firebase.
     */
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    /**
     * Método para manejar el resultado de la autenticación.
     *
     * @param result Resultado de la autenticación devuelto por Firebase.
     */
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            goToMainActivity();
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            Toast.makeText(this, "Error login", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método para redirigir al HomeActivity después de una autenticación exitosa.
     */
    private void goToMainActivity() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    /**
     * Método llamado al inicio de la actividad.
     * Comprueba si el usuario ya está autenticado. Si lo está, lo redirige al HomeActivity.
     * Si no, inicia el proceso de autenticación.
     */
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            goToMainActivity();
        } else {
            startSignin();
        }
    }

}