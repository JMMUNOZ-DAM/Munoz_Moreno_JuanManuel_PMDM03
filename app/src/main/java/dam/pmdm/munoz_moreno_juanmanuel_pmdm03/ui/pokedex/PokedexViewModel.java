package dam.pmdm.munoz_moreno_juanmanuel_pmdm03.ui.pokedex;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PokedexViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PokedexViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is pokedex fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}