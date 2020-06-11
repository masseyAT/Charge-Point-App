package com.example.chargingpointscomplete.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.chargingpointscomplete.Api.ChargeDao;
import com.example.chargingpointscomplete.ChargeDeviceSetup.ChargeDeviceComplete;
import com.example.chargingpointscomplete.ChargeDeviceSetup.Converters;

@Database(entities = {ChargeDeviceComplete.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ChargeDatabase extends RoomDatabase {

    private static ChargeDatabase instance;
    public abstract ChargeDao chargeDao();

    public static synchronized ChargeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ChargeDatabase.class, "charge_database.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
