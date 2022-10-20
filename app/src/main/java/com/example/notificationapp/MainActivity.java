package com.example.notificationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.notificationapp.data.User;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //EditText etName;
   // Button addUser;
    int userId = 0;
    DatabaseReference reference, reference2;
    User user;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> users = new ArrayList<>();
  //  private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      // etName = findViewById(R.id.et_user_name);
        //addUser = findViewById(R.id.add_data);
        listView = findViewById(R.id.users_data);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1
                , users);
        listView.setAdapter(arrayAdapter);
        user = new User();

      /*  firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                   @Override
                                 public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                      String name = "";
                                      users.clear();
                                      for (QueryDocumentSnapshot dataSnapshot : value) {
                                             name = dataSnapshot.getString("name");
                                              users.add(dataSnapshot.getString("name"));
                                               }
                                               Toast.makeText(getApplicationContext(), "counter: " + users.size(), Toast.LENGTH_LONG).show();
                                               arrayAdapter.notifyDataSetChanged();
                                               notification(name);
                                                                      }
                                               });*/



      reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userId = (int) snapshot.getChildrenCount();
                    String name = "";
                    users.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        //name = dataSnapshot.getValue().toString();
                        users.add(dataSnapshot.getValue().toString());

                    }
                    Toast.makeText(getApplicationContext(), "counter: " + users.size(), Toast.LENGTH_LONG).show();
                    arrayAdapter.notifyDataSetChanged();
                    notification();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



/*       reference.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
              String name=etName.getText().toString();
              if(name.isEmpty()){
                  Toast.makeText(getApplicationContext(),"Fill Name",Toast.LENGTH_LONG).show();
              }else{

              }
                //  notification();
           }


           @Override
           public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot snapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });*/

      /*  LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("FirebaseMessagingServices"));*/


      /*addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill Name", Toast.LENGTH_LONG).show();
                } else {
                    user.setName(name);
                    reference.child(String.valueOf(userId + 1)).setValue(user);
                    // users.add(name);
                    //listView.deferNotifyDataSetChanged();
                  //  notification(name);

                }
            }
        });*/


    }

    private void notification() {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channelId",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "channelId")
                        .setContentTitle("Unilever")
                        .setContentText("Notify Data changed")
                        .setSmallIcon(R.drawable.ic_notifiy)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, notificationBuilder.build());


        // Since android Oreo notification channel is needed.


    }

/*    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("notification");
             setToken.setText(message);
            // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    };*/

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(mMessageReceiver );
    }




}