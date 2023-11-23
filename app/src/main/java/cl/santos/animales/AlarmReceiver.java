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

import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Obtener el identificador único de la alarma
        int alarmId = intent.getIntExtra("ALARM_ID", 0);

        // Lógica para mostrar la notificación y enviar notificación FCM
        String title = "¡Alimenta a tu mascota!";
        String message = "Es momento de alimentar a tu mascota";
        mostrarNotificacion(context, title, message);
        // Enviar notificación mediante FCM
        enviarNotificacionFCM(context, title, message, alarmId);

        // Inicializar Firebase (si no lo has hecho ya)
        FirebaseApp.initializeApp(context);

        // Opcional: suscribirse a un tópico específico si es necesario
        FirebaseMessaging.getInstance().subscribeToTopic("alimento");
    }

    public void onMessageReceived(RemoteMessage remoteMessage, Context context, String title, String message) {
        // Si el mensaje contiene datos, estos se procesan en onMessageReceived
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Mensaje de datos recibido: " + remoteMessage.getData());
            // Aquí puedes procesar los datos y decidir cómo manejar la notificación
        }

        // Si el mensaje contiene una notificación, se muestra en la bandeja de notificaciones
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Mensaje de notificación recibido: " + remoteMessage.getNotification().getBody());
            mostrarNotificacion(context, title, message);
        }
    }

    private void enviarNotificacionFCM(Context context, String title, String message, int alarmId) {
        // Puedes utilizar FirebaseMessagingService para manejar la lógica de recepción de mensajes.
        // Aquí deberías agregar el código para enviar la notificación mediante FCM.

        // Crear un mapa para los datos del mensaje
        Map<String, String> data = new HashMap<>();
        data.put("title", title);
        data.put("message", message);
        data.put("alarmId", String.valueOf(alarmId));

        // Crear un mensaje de datos
        RemoteMessage.Builder remoteMessageBuilder = new RemoteMessage.Builder("tu_token_de_destino@FCMToken")
                .setData(data);

        // Enviar el mensaje al servidor de FCM
        FirebaseMessaging.getInstance().send(remoteMessageBuilder.build());
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