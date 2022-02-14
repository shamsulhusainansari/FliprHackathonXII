package com.knoxtech.fliprhackathonxii;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.knoxtech.fliprhackathonxii.model.Data;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextInputEditText email, pass;
    LinearProgressIndicator progressIndicator;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        email = findViewById(R.id.editEmail);
        pass = findViewById(R.id.editPass);
        progressIndicator = findViewById(R.id.progress_horizontal);
        //authIdentity();
        findViewById(R.id.extended_fab).setOnClickListener(v -> {
            if (TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(pass.getText())) {
                Toast.makeText(MainActivity.this, "Please Enter Details!!!", Toast.LENGTH_SHORT).show();
            }else {
                progressIndicator.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(String.valueOf(email.getText()), String.valueOf(pass.getText()))
                        .addOnCompleteListener(MainActivity.this, task -> {
                            if (!task.isSuccessful()){

                                Toast.makeText(MainActivity.this, "Please Enter Correct Details", Toast.LENGTH_SHORT).show();
                                progressIndicator.setVisibility(View.GONE);
                            }else {
                                progressIndicator.setVisibility(View.GONE);
                                Log.e("ID",email.getText()+""+pass.getText());
                                authIdentity();
                                Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        findViewById(R.id.signUpBtn).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SignUpActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user != null) {
            authIdentity();
        }
    }

    private void authIdentity() {
        DocumentReference docRef = db.collection("Users").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            Data data = documentSnapshot.toObject(Data.class);
            assert data != null;
            String status =data.getStatus();
            if (status.equals("Dealer")){
                startActivity(new Intent(MainActivity.this,DealerActivity.class));
                finish();
            }else {
                startActivity(new Intent(MainActivity.this,DriverActivity.class));
                finish();
            }
        }).addOnFailureListener(e -> Log.e("FAIL",e.getMessage()));
    }
}