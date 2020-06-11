package com.example.chargingpointscomplete.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chargingpointscomplete.ChargeDeviceSetup.Connector;
import com.example.chargingpointscomplete.R;
import java.util.ArrayList;

// This is the recycle view on the single charge point details screen
// I have passed through the relevent information needed and assigned them to the TextViews
public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {
    ArrayList<Connector> connectors;
    Context context;
    int images[];

    public DetailsAdapter(Context context, ArrayList<Connector> connectors, int img[] ){
        this.connectors = connectors;
        this.context = context;
        this.images = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.details_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String connectorType = connectors.get(position).getConnectorType();
        holder.connectorType.setText(connectorType);
        String ratedOutputKw = connectors.get(position).getRatedOutputkW();
        holder.ratedOutputKw.setText("Rated output "+ratedOutputKw+" kw");
        String chargeMethod = connectors.get(position).getChargeMethod();
        holder.chargeMethod.setText(chargeMethod);
        String inService = connectors.get(position).getChargePointStatus();
        holder.inService.setText("In service: "+inService);

        // Small visual clue to go with the status check , helps stand out more to user
        String statusPic = connectors.get(position).getChargePointStatus();
        if( statusPic.equals(inService)){
            holder.imageView.setImageResource(R.drawable.greentick);
        }
        else
        {
            holder.imageView.setImageResource(R.drawable.redcross);
        }
    }

    @Override
    public int getItemCount() {
        return connectors.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        TextView connectorType, ratedOutputKw, chargeMethod, inService;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // TextViews linked up from details_item.xml
            connectorType = itemView.findViewById( R.id.connector_type);
            ratedOutputKw = itemView.findViewById(R.id.ratedOutputKw);
            chargeMethod = itemView.findViewById(R.id.chargeMethod);
            inService = itemView.findViewById(R.id.inService);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
