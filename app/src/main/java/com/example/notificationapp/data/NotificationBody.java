package com.example.notificationapp.data;

import android.provider.ContactsContract;

import java.util.ArrayList;

public class NotificationBody {
    ArrayList<String>registration_ids;
    Data notification;

    public NotificationBody(ArrayList<String>registration_ids, Data notification) {
        this.registration_ids=registration_ids;
        this.notification = notification;
    }
}

