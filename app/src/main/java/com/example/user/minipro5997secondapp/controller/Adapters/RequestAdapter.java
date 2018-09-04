package com.example.user.minipro5997secondapp.controller.Adapters;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
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
 *  <p>
 *  RecyclerView.Adapter
 *  RecyclerView.ViewHolder
 *  </p>
 *
 * <p>adapter to a requests list
 * @see <a href="https://developer.android.com/guide/topics/ui/layout/recyclerview">recycler view</a>
 */
public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private Context context;
    private List<ClientRequest> clientRequests;
    private Backend backend;
    private Driver driver;

    /**
     * the constructor take 3 params
     * @param context the parent context
     * @param clientRequests the list of all requests
     * @param driver the drive user details
     */
    public RequestAdapter(Context context, List<ClientRequest> clientRequests, Driver driver) {
        this.context = context;
        this.clientRequests = clientRequests;
        this.driver = driver;
        this.backend = BackendFactory.getBackend();
    }


    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a request item view and initialize with RequestViewHolder
        View v = LayoutInflater.from(context).inflate(R.layout.request_item_view, parent, false);
        return new RequestViewHolder(v);
    }

    /**
     * <p>initialize the view holder</p>
     *
     * @see <a href="https://developers.google.com/maps/documentation/urls/android-intents">open map by intent</a>
     * @see <a href="https://developer.android.com/training/contacts-provider/modify-data">adding contract</a>
     * @see <a href="https://developer.android.com/guide/topics/ui/dialogs">Dialogs</a>
     * @param holder the view holder request view
     * @param position the index of this holder in the recycler view
     */
    @Override
    public void onBindViewHolder(@NonNull final RequestViewHolder holder, int position) {
        //a single request from the list
        final ClientRequest request = clientRequests.get(position);

        //set the name text view
        holder.name.setText(request.getName());

        //set the destination location text view
        final Location dest = new Location(LocationManager.GPS_PROVIDER);
        dest.setLatitude(request.getDestinationLatitude());
        dest.setLongitude(request.getDestinationLongitude());
        // get the address for the destination
        // using the help class GeocoderHandler in this class
        // and the method getAddressFromLocation in geocoding class
        getAddressFromLocation(dest, context, new GeocoderHandler(holder.destination));
        // set on click open the location in the map
        holder.destination.setOnClickListener(v -> {
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", dest.getLatitude(), dest.getLongitude());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            context.startActivity(intent);
        });


        //set the source location
        final Location source = new Location(LocationManager.GPS_PROVIDER);
        source.setLatitude(request.getSourceLatitude());
        source.setLongitude(request.getSourceLongitude());
        // get the address for the source
        // using the help class GeocoderHandler in this class
        // and the method getAddressFromLocation in geocoding class
        getAddressFromLocation(source, context, new GeocoderHandler(holder.location));
        // set on click open the location in the map
        holder.location.setOnClickListener(v -> {
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", source.getLatitude(), source.getLongitude());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            context.startActivity(intent);
        });


        //set the phone
        holder.phone.setText(request.getPhone());

        //set on click to save new contact
        if (contactExists(context, request.getPhone())) {
            holder.addContact.setVisibility(View.GONE);
        } else {
            holder.addContact.setOnClickListener(v -> {
                // Creates a new Intent to insert a contact
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                // Sets the MIME type to match the Contacts Provider
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                // send the phone and name to the adding contract activity
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, request.getPhone())
                        .putExtra(ContactsContract.Intents.Insert.NAME, request.getName())
                        //this need to return the app after the user save the contract
                        .putExtra("finishActivityOnSaveCompleted", true);

                context.startActivity(intent);

                v.setClickable(false);
            });
        }

        //set on click to take drive
        holder.takeDrive.setOnClickListener(v -> {
            //open dialog to sure its ok
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(driver.getName() + " are you sure you want to take the drive" +
                    "\nfrom: " + holder.location.getText() + "\nto: " + holder.destination.getText())
                    .setTitle("Take Drive");

            builder.setPositiveButton("ok", (dialog, id) ->
                    backend.changeStatus(request.getId(),driver, ClientRequestStatus.close,context));

            builder.setNegativeButton("cancel", (dialog, id) -> { });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    /**
     * check if the phone already exist in the contracts
     * @see <a href="https://stackoverflow.com/questions/3505865/android-check-phone-number-present-in-contact-list-phone-number-retrieve-fr">stackoverflow</a>
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

    /**
     * the view holder class
     * to hold one request in the recycler
     */
    class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView name, location, destination, phone;
        Button addContact, takeDrive;

        /**
         * constructor to find the views in the itemView
         * @param itemView a single item View
         */
        RequestViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameItem);
            location = itemView.findViewById(R.id.locationItem);
            destination = itemView.findViewById(R.id.destinationItem);
            phone = itemView.findViewById(R.id.phoneItem);

            addContact = itemView.findViewById(R.id.saveContact);
            takeDrive = itemView.findViewById(R.id.takeDrive);
        }
    }

    /**
     * handler to show the results of Geocoder in the UI:
     */
    @SuppressLint("HandlerLeak")
    private class GeocoderHandler extends Handler {

        TextView view;

        //the view to handle
        GeocoderHandler(TextView v) {
            view = v;
        }

        //showing result geocoding
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
