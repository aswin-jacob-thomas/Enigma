package com.example.aswin.hope;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import com.example.aswin.info.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class UpdateService extends Service implements SensorEventListener,LocationListener {
    NotificationCompat.Builder mBuilder;

    public Sensor mySensor, mySensor2;
    public SensorManager SM,SM2;
    private Timer mTimer;
    private boolean shouldDo=true;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    public int value=1;
    public DatabaseHandler db = new DatabaseHandler(this);
    public String data;
    public Double locationlat;
    public Double locationlong;
    public Location location;
    public float accx;
    public float accy;
    public float accz;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    public static int no =0;

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    LocationManager mLocationManager;


    @Override
    public void onCreate() {
       super.onCreate();
        Log.d("what","erroring");
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReciever();
        registerReceiver(mReceiver, filter);
    }


    CountDownTimer countDownTimer = new CountDownTimer(100000,5000) {

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
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);

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
//        if(screenOff) {
//            List<Security> security = db.getAllSecurity();
//
//            for (Security cn : security) {
//                String log = "Id: " + cn.getTime() + " ,ACCX: " + cn.getAccx() + " ,ACCY: " + cn.getAccy()+" ,ACCZ: " + cn.getAccz();
//                // Writing Contacts to log
//                //Log.d("fourth",cn.getAccz());
//                String logi = "y is this"+cn.getAccz();
//                Log.d("Name: ", logi);
//                Security fi = db.getSecurity(10);
//                String shi = "shi" + fi.getAccz();
//                Log.d("thisi shit",shi);
//            }
//        }
        if(screenOff){
            no++;
            //Cursor res = db.getAllSecurity();
            //StringBuffer buffer = new StringBuffer();
//            int i=0;
//            while (res.moveToNext()) {
//                i++;
//                buffer.append("TIME:"+ res.getString(0));
//                buffer.append("ACCX :"+ res.getString(1));
//                buffer.append("ACCY:"+ res.getString(2));
//                buffer.append("ACCZ :"+ res.getString(3)+"\n");
//            }
           // res.close();
           //Log.d("hihi",buffer.toString());
            //Log.d("counting",String.valueOf(i));
            //Log.d("Achodaa",String.valueOf(db.getSecurityCount()));

        }

       // Log.d("debug","getting something");
        if (!screenOff) {
            // your code
            //Create our sensor manager
           // countDownTimer.start();
            //Accelometer sensor

            mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mySensor2 = SM2.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
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

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public void appendLog(String text)
    {
        File sdCard = Environment.getExternalStorageDirectory();
        Log.d("pllzzz","what the heck");
        File logFile = new File(sdCard.getAbsolutePath()+"/loging.txt");
        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            Log.d("what is this",logFile.getAbsolutePath());
            buf.append(text);
            buf.newLine();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (value==1) {
            Log.d("final", "data changed");
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");    //will give the value of the current weekday
            Date d = new Date();
            String dayOfTheWeek = sdf.format(d);


            SimpleDateFormat railwaytime = new SimpleDateFormat("dd/MM/yy HH:mm:ss.SSS");
            String timenow = railwaytime.format(new Date());

            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();
            timenow=timenow.substring(9);
           //data = "Time : "+timenow;
           // data +=" "+"DayOfTheWeek : "+dayOfTheWeek;
            data=timenow;
            data+=","+dayOfTheWeek;
            long time = System.currentTimeMillis();
            Log.d("current time",Long.toString(time));

            //db.addSecurity(new Security(45,"7",dayOfTheWeek,"10"));
            //today.monthDay Day of the month (1-31)
            // today.month Month (0-11)
            // today.year Year
            //today.format("%k:%M:%S")


            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

               // db.addSecurity(new Security(String.valueOf(System.currentTimeMillis()),dayOfTheWeek,String.valueOf(event.values[0]),String.valueOf(event.values[1])
                 //
                //                          ,String.valueOf(event.values[2])));
                accx=event.values[0];
                accy=event.values[1];
                accz=event.values[2];



//                data = data + " accx : "+ event.values[0]+ " accy : "+ event.values[1]+" accz : "+ event.values[2];
//            xText.setText("X : " + event.values[0]);
//            yText.setText("Y : " + event.values[1]);
//            zText.setText("Z : " + event.values[2]);


            }
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                //data = data + " accx : "+ accx+ " accy : "+accy+ " accz : "+accz+" gyrox : "+ event.values[0]+ " gyroy : "+ event.values[1]+" gyroz : "+ event.values[2];
               Location myLocation = getLastKnownLocation();
                Log.d("locaitontest",Double.toString(myLocation.getLongitude()));
//                data+=" latitude: "+myLocation.getLatitude()+" longitude: "+myLocation.getLongitude();
//                appendLog(data);
                data+=","+accx+","+accy+","+accz+","+event.values[0]+","+event.values[1]+","+event.values[2]+","+myLocation.getLatitude()+","+myLocation.getLongitude()
                                    +","+no;
                appendLog(data);
//                // SensorManager.getRotationMatrixFromVector(mRotationMatrix,event.values);
//            xGyro.setText("X : " + event.values[0]);
//            yGyro.setText("Y : " + event.values[1]);
//            zGyro.setText("Z : " + event.values[2]);
            }
            //location=getLocation();



//            ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
//// The first in the list of RunningTasks is always the foreground task.
//            ActivityManager.RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
//
//            String foregroundTaskPackageName = foregroundTaskInfo .topActivity.getPackageName();
//            PackageManager pm = this.getPackageManager();
//            PackageInfo foregroundAppPackageInfo = null;
//            try {
//                foregroundAppPackageInfo = pm.getPackageInfo(foregroundTaskPackageName,0);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//            //pm.getPackageInfo(foregroundTaskPackageName, 0);
//            String foregroundTaskAppName = foregroundAppPackageInfo.applicationInfo.loadLabel(pm).toString();
//// The first in the list of RunningTasks is always the foreground task.

            //ActivityManager manager = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);

            //List<ActivityManager.RunningAppProcessInfo> tasks = manager.getRunningAppProcesses();

            //Log.i("current_app",tasks.get(0).processName);

            //Log.d("ganee",getActivity());

        }
    }

//    TimerTask timerTask = new TimerTask() {
//        @Override
//        public void run() {
//            Log.d("debug","runnning notification");
//            notifiy();
//        }
//    };

    public String getActivity(){

        String currentApp="sorry!!!";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            @SuppressWarnings("WrongConstant")
            UsageStatsManager usm = (UsageStatsManager) getSystemService("usagestats");
            Log.d("im in here","not");
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                    time - 1000 * 1000, time);
            if (appList != null && appList.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(),
                            usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(
                            mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager) getBaseContext().getSystemService(ACTIVITY_SERVICE);
            currentApp = am.getRunningTasks(1).get(0).topActivity .getPackageName();

        }
        return currentApp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public Location getLocation() {

        Location location=this.location;
        try {
            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
//            isNetworkEnabled = locationManager
//                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

//            if (!isGPSEnabled && !isNetworkEnabled) {
//                // no network provider is enabled
//            } else {
//                this.canGetLocation = true;
//                // First get location from Network Provider
//                if (isNetworkEnabled) {
//                    locationManager.requestLocationUpdates(
//                            LocationManager.NETWORK_PROVIDER,
//                            MIN_TIME_BW_UPDATES,
//                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//
//                    Log.d("Network", "Network");
//                    if (locationManager != null) {
//                        location = locationManager
//                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//
//                        if (location != null) {
//                            latitude = location.getLatitude();
//                            longitude = location.getLongitude();
//                        }
//                    }
//                }

            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {

                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                Log.d("GPS Enabled", "GPS Enabled");
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                        Log.d("HOPE", "getting something");

                        locationlat = location.getLatitude();
                        locationlong = location.getLongitude();
                        Log.d("lati", Double.toString(locationlat));
                        Log.d("langi", Double.toString(locationlong));
                    } else {
                        Log.d("shit", "shit is happ");
                    }

                } else {
                    Log.d("too much shit", "too much shti");
                }
            }
        }


         catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }



    @Override
    public void onLocationChanged(Location location) {
//        if(location==null)
//            Log.d("fish","its not gonna work");
//        locationlat = location.getLatitude();
//        locationlong=location.getLongitude();
//        Log.d("latitude",Double.toString(location.getLatitude()));
//        Log.d("longitude",Double.toString(location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude","enable");
    }




    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}


