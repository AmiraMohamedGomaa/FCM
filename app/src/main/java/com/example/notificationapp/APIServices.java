package com.example.notificationapp;
import com.example.notificationapp.data.NotificationBody;
import com.example.notificationapp.data.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIServices {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAOzUjm88:APA91bEo30-ZXd7GQb45KQnv6Gg2GseJUq1S8wa8bxADWy6oJzxOtPtZuorUw3bg-2nqvDq1S8FLUD74A8yHm_-U6-IBlL4aegCc5dBkGs67YgLFeoprEBRvzeT1QRemqXlguUIPb59h"
            }
    )
    @POST("fcm/send")
Call <Response>sendNotification(@Body NotificationBody body);
}
