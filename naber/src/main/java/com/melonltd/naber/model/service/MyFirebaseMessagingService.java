package com.melonltd.naber.model.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.melonltd.naber.R;
import com.melonltd.naber.view.common.BaseActivity;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
//    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
//    public static final String FCM_PARAM = "picture";
//    private static final String CHANNEL_NAME = "FCM";
//    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private int numMessages = 0;

//    private static List<Identity> USER_IDENTITY = Identity.getUserValues();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Log.i(TAG, Tools.JSONPARSE.toJson(remoteMessage.getData()));

        Map<String, String> map = remoteMessage.getData();
//        Identity identity = Identity.of(map.get("identity"));
//        Identity currentIdentity = Identity.of(SPService.getIdentity());
//        if (USER_IDENTITY.containsAll(Lists.newArrayList(identity, currentIdentity))) {
//            sendNotification(map);
//        } else if (Lists.newArrayList(identity, currentIdentity).contains(Identity.SELLERS)) {
//            sendNotification(map);
//        }
        sendNotification(map);
    }

    private void sendNotification(Map<String, String> data) {
//        Bundle bundle = new Bundle();
//        bundle.putString(FCM_PARAM, data.get(FCM_PARAM));

        Intent intent = new Intent(this, BaseActivity.class);
//        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        Bitmap restaurantLogo = null;
//        try {
//            restaurantLogo = BitmapFactory.decodeStream(new URL(data.get("icon")).openConnection().getInputStream());
//        } catch (IOException e) {
////            e.printStackTrace();
//        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "1")
                .setContentTitle(data.get("title"))
                .setContentText(data.get("message"))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
//                .setContentInfo("Hello")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.naber_icon_logo_reverse))
                .setColor(getResources().getColor(R.color.colorAccent))
                .setLights(Color.RED, 1000, 300)
//                .setDefaults(Notification.DEFAULT_VIBRATE)
//                .setNumber(1)
                .setSmallIcon(R.drawable.ic_notif_eca_small);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle(data.get("title")).bigText(data.get("message")));
        }


        // 額外通知設定
        Notification note = notificationBuilder.build();
        // 通知 震動
//        if (SPService.getNotifyShake()) {
//            note.defaults |= Notification.DEFAULT_VIBRATE;
//        }
//        // 通知 聲音
//        if (SPService.getNotifySound()) {
//            note.defaults |= Notification.DEFAULT_SOUND;
//        }
        // 通知 燈光
        note.defaults |= Notification.DEFAULT_LIGHTS;

//        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
//        style.setBigContentTitle(data.get("title"));
//        style.bigText(data.get("message"));
//        style.setSummaryText(context.getString(R.string.app_name));


        // 通知 震動
        if (SPService.getNotifyShake()) {
            note.vibrate = new long[]{100, 200, 300, 400, 500};
//            note.setVibrate(new long[]{100, 200, 300, 400, 500});
        }
        // 通知 聲音
        if (SPService.getNotifySound()) {
            note.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        }

//        try {
//            String picture = data.get("picture");
//            if (picture != null && !"".equals(picture)) {
//                URL url = new URL(picture);
//                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bigPicture));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("1", CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
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

        assert notificationManager != null;
        notificationManager.notify(numMessages, note);
    }

}
