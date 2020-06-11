package com.example.chargingpointscomplete;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    // When doing searches I didn't get round to putting the
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        final double latitude = intent.getDoubleExtra("latitude", 0);
        final double longitude = intent.getDoubleExtra("longitude", 0);

        setContentView(R.layout.activity_search);

        final Spinner connectorSpinner = findViewById(R.id.spinnerConnectorType);
        final Spinner mileageSpinner = findViewById(R.id.spinnerMileageRadius);
        Button searchButton = findViewById(R.id.buttonn);
        ImageView icon = findViewById(R.id.cpIcon);

        String[] spinnerConnectorType = new String[]{
                "Type 2 Mennekes (IEC62196)","Type 2 Combo (IEC62196) DC", "JEVS G105 (CHAdeMO) DC",
                "Type 1 SAEJ1772 (IEC 62196)", "Type 3 Scame (IEC62196)", "Type 2 Tesla (IEC62196) DC",
                "3-pin Type G (BS1363)","Commando 2P+E (IEC60309)",
                "Commando 3P+N+E (IEC60309)"};

        String[] spinnerMileageRadius = new String[]{
                "10", "20", "30", "50", "100", "200"
        };


        ArrayAdapter<String> adapterConnector = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerConnectorType);
        adapterConnector.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        connectorSpinner.setAdapter(adapterConnector);

        ArrayAdapter<String> adapterMileage = new ArrayAdapter<>(this, R.layout.spinner_item, spinnerMileageRadius);
        adapterMileage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mileageSpinner.setAdapter(adapterMileage);

        final String[] connectorToReturn = {"0"};
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The number I am changing the connector types too are what I need to enter for the @get api request,
                // which are preset by the data set
                String mileageSelection = mileageSpinner.getSelectedItem().toString();
                String spinnerSelection = connectorSpinner.getSelectedItem().toString();
                System.out.println(spinnerSelection);

                if (Objects.equals(spinnerSelection, "3-pin Type G (BS1363)")) {
                    connectorToReturn[0] = "3";
                }
                if (Objects.equals(spinnerSelection, "JEVS G105 (CHAdeMO) DC")) {
                    connectorToReturn[0] = "4";
                }

                if (Objects.equals(spinnerSelection, "Type 1 SAEJ1772 (IEC 62196)")) {
                    connectorToReturn[0] = "5";
                }

                if (Objects.equals(spinnerSelection, "Type 2 Mennekes (IEC62196)")) {
                    connectorToReturn[0] = "6";
                }

                if (Objects.equals(spinnerSelection, "Type 3 Scame (IEC62196)")) {
                    connectorToReturn[0] = "7";
                }

                if (Objects.equals(spinnerSelection, "Type 2 Combo (IEC62196) DC")) {
                    connectorToReturn[0] = "15";
                }

                if (Objects.equals(spinnerSelection, "Type 2 Tesla (IEC62196) DC")) {
                    connectorToReturn[0] = "16";
                }

                if (Objects.equals(spinnerSelection, "Commando 2P+E (IEC60309)")) {
                    connectorToReturn[0] = "17";
                }

                if (Objects.equals(spinnerSelection, "Commando 3P+N+E (IEC60309)")) {
                    connectorToReturn[0] = "18";
                }


                Intent i = new Intent(SearchActivity.this, ResultsActivity.class);
                i.putExtra("connID", connectorToReturn);
                i.putExtra("mileageRadius", mileageSelection);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });

    }
}
