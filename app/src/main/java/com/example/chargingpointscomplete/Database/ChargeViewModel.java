package com.example.chargingpointscomplete.Database;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.chargingpointscomplete.ChargeDeviceSetup.ChargeDeviceComplete;
import java.util.List;

public class ChargeViewModel extends AndroidViewModel {
    private ChargeRepository repository;
    private LiveData<List<ChargeDeviceComplete>> allCharge;

    public ChargeViewModel(@NonNull Application application) {
        super(application);
        repository = new ChargeRepository(application);
        allCharge = repository.getAllChargeOrder();
    }

    public void insert(ChargeDeviceComplete chargeDeviceDB){
        repository.insert(chargeDeviceDB);
    }

    public void deleteAllCharge() {
        repository.deleteAllCharge();
    }


    public LiveData<List<ChargeDeviceComplete>> getAllChargeOrder() {
        return allCharge;
    }
}
