package cl.santos.animales;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingService";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle FCM messages here.
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long-running job */ true) {
                // For long-running tasks (10 seconds or more), use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also, if you intend to generate your own notifications as a result of a received FCM
        // message, initiate that here.
        sendNotification(remoteMessage.getData());
    }

    @Override
    public void onNewToken(String token) {
        // Log del nuevo token
        Log.d(TAG, "Token actualizado: " + token);

        // Aquí puedes enviar el token a tu servidor, almacenarlo en SharedPreferences, etc.
    }

    private void handleNow() {
    }

    private void sendNotification(Map<String, String> data) {

    }

    private void scheduleJob() {

    }
}