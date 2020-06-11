package com.example.chargingpointscomplete.Database;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.chargingpointscomplete.Api.ChargeDao;
import com.example.chargingpointscomplete.ChargeDeviceSetup.ChargeDeviceComplete;
import java.util.List;

// All database calls are done as asyncTask to speed up UI and create a better experience for user
public class ChargeRepository {
    private ChargeDao chargeDao;
    private LiveData<List<ChargeDeviceComplete>> allCharge;

    public ChargeRepository(Application application){
        ChargeDatabase database = ChargeDatabase.getInstance(application);
        chargeDao = database.chargeDao();
        allCharge = chargeDao.getAllChargeOrder();
    }

    public void deleteAllCharge(){
        new DeleteAllChargeAsyncTask(chargeDao).execute();
    }

    // Livedata list used on the this as it will keep returning the closest location when it has calculated it
    public LiveData<List<ChargeDeviceComplete>> getAllChargeOrder(){
        return  allCharge;
    }

    public void insert(ChargeDeviceComplete chargeDeviceDB){
        new InsertChargeAsyncTask(chargeDao).execute(chargeDeviceDB);
    }

    private static class InsertChargeAsyncTask extends AsyncTask<ChargeDeviceComplete, Void, Void> {
        private ChargeDao chargeDao;

        private InsertChargeAsyncTask(ChargeDao chargeDao){
            this.chargeDao = chargeDao;
        }
        @Override
        protected Void doInBackground(ChargeDeviceComplete... chargeDevices) {
            chargeDao.insert(chargeDevices[0]);
            return null;
        }
    }

    private static class DeleteAllChargeAsyncTask extends AsyncTask<Void, Void, Void> {
        private ChargeDao chargeDao;

        private DeleteAllChargeAsyncTask(ChargeDao chargeDao){
            this.chargeDao = chargeDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            chargeDao.deleteAllCharge();
            return null;
        }
    }
}
