package com.example.user.minipro5997secondapp.controller;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.user.minipro5997secondapp.R;
import com.example.user.minipro5997secondapp.controller.Adapters.RequestAdapter;
import com.example.user.minipro5997secondapp.model.backend.Backend;
import com.example.user.minipro5997secondapp.model.backend.BackendFactory;
import com.example.user.minipro5997secondapp.model.entities.ClientRequest;
import com.example.user.minipro5997secondapp.model.entities.Driver;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class RequestsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    View view;
    RecyclerView recyclerView;
    RequestAdapter adapter;
    Spinner filter;
    Backend backend = BackendFactory.getBackend();
    Driver driver;
    RequestTask mAsyncTask;

    @SuppressLint("ValidFragment")
    RequestsFragment(Driver driver) {
        this.driver = driver;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.requests_layout, container, false);
        // ----- set the recycle view -----
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        getActivity().setTitle("MY REQUESTS");

        adapter = new RequestAdapter(view.getContext(), backend.getAllRequest(), driver);
        recyclerView.setAdapter(adapter);


        mAsyncTask = new RequestTask();

        // ---- set the filter ----
        filter = view.findViewById(R.id.spinner);
        String[] items = new String[]{"100", "50", "25", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        filter.setAdapter(adapter);

        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Location location = new Location("gps");
                location.setLatitude(31.77);
                location.setLongitude(35.177);
                // backend.getRequest(location,100,Double.parseDouble(items[position]))
//                List request = backend.getAllRequest();
//                recyclerView.setAdapter(new RequestAdapter(view.getContext(), request, driver));
                Location location1[] = new Location[1];
                location1[0] = location;
                mAsyncTask.execute(location1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    class RequestTask extends AsyncTask<Location, List<ClientRequest>, Void> {
        List<ClientRequest> requests;

        @Override
        protected Void doInBackground(Location... locations) {
            requests = backend.getRequest(locations[0], 100);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            recyclerView.setAdapter(new RequestAdapter(view.getContext(), requests, driver));
        }
    }


    ///     --------------------------------------------------------------------          /////////
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
}
