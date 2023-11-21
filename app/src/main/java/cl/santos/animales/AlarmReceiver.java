package cl.santos.animales;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("alimento", "¡Alarma recibida!");

        String title = "¡Alarma!";
        String message = "Es hora de realizar el mantenimiento.";
        mostrarNotificacion(context.getApplicationContext(), title, message);

        // Enviar notificación mediante FCM
        enviarNotificacionFCM(context, title, message);
        // Inicializa Firebase
        FirebaseApp.initializeApp(context);

        // Opcional: suscríbete a un tópico específico si es necesario
        FirebaseMessaging.getInstance().subscribeToTopic("alimento");

    }

    private void enviarNotificacionFCM(Context context, String title, String message) {
        // Puedes utilizar FirebaseMessagingService para manejar la lógica de recepción de mensajes.
        // Aquí deberías agregar el código para enviar la notificación mediante FCM.
    }

    static void mostrarNotificacion(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Verificar si el dispositivo tiene Android Oreo o superior, ya que se necesita un canal de notificación.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("alarm_channel", "Alarm Channel", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "alarm_channel")
                .setSmallIcon(R.drawable._f415)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);

        notificationManager.notify(0, builder.build());
    }
}