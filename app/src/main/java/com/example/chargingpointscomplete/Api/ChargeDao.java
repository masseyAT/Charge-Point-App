package com.example.chargingpointscomplete.Api;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chargingpointscomplete.ChargeDeviceSetup.ChargeDeviceComplete;

import java.util.List;

@Dao
public interface ChargeDao {

    @Insert
    void insert(ChargeDeviceComplete chargeDeviceDB);

    @Query("DELETE FROM charge_table")
    void deleteAllCharge();
    // Select all query that returns the closest charging points , limit to 20 for speed
    // Livedata will allow the list to update when new closer locations are calculated
    @Query("SELECT * FROM charge_table ORDER BY ((user_lat - lat) * ( user_lat - lat) + ( user_long - long) * (user_long - long)) asc LIMIT 20")
    LiveData<List<ChargeDeviceComplete>> getAllChargeOrder();
}
