package com.knoxtech.fliprhackathonxii;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.knoxtech.fliprhackathonxii.model.Data;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DealerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "UserListFragment";
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mMapView;
    private String truck_no,timeStamp;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    SearchView searchView;
    private String dealer_state,dealer_city,dealer_mob,dealer_weight,dealer_name,nofm,quantity;
    private int LOCATION_PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        searchView = findViewById(R.id.av_location);
        mMapView = findViewById(R.id.user_list_map);
        initGoogleMap(savedInstanceState);
        timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

        DocumentReference docRef = db.collection("Users").document(user.getUid());
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            Data data = documentSnapshot.toObject(Data.class);
            assert data != null;
            dealer_city = data.getCity();
            dealer_mob = data.getMobile();
            dealer_name=data.getName();
            nofm=data.getNature_of_material();
            quantity=data.getQuantity();
            dealer_state=data.getState();
            dealer_weight=data.getWeight();
        });


    }
    private void initGoogleMap(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(DealerActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(DealerActivity.this, "You have already granted this permission!",
                    Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission();
        }
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addresses = null;
                if (location!=null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(DealerActivity.this);
                    try {
                        addresses=geocoder.getFromLocationName(location,1);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    Address address = addresses.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TRUCKS").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document != null) {
                        String lat = document.getString("from_lat");
                        String from_long = document.getString("from_long");
                        truck_no = document.getString("truck_no");
                        Log.e("COORDINATES",lat+from_long);
                        assert lat != null;
                        assert from_long != null;
                        LatLng location = new LatLng(Double.parseDouble(lat), Double.parseDouble(from_long));
                        googleMap.addMarker(new MarkerOptions().position(location).title(truck_no).icon(BitmapDescriptorFactory.fromResource(R.drawable.truck3)));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));


                    }
                    googleMap.setOnMarkerClickListener(marker -> {

                        final Dialog dialog = new Dialog(DealerActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.custom_dialog);

                        ProgressBar progressBar = dialog.findViewById(R.id.dealer_progress);
                        TextView name,trans,truck_no,age,exp,capacity,from,to;
                        name = dialog.findViewById(R.id.heading_cast);
                        trans = dialog.findViewById(R.id.driver_trans);
                        truck_no = dialog.findViewById(R.id.driver_vehicle_no);
                        age = dialog.findViewById(R.id.driver_age);
                        exp = dialog.findViewById(R.id.driver_exp);
                        capacity = dialog.findViewById(R.id.driver_capacity);
                        from = dialog.findViewById(R.id.dfrom);
                        to = dialog.findViewById(R.id.dTo);

                        db.collection("TRUCKS")
                                .whereEqualTo("truck_no", marker.getTitle())
                                .get()
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                            name.setText(document1.getString("name"));
                                            trans.setText(document1.getString("transporter_name"));
                                            truck_no.setText(document1.getString("truck_no"));
                                            age.setText("AGE\t: "+ document1.getString("age")+"YEAR");
                                            exp.setText("EXPERIENCE\t: "+ document1.getString("experience")+"YEARS");
                                            capacity.setText("CAPACITY\t:"+ document1.getString("capacity")+"TON");
                                            from.setText(document1.getString("from"));
                                            to.setText(document1.getString("to"));
                                        }
                                    } else {
                                        dialog.dismiss();
                                        Log.d(TAG, "Error getting documents: ", task1.getException());
                                        Toast.makeText(DealerActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        dialog.show();
                        dialog.findViewById(R.id.btnCall).setOnClickListener(v -> {

                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            assert document != null;
                            callIntent.setData(Uri.parse("tel:" + document.getString("mobile")));
                            startActivity(callIntent);
                            dialog.dismiss();
                        });
                        dialog.findViewById(R.id.btnSubmit).setOnClickListener(v -> {
                            progressBar.setVisibility(View.VISIBLE);

                            Map<String, Object> docData = new HashMap<>();
                            docData.put("docId", timeStamp);
                            assert document != null;
                            docData.put("driverId", document.getString("uid"));
                            docData.put("dealerId", user.getUid());
                            docData.put("age", document.getString("age"));
                            docData.put("capacity", document.getString("capacity"));
                            docData.put("experience", document.getString("experience"));
                            docData.put("from", document.getString("from"));
                            docData.put("driver_name", document.getString("name"));
                            docData.put("driver_mob", document.getString("mobile"));
                            docData.put("to", document.getString("to"));
                            docData.put("transporter_name", document.getString("transporter_name"));
                            docData.put("truck_no", document.getString("truck_no"));
                            docData.put("status", "Pending");
                            docData.put("dealer_state", dealer_state);
                            docData.put("dealer_city", dealer_city);
                            docData.put("dealer_mob", dealer_mob);
                            docData.put("dealer_weight", dealer_weight);
                            docData.put("dealer_name",dealer_name);
                            docData.put("nature_of_material",nofm);
                            docData.put("quantity",quantity);

                            db.collection("BOOKING").document(timeStamp)
                                    .set(docData)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                        Toast.makeText(DealerActivity.this, "success", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        dialog.dismiss();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.w(TAG, "Error writing document", e);
                                        Toast.makeText(DealerActivity.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        dialog.dismiss();
                                    });

                        });
                        dialog.findViewById(R.id.cancel_button).setOnClickListener(v -> dialog.dismiss());
                        return false;
                    });
                }

            } else {
                Log.d(TAG, Objects.requireNonNull(task.getException()).getMessage());
            }
        });
        googleMap.setMyLocationEnabled(true);

        findViewById(R.id.bookingBtn).setOnClickListener(v -> startActivity(new Intent(DealerActivity.this,HistoryActivity.class)));
    }
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(DealerActivity.this,
                                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                //mMapView.onCreate(mapViewBundle);

                mMapView.getMapAsync(this);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}