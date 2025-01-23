package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.ui.settings;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.LoginActivity;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.R;
import dam.pmdm.munoz_moreno_juanmanuel_pmdm03.databinding.FragmentSettingsBinding;
import android.widget.Switch;
import java.util.Locale;

/**
 * Fragmento para la configuración de la aplicación.
 * Este fragmento maneja las acciones y vistas relacionadas con la configuración del usuario,
 * como cambiar el idioma, mostrar información sobre la aplicación, y cerrar sesión.
 */
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private Switch deleteSwitch;


    /**
     * Método que se llama cuando el fragmento es creado.
     * Aquí se inicializa el ViewModel, se aplican preferencias lingüísticas y se configuran los listeners.
     *
     * @param inflater           Inflater para el diseño del fragmento.
     * @param container          Contenedor donde se coloca el fragmento.
     * @param savedInstanceState Estado guardado del fragmento.
     * @return La vista del fragmento inflada.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        applySavedLanguage(); // Aplica el idioma antes de cargar los recursos
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Recuperar las preferencias compartidas para el fragmento
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("misPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Configura los listeners para cada opción
        root.findViewById(R.id.changeLanguage).setOnClickListener(v -> changeLanguage());
        root.findViewById(R.id.about_option).setOnClickListener(v -> showAbout());
        root.findViewById(R.id.logout_option).setOnClickListener(v -> logoutSession(root));
        deleteSwitch = root.findViewById(R.id.switch_delete_pokemon);

        // Recuperar estado del Switch
        boolean isSwitchOn = sharedPreferences.getBoolean("deleteSwitchState", false);
        deleteSwitch.setChecked(isSwitchOn);

        // Guardar el estado del Switch cuando cambie
        deleteSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("deleteSwitchState", isChecked);
            editor.apply();
            requireActivity().recreate(); // Recarga el activity para aplicar el cambio
        });
        return root;
    }

    /**
     * Muestra un cuadro de diálogo para permitir al usuario cambiar el idioma de la aplicación.
     * Al seleccionar un idioma, se guarda en las preferencias y se aplica el cambio.
     */
    private void changeLanguage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.changeLanguage));
        String[] languages = {getString(R.string.english), getString(R.string.spanish)};
        builder.setItems(languages, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String languageCode = (which == 0) ? "en" : "es";
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("misPreferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("idiomaDefecto", languageCode);  // Almacena el nuevo idioma
                editor.apply();

                // Aplicar los cambios en el sistema
                setLocale(languageCode);
                requireActivity().recreate(); // Recarga el activity para aplicar el cambio
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Aplica el idioma guardado en las preferencias del sistema.
     * El idioma se toma de las preferencias y se configura en la aplicación.
     */
    private void applySavedLanguage() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("misPreferencias", Context.MODE_PRIVATE);
        String savedLanguage = sharedPreferences.getString("idiomaDefecto", "en"); // Valor por defecto: inglés
        setLocale(savedLanguage);
    }

    /**
     * Configura el idioma de la aplicación utilizando un código de idioma.
     *
     * @param languageCode El código del idioma deseado (por ejemplo, "en" para inglés, "es" para español).
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
     * Muestra un cuadro de diálogo con información acerca de la aplicación.
     * Incluye detalles como el desarrollador y la versión actual.
     */
    private void showAbout(){
        // Crear un cuadro de diálogo (AlertDialog)
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.acerca_de));
        // Definir el contenido del cuadro de diálogo
        String message = getString(R.string.developer)+"\n"+getString(R.string.version);
        builder.setMessage(message)
                .setCancelable(true)  // Permitir cerrar el cuadro de diálogo presionando fuera de él
                .setPositiveButton(getString(R.string.closeAbout), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();  // Cerrar el cuadro de diálogo al presionar "Cerrar"
                    }
                });
        // Crear el cuadro de diálogo
        AlertDialog alert = builder.create();
        // Mostrar el cuadro de diálogo
        alert.show();
    }

    /**
     * Muestra un cuadro de diálogo para confirmar el cierre de sesión.
     * Permite al usuario confirmar si desea cerrar sesión.
     */
    private void logoutSession(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.tituloLogout));
        builder.setMessage(getString(R.string.msgLogout))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.buttonYes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cierre de sesión mediante Firebase Auth
                        AuthUI.getInstance()
                                .signOut(requireContext())  // Sign out usando el contexto del fragmento
                                .addOnCompleteListener(task -> goToLogin());
                        dialog.dismiss();  // Cierra el cuadro de diálogo después de la acción
                    }
                })
                .setNegativeButton(getString(R.string.buttonNo), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();  // Cierra el cuadro de diálogo sin realizar ninguna acción
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
    /**
     * Redirige al usuario a la actividad de inicio de sesión después de cerrar sesión.
     */
    private void goToLogin() {
        Intent i = new Intent(requireContext(), LoginActivity.class);
        startActivity(i);
        requireActivity().finish();  // Finaliza la actividad actual después del cambio
    }

    /**
     * Limpia la vista del fragmento cuando se destruye.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}