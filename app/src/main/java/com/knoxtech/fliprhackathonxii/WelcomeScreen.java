package com.knoxtech.fliprhackathonxii;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.knoxtech.fliprhackathonxii.model.Data;

public class WelcomeScreen extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        new Handler().postDelayed(() -> {
            if (user!=null){
                DocumentReference docRef = db.collection("Users").document(user.getUid());
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Data d = documentSnapshot.toObject(Data.class);
                        assert d != null;
                        if (d.getStatus().equals("Dealer")){
                            startActivity(new Intent(WelcomeScreen.this,DealerActivity.class));
                            finish();
                        }else {
                            startActivity(new Intent(WelcomeScreen.this,DriverActivity.class));
                            finish();
                        }
                    }
                });
            }else{
                Intent i = new Intent(WelcomeScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 5000);
    }
}
