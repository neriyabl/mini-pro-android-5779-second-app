package com.example.user.minipro5997secondapp.controller.Adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.minipro5997secondapp.R;
import com.example.user.minipro5997secondapp.model.backend.Backend;
import com.example.user.minipro5997secondapp.model.backend.BackendFactory;
import com.example.user.minipro5997secondapp.model.entities.ClientRequest;
import com.example.user.minipro5997secondapp.model.entities.ClientRequestStatus;
import com.example.user.minipro5997secondapp.model.entities.Driver;

import java.util.List;
import java.util.Locale;

import static com.example.user.minipro5997secondapp.model.entities.geocoding.getAddressFromLocation;

/**
 * RecycleView.Adapter
 * RecycleView.ViewHolder
 */
public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private Context context;
    private List<ClientRequest> clientRequests;
    private Backend backend;
    private Driver driver;

    // constructor
    public RequestAdapter(Context context, List<ClientRequest> clientRequests, Driver driver) {
        this.context = context;
        this.clientRequests = clientRequests;
        this.driver = driver;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.request_item_view, parent, false);
        return new RequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestViewHolder holder, int position) {
        final ClientRequest request = clientRequests.get(position);
        backend = BackendFactory.getBackend();

        //set the name
        holder.name.setText(request.getName());

        //set the destination location
        final Location dest = new Location(LocationManager.GPS_PROVIDER);
        dest.setLatitude(request.getDestinationLatitude());
        dest.setLongitude(request.getDestinationLongitude());
        getAddressFromLocation(dest, context, new GeocoderHandler(holder.destination));
        holder.destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", dest.getLatitude(), dest.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(intent);
            }
        });


        //set the source location
        final Location source = new Location(LocationManager.GPS_PROVIDER);
        source.setLatitude(request.getSourceLatitude());
        source.setLongitude(request.getSourceLongitude());
        getAddressFromLocation(source, context, new GeocoderHandler(holder.location));
        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", source.getLatitude(), source.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(intent);
            }
        });


        //set the phone
        holder.phone.setText(request.getPhone());

        //set on click to save new contact
        if (contactExists(context, request.getPhone())) {
            holder.addContact.setVisibility(View.GONE);
        } else {
            holder.addContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Creates a new Intent to insert a contact
                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    // Sets the MIME type to match the Contacts Provider
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, request.getPhone())
                            .putExtra(ContactsContract.Intents.Insert.NAME, request.getName())
                            .putExtra("finishActivityOnSaveCompleted", true);

                    context.startActivity(intent);

                    v.setClickable(false);
                }
            });
        }

        //set on click ti take drive
        holder.takeDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(driver.getName() + " are you sure you want to take the drive" +
                        "\nfrom: " + holder.location.getText() + "\nto: " + holder.destination.getText())
                        .setTitle("Take Drive");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        backend.changeStatus(request.getId(),driver, ClientRequestStatus.close,context);
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    /**
     * check if the phone already exist in the contracts
     *
     * @param context the activity context
     * @param number  the number to check
     * @return bool exist or not
     */
    private boolean contactExists(Context context, String number) {
        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return clientRequests.size();
    }

    class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView name, location, destination, phone;
        Button addContact, takeDrive;

        public RequestViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameItem);
            location = itemView.findViewById(R.id.locationItem);
            destination = itemView.findViewById(R.id.destinationItem);
            phone = itemView.findViewById(R.id.phoneItem);

            addContact = itemView.findViewById(R.id.saveContact);
            takeDrive = itemView.findViewById(R.id.takeDrive);
        }
    }

    // handler to show the results of Geocoder in the UI:
    private class GeocoderHandler extends Handler {

        TextView view;

        GeocoderHandler(TextView v) {
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
                    result = "can't parse the location";
            }
            //update view
            view.setText(result);
        }
    }

}
