package com.example.aswin.hope;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.aswin.info.R;

import java.util.Timer;
import java.util.TimerTask;

public class UpdateService extends Service implements SensorEventListener {
    NotificationCompat.Builder mBuilder;

    public Sensor mySensor, mySensor2;
    public SensorManager SM,SM2;
    private Timer mTimer;
    private boolean shouldDo=true;
    public int value=1;

    @Override
    public void onCreate() {
       super.onCreate();
        Log.d("what","erroring");
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReciever();
        registerReceiver(mReceiver, filter);
    }


    CountDownTimer countDownTimer = new CountDownTimer(1000,1000) {

        @Override
        public void onTick(long millisUntilFinished) {

                value=1-value;
        }

        @Override
        public void onFinish() {
            start();
        }
    }.start();
//    private final int FIVE_SECONDS = 5000;
//
//    public void scheduleSendLocation() {
//        handler.postDelayed(new Runnable() {
//            public void run() {
//
//                handler.postDelayed(this, FIVE_SECONDS);
//            }
//        }, FIVE_SECONDS);
//    }
//
//    Handler handler = new Handler();
//    handler.postDelayed


   /* public void showNotification() {


            mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.pirate)
                            .setContentTitle("An app has no Name")
                            .setContentText("Collecting....!!");

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
            mBuilder.build().flags = Notification.FLAG_ONGOING_EVENT;
            mNotificationManager.notify(0, mBuilder.build());

    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // Log.d("what","error");
       // if(intent!=null)
         //   Log.d("fish","fishing");
        SM = (SensorManager) getSystemService(SENSOR_SERVICE);
        SM2 = (SensorManager) getSystemService(SENSOR_SERVICE);

       /* mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.pirate)
                        .setContentTitle("An app has no Name")
                        .setContentText("Collecting....!!");

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mBuilder.build().flags=  Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(0, mBuilder.build());

        boolean screenOn = intent.getBooleanExtra("screen_state", false);*/
        boolean screenOff= ScreenReciever.screenOff;
        Log.d(String.valueOf(ScreenReciever.screenOff),"screen thing");


       // Log.d("debug","getting something");
        if (!screenOff) {
            // your code
            //Create our sensor manager
           // countDownTimer.start();
            //Accelometer sensor

            mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mySensor2 = SM2.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            //Register sensor listener
            SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
            SM2.registerListener(this, mySensor2, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // your code
           // countDownTimer.cancel();
            SM.unregisterListener(this);
            SM2.unregisterListener(this);
        }
        return START_STICKY;


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (value==1) {
            Log.d("final", "data changed");
            //Toast.makeText(this, "collecting", Toast.LENGTH_SHORT).show();
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            xText.setText("X : " + event.values[0]);
//            yText.setText("Y : " + event.values[1]);
//            zText.setText("Z : " + event.values[2]);


            }
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                // SensorManager.getRotationMatrixFromVector(mRotationMatrix,event.values);
//            xGyro.setText("X : " + event.values[0]);
//            yGyro.setText("Y : " + event.values[1]);
//            zGyro.setText("Z : " + event.values[2]);
            }
        }
    }

//    TimerTask timerTask = new TimerTask() {
//        @Override
//        public void run() {
//            Log.d("debug","runnning notification");
//            notifiy();
//        }
//    };

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



}


