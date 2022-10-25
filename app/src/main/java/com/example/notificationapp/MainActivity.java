package com.example.notificationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notificationapp.data.Data;
import com.example.notificationapp.data.NotificationBody;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private APIServices apiServices;
    int userId=0;
EditText tvUserCount;
Button btnSendNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvUserCount=findViewById(R.id.et_user_name);
        btnSendNotification=findViewById(R.id.add_data);
   /*     try {
            TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
                    users, "Orders");
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }*/

        btnSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId=Integer.parseInt(tvUserCount.getText().toString().trim());
            if(userId!=0){
                getUserToken(userId);
            }
            }
        });

    }




    public void getUserToken(int userId){
        ArrayList<String>tokens=new ArrayList<>();
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tokens").child(String.valueOf(userId)).child("token");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String token =snapshot.getValue(String.class);
                    tokens.add(token);
                    Log.d("Token", "onDataChange Token: "+token);
                    callFirebaseServer(tokens);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void callFirebaseServer(ArrayList<String> token){
        Data data=new Data("Unilever","Welcome to Unilever");
        NotificationBody notificationBody=new NotificationBody(token,data);
        apiServices=Client.getClient().create(APIServices.class);
        apiServices.sendNotification(notificationBody).enqueue(new Callback<com.example.notificationapp.data.Response>() {
            @Override
            public void onResponse(Call<com.example.notificationapp.data.Response> call, Response<com.example.notificationapp.data.Response> response) {
                if(response.code()==200){
                   if(response.body().success!=1){
                       Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_LONG).show();
                   }
                }
            }
            @Override
            public void onFailure(Call<com.example.notificationapp.data.Response> call, Throwable t) {

            }
        });
    }
}