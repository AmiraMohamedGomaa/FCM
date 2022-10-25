package com.example.notificationapp;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class App extends Application {
    DatabaseReference reference;
    int id=0;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ids", "onCreate:ids "+id);

        reference = FirebaseDatabase.getInstance().getReference().child("Tokens");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    id = (int) snapshot.getChildrenCount();
                    Toast.makeText(getApplicationContext(),""+ id,Toast.LENGTH_LONG).show();}}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }
            // Get new FCM registration token
            String token = task.getResult();
           reference.child(String.valueOf(id + 1)).child("token").setValue(token);
            //  DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Users");

        });

       // retrieveToken();

    }

    public void retrieveToken(){
        reference = FirebaseDatabase.getInstance().getReference().child("Tokens").child(String.valueOf(id + 1));
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }
            // Get new FCM registration token
            String token = task.getResult();
            reference.child("token").setValue(token);
            //  DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Users");

        });

    }

    public int getChildCount(){
        reference = FirebaseDatabase.getInstance().getReference().child("Tokens");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    id = (int) snapshot.getChildrenCount();
                    Toast.makeText(getApplicationContext(),""+ id,Toast.LENGTH_LONG).show();
                    retrieveToken();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return id;
    }
}
