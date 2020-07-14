package app.iislearning.notif;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Locale;

import app.iislearning.MainActivity;
import app.iislearning.MyClasses;
import app.iislearning.R;

public class MyFirebaseInstanceIdService extends FirebaseMessagingService  {
    @Override
    public void onMessageReceived(RemoteMessage message) {
        super.onMessageReceived(message);
        Context context = this;



        /**/

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel  = new NotificationChannel("Channel1","ChannelForOffers", NotificationManager.IMPORTANCE_HIGH);
            channel.setShowBadge(true);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }




        NotificationCompat.Builder notification = new NotificationCompat.Builder(MyFirebaseInstanceIdService.this,"Channel1");

            Intent notificationIntent;
            if(message.getNotification().getTitle().equals("New Class Available"))
            {
               // notificationIntent = new Intent(context, MyClasses.class);
                return;
            }
            else if(message.getNotification().getTitle().equals("New Update Is Available"))
            {
                String url = "https://play.google.com/store/apps/details?id=app.iislearning";
                notificationIntent = new Intent(Intent.ACTION_VIEW);
                notificationIntent.setData(Uri.parse(url));
            }
            else
            {
                notificationIntent = new Intent(context, MainActivity.class);
            }

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
            notification
                    .setSmallIcon(R.drawable.ico_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ico_school))
                    .setContentIntent(intent)
                    .setContentTitle(message.getNotification().getTitle())
                    .setContentText(message.getNotification().getBody())
                    .addAction(R.drawable.ico_notification,"Mark as read",intent)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_MAX);








        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification.build());









    }


    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("newToken", token);
        //Add your token in your sharepreferences.
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fcm_token", token).apply();
    }


    //Whenewer you need FCM token, just call this static method to get it.
    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fcm_token", "empty");
    }


}
