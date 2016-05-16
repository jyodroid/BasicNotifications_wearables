package com.example.android.basicnotifications;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;

/**
 * The entry point to the BasicNotification sample.
 */
public class MainActivity extends Activity {
    /**
     * A numeric value that identifies the notification that we'll be sending.
     * This value needs to be unique within this app, but it doesn't need to be
     * unique system-wide.
     */
    public static final int NOTIFICATION_ID = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_layout);

    }

    /**
     * Send a sample notification using the NotificationCompat API.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotification(View view) {

        // BEGIN_INCLUDE(build_action)
        /** Create an intent that will be fired when the user clicks the notification.
         * The intent needs to be packaged into a {@link android.app.PendingIntent} so that the
         * notification service can fire it on our behalf.
         */
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://developer.android.com/reference/android/app/Notification.html"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // END_INCLUDE(build_action)

        // BEGIN_INCLUDE (build_notification)
        /**
         * Use NotificationCompat.Builder to set up our notification.
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        /** Set the icon that will appear in the notification bar. This icon also appears
         * in the lower right hand corner of the notification itself.
         *
         * Important note: although you can use any drawable as the small icon, Android
         * design guidelines state that the icon should be simple and monochrome. Full-color
         * bitmaps or busy images don't render well on smaller screens and can end up
         * confusing the user.
         */
        builder.setSmallIcon(R.drawable.ic_stat_notification);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true);

        /**
         *Build the notification's appearance.
         * Set the large icon, which appears on the left of the notification. In this
         * sample we'll set the large icon to be the same as our app icon. The app icon is a
         * reasonable default if you don't have anything more compelling to use as an icon.
         */
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));

        /**
         * Set the text of the notification. This sample sets the three most commononly used
         * text areas:
         * 1. The content title, which appears in large type at the top of the notification
         * 2. The content text, which appears in smaller text below the title
         * 3. The subtext, which appears under the text on newer devices. Devices running
         *    versions of Android prior to 4.2 will ignore this field, so don't use it for
         *    anything vital!
         */
        builder.setContentTitle("BasicNotifications Sample");
        builder.setContentText("Time to learn about notifications!");
        builder.setSubText("Tap to view documentation about notifications.");

        //Map Intent
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        String location = "Medellin";
        Uri geoUri = Uri.parse("geo:0,0?q="+Uri.encode(location));
        mapIntent.setData(geoUri);

        PendingIntent mapPendingIntent = PendingIntent.getActivity(this, 0, mapIntent, 0);

        //This action will run on the phone no on the wearable
//        builder.addAction(R.drawable.ic_stat_notification, "Location", mapPendingIntent);

        /**
         * Its possible to have different actions to appear on the wearable
         * For example using (ActionActivity for wearable)
         */

//        Intent actionIntent = new Intent(this, ActionActivity.class);
//        PendingIntent actionPendingIntent =
//                PendingIntent.getActivity(this, 0, actionIntent, pendingIntent.FLAG_UPDATE_CURRENT);
//
//        //Create the action
//        NotificationCompat.Action action =
//                new NotificationCompat.Action.Builder(
//                        R.drawable.ic_stat_notification, "Some text", actionPendingIntent).build();
//
//        //Build the notification and add the action via WearableExtender
//        Notification notificationCompat =
//                new NotificationCompat.Builder(getApplicationContext())
//                        .setSmallIcon(R.drawable.ic_stat_notification)
//                        .setContentTitle("A title")
//                        .setContentText("A text")
//                        .extend(new NotificationCompat.WearableExtender().addAction(action))
//                        .build();

        /**
         * WearableExtender includes a method to set high resolution images as background
         * Make sure is not a too big image beacuse you can overwhelm the memory in the small
         * wearables devices
         * Recommended:
         * -400x400 px images for static images
         * -640x400 px images for parallax where the left and right edges are used to simulat
         * background movement
         * -Store these resources in the drawable-nodpi directory to avoid resize
         */

//        NotificationCompat.WearableExtender wearableExtender =
//                new NotificationCompat.WearableExtender()
//                        .setHintHideIcon(true)
//                .setBackground(/*Bitmap image*/)

        /**
         * Notification style
         * We can ise the 'Big View' Style to display the notification with long event description that
         * May not fit the normal content text
         */

//        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
//        bigTextStyle.bigText("a very long text");
//
//        NotificationCompat.Builder notiBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_stat_notification)
//                        .setContentTitle("Event Title")
//                          /**
//                           * Android wear automatically uses this as the background image
//                           * Android framework only uses the 64x64 pixel version of the image
//                           * */
//                        .setLargeIcon(/*BITMAP ICON*/, /*background*/)
//                        ...
//                        .setStyle(bigTextStyle);

        // END_INCLUDE (build_notification)

        // BEGIN_INCLUDE(send_notification)

        /**
         * You can create several pages for notifications
         */
        //Create a big text style for second page of notification
        NotificationCompat.BigTextStyle secondPageTextStyle = new NotificationCompat.BigTextStyle();
        secondPageTextStyle.setBigContentTitle("Page 2").bigText("A lot of text");

        //Second page notification
        Notification secondPageNotification =
                new NotificationCompat.Builder(this).setStyle(secondPageTextStyle).build();

        //Extend the notification builder with the second notification
        builder.extend(new NotificationCompat.WearableExtender().addPage(secondPageNotification));

        /**
         * Send the notification. This will immediately display the notification icon in the
         * notification bar.
         */
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        // END_INCLUDE(send_notification)

        /**
         * Reply notification using voice commands
         */

//        //Create a key for string that's delivered in the action's intent
//        private static final String EXTRA_VOICE_REPLY = "extra_voice_reply";
//
//        String replyLabel = "String Label";
//        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
//                .setLabel(replyLabel)
//                .build();

//        //Create an intent for reply action
//        Intent replyIntent = new Intent(this, ReplyActivity.class);
//        PendingIntent replyPendingIntent =
//                PendingIntent.getActivity(this, 0, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
        //ReplyActivity class should contain next method that will handle intent
//        private CharSequence getMessageText(Intent intent){
//            Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
//            if (remoteInput != null){
//                return remoteInput.getCharSequence(EXTRA_VOICE_REPLY);
//            }
//            return null
//        }


//        //Create the reply action and add the remote input
//        NotificationCompat.Action action =
//                new NotificationCompat.Action.Builder(/*icon*/, "label text", replyPendingIntent)
//                .addRemoteInput(remoteInput)
//                .build();

        //Build the notification and add the action via WearableExtender (as line 120)
        //Issue the notification (send notification)

        /**
         * You can also give reply options instead of using voice reply
         */
//        String[] choices = /*Obtain array of chooses from array resource*/;
//        //The array resource is like
//        <resources>
//          <string-array name="reply_choices">
//                <item>yes</item>
//                <item>no</item>
//                <item>maybe</item>
//          </string-array>
//        </resources>
//        RemoteInput remote = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
//                .setLabel()
//                .setChoices(choices)
//                .build();

    }
}
