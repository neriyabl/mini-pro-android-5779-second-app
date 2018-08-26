package com.example.user.minipro5997secondapp.controller.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.minipro5997secondapp.R;
import com.example.user.minipro5997secondapp.model.entities.ClientRequest;

import java.util.List;

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
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.request_item_view , null);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        ClientRequest request = clientRequests.get(position);

        holder.name.setText(request.getName());
        holder.destinetion.setText(request.getDestination().toString());
        holder.location.setText(request.getSourceLatitude() + " " + request.getSourceLongitude());
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
}
