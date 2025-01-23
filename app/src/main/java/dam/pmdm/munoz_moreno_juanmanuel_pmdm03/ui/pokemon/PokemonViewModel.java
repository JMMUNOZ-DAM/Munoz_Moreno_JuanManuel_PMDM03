package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.ui.pokemon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PokemonViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PokemonViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Pokemon fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}