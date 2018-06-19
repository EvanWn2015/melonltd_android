package com.melonltd.naberc.model.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.melonltd.naberc.util.Tools;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String FCM_PARAM = "picture";
    private static final String CHANNEL_NAME = "FCM";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private int numMessages = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("FCM", "onMessageReceived:  " + remoteMessage);
        Map<String, String> map  = remoteMessage.getData();
        Log.d("FCM", "onMessageReceived:  " + Tools.JSONPARSE.toJson(map));
    }

    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {
//        Bundle bundle = new Bundle();
//        bundle.putString(FCM_PARAM, data.get(FCM_PARAM));
//
//        Intent intent = new Intent(this, UserMainActivity.class);
//        intent.putExtras(bundle);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
//                .setContentTitle(notification.getTitle())
//                .setContentText(notification.getBody())
//                .setAutoCancel(true)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
//                .setContentIntent(pendingIntent)
//                .setContentInfo("Hello")
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
//                .setColor(getResources().getColor(R.color.colorAccent))
//                .setLights(Color.RED, 1000, 300)
//                .setDefaults(Notification.DEFAULT_VIBRATE)
//                .setNumber(++numMessages)
//                .setSmallIcon(R.drawable.naber_icon_logo);
//
//        try {
//            String picture = data.get(FCM_PARAM);
//            if (picture != null && !"".equals(picture)) {
//                URL url = new URL(picture);
//                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                notificationBuilder.setStyle(
//                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
//                );
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(
//                    getString(R.string.notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
//            );
//            channel.setDescription(CHANNEL_DESC);
//            channel.setShowBadge(true);
//            channel.canShowBadge();
//            channel.enableLights(true);
//            channel.setLightColor(Color.RED);
//            channel.enableVibration(true);
//            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
//
//            assert notificationManager != null;
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        assert notificationManager != null;
//        notificationManager.notify(0, notificationBuilder.build());
    }

}
