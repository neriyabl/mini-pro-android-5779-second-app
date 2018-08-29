package com.example.user.minipro5997secondapp.controller.Adapters;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.minipro5997secondapp.R;
import com.example.user.minipro5997secondapp.model.entities.ClientRequest;

import java.util.List;

import static com.example.user.minipro5997secondapp.model.entities.geocoding.getAddressFromLocation;

/**
 * RecycleView.Adapter
 * RecycleView.ViewHolder
 */
public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder>{

    private Context context;
    private List<ClientRequest> clientRequests;

    // constructor
    public RequestAdapter(Context context, List<ClientRequest> clientRequests) {
        this.context = context;
        this.clientRequests = clientRequests;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.request_item_view, parent, false);
        return new RequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        ClientRequest request = clientRequests.get(position);

        holder.name.setText(request.getName());

        Location dest = new Location(LocationManager.GPS_PROVIDER);
        dest.setLatitude(request.getDestinationLatitude());
        dest.setLongitude(request.getDestinationLongitude());
        getAddressFromLocation(dest, context, new GeocoderHandler(holder.destinetion));

        Location source = new Location(LocationManager.GPS_PROVIDER);
        source.setLatitude(request.getSourceLatitude());
        source.setLongitude(request.getSourceLongitude());
        getAddressFromLocation(source,context,new GeocoderHandler(holder.location));

       // holder.destinetion.setText(request.getDestination());
       // holder.location.setText(request.getSourceLatitude() + " " + request.getSourceLongitude());
    }

    @Override
    public int getItemCount() {
        return clientRequests.size();
    }

    class RequestViewHolder extends RecyclerView.ViewHolder{

        TextView name, location, destinetion;

        public RequestViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameItem);
            location = itemView.findViewById(R.id.locationItem);
            destinetion =itemView.findViewById(R.id.destinationItem);

        }
    }



    // handler to show the results of Geocoder in the UI:
    private class GeocoderHandler extends Handler {

        TextView view;

        GeocoderHandler(TextView v){
            view = v;
        }

        @Override
        public void handleMessage(Message message) {
            String result;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    result = bundle.getString("address");
                    break;
                default:
                    result = null;
            }
            //update view
            view.setText(result);
        }
    }

}
