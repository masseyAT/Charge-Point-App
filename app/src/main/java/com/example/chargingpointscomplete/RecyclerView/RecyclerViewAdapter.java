package com.example.chargingpointscomplete.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chargingpointscomplete.ChargeDeviceSetup.ChargeDeviceComplete;
import com.example.chargingpointscomplete.R;
import java.util.ArrayList;
import java.util.List;

// Recycle view adapter for the list on the Results activity page
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final String TAG = "RecyclerViewAdapter";
    private OnItemClickListener listener;
    private List<ChargeDeviceComplete> mChargeDevices = new ArrayList<>();

    public RecyclerViewAdapter(LiveData<List<ChargeDeviceComplete>> chargeDeviceDBS) {
    }

    // A separate activity has been created which is linked below. This is when each item will be when placed
    // in the recycler view. This is to allow a uniform design.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    // This is to know which chargeDevices have been returned and gets the relevant information
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        String chargeDeviceRef = mChargeDevices.get(position).getChargeDeviceName();
        holder.chargeDeviceName.setText(chargeDeviceRef);
        String postcode = mChargeDevices.get(position).getPostCode();
        holder.postCode.setText(postcode);
        String connector = mChargeDevices.get(position).getConnector().get(0).getConnectorType();
        holder.connector.setText(connector);
    }

    @Override
    public int getItemCount() {
        return mChargeDevices.size();
    }

    public void setmChargeDevices(List<ChargeDeviceComplete> chargeDevices) {
        this.mChargeDevices = chargeDevices;
        notifyDataSetChanged();
    }

    public ChargeDeviceComplete getChargeAt(int position) {
        return mChargeDevices.get(position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView chargeDeviceName;
        private TextView postCode;
        private TextView connector;


        public ViewHolder(View itemView) {
            super(itemView);
            chargeDeviceName = itemView.findViewById(R.id.chargeDeviceName);
            postCode = itemView.findViewById(R.id.postCode);
            connector = itemView.findViewById(R.id.connector);

            // This is to pass on which chargeDevice has been clicked for
            // when the single details activity has run
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mChargeDevices.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ChargeDeviceComplete chargeDeviceDB);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
