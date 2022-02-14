package com.knoxtech.fliprhackathonxii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.knoxtech.fliprhackathonxii.Adapter.DataAdapter;
import com.knoxtech.fliprhackathonxii.Adapter.DriverAdapter;
import com.knoxtech.fliprhackathonxii.model.Data;

public class DriverActivity extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference notebookRef = db.collection("BOOKING");
    private DriverAdapter front_adapter;
    FirebaseAuth mAuth;
    FirebaseUser user;
    MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        toolbar = findViewById(R.id.topAppBar);
        Query query = notebookRef.orderBy("docId", Query.Direction.DESCENDING).whereEqualTo("driverId",user.getUid());
        FirestoreRecyclerOptions<Data> options = new FirestoreRecyclerOptions.Builder<Data>()
                .setQuery(query, Data.class)
                .build();
        front_adapter = new DriverAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.history_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(front_adapter);


        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.signOut) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DriverActivity.this,MainActivity.class));
                finish();
            }
            return false;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        front_adapter.startListening();

    }
    @Override
    protected void onStop() {
        super.onStop();
        front_adapter.stopListening();
    }
}