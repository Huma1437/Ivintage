package com.ivintagesolutions.i_vintage;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.ivintagesolutions.i_vintage.StudentFiles.Enrollment_Form;
import com.ivintagesolutions.i_vintage.StudentFiles.OtpScreen;
import com.ivintagesolutions.i_vintage.StudentFiles.Splash_Screen;
import com.ivintagesolutions.i_vintage.StudentFiles.Test_Main;

/**
 * Service to handle callbacks from the JobScheduler. Requests scheduled with the JobScheduler
 * ultimately land on this service's "onStartJob" method.
 * @author jiteshmohite
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkSchedulerService extends JobService implements
        ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = NetworkSchedulerService.class.getSimpleName();

    private ConnectivityReceiver mConnectivityReceiver;
    Test_Main test_main;
    Enrollment_Form ef;
    OtpScreen otpScreen;
    String getOfflineValue;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "Service created");
        mConnectivityReceiver = new ConnectivityReceiver(this);
        test_main = new Test_Main();
        ef = new Enrollment_Form();
        otpScreen = new OtpScreen();
        getOfflineValue = Splash_Screen.sh.getString("OfflineValue", null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "Service destroyed");
    }

    /**
     * When the app's MainActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return START_NOT_STICKY;
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "onStartJob" + mConnectivityReceiver);
        registerReceiver(mConnectivityReceiver, new IntentFilter(Constants.CONNECTIVITY_ACTION));
        Log.e("REGISTER RECEIVER", ">>>>>>>>>>>.." + mConnectivityReceiver);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG, "onStopJob" +mConnectivityReceiver);
        unregisterReceiver(mConnectivityReceiver);
        Log.e("UNREGISTER RECEIVER", ">>>>>>>>>>>>>" + mConnectivityReceiver);
        return true;
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        if (!MyApplication.isInterestingActivityVisible()) {
            String message = isConnected ? "Good! Connected to Internet" : "Sorry! Not connected to internet";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

            if(message.contains("Good! Connected to Internet")){

                Log.e("TestSch TRUE CASE", ">>>>>>>>>>>>>>>" + isConnected);
                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();

                ef.getDBData(getApplicationContext());
                otpScreen.getOTPDBData(getApplicationContext());
                test_main.callFromService(getApplicationContext());


            }else if(message.contains("Sorry! Not connected to internet")){

                Log.e("TestSch FALSE CASE", ">>>>>>>>>>>>>>>" + isConnected);
                Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_LONG).show();
            }

            Log.e("NETWORK MESSAGE", ">>>>>>>>>>>>>>>>>>>>>>" + message);
        }
    }
}
