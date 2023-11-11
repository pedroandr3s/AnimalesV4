package cl.santos.animales;

import androidx.core.app.NotificationCompat;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class NotificationHelper {
    private Context mContext;
    private NotificationManager mNotificationManager;
    private PendingIntent mPendingIntent;

    public NotificationHelper(Context context) {
        mContext = context;
    }

    // Método para mostrar una notificación
    public void createNotification(String title, String message, Intent intent) {
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        // Crear un PendingIntent para abrir la aplicación cuando se toque la notificación
        mPendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Crear la notificación
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "default")
                .setSmallIcon(R.drawable.dos)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(mPendingIntent)
                .setVibrate(new long[]{0, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setAutoCancel(true);

        mNotificationManager.notify(0, mBuilder.build());
    }
}
