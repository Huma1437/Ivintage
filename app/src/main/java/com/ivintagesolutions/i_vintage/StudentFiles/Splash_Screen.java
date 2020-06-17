package com.ivintagesolutions.i_vintage.StudentFiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ivintagesolutions.i_vintage.AssessorFiles.AssessorDashboard;
import com.ivintagesolutions.i_vintage.AssessorFiles.Assessor_Login;
import com.ivintagesolutions.i_vintage.R;

public class Splash_Screen extends Activity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    static final int LOCATION_REQUEST = 1;

    private Handler handler;
    private long startTime, currentTime, finishedTime = 0L;
    private int duration = 20000 / 4;// 1 character is equal to 1 second. if want to
    // reduce. can use as divide
    // by 2,4,8
    private int endTime = 0;
    public static String str_login_test,ass_login_test;

    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        sh = getSharedPreferences("myprefe", 0);
        editor = sh.edit();

        // check here if user is login or not
        str_login_test = sh.getString("loginTest", null);
        ass_login_test = sh.getString("loginTestAssessor", null);

        Log.e("LOGIN TEST", ">>>>" + str_login_test + ass_login_test);

        nextactivity(1);

    }


    public void nextactivity(final int requestCode) {

        if (requestCode == LOCATION_REQUEST) {
            handler = new Handler();
            startTime = Long.valueOf(System.currentTimeMillis());
            currentTime = startTime;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    currentTime = Long.valueOf(System.currentTimeMillis());
                    finishedTime = Long.valueOf(currentTime)
                            - Long.valueOf(startTime);

                    if (finishedTime >= duration + 30) {
                        //
                    } else {
                        endTime = (int) (finishedTime / 250);// divide this by

                        handler.postDelayed(this, 10);
                    }
                }
            }, 10);


//            flipit(image);
            /****** Create Thread that will sleep for 5 seconds *************/
            Thread background = new Thread() {
                public void run() {

                    try {
                        // Thread will sleep for 5 seconds
                        sleep(2 * 1000);

                        if(str_login_test == null && ass_login_test == null){
                            Log.e("BOTH VALUES NULL", ">>>>>>>>>>" + str_login_test + ass_login_test);
                            Intent intentblack = new Intent(getApplicationContext(), Login.class);
                            startActivity(intentblack);
                        }else  if ((str_login_test != null && !str_login_test.toString().trim().equals(""))) {

                            Log.e("STDNT TRUE", ">>>>>>>>>>>>>." + str_login_test + ass_login_test);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        } else  if ((ass_login_test != null && !ass_login_test.toString().trim().equals(""))) {

                            Log.e("ASS TRUE", ">>>>>>>>>>>>>." + ass_login_test + str_login_test);

                            Intent intent = new Intent(getApplicationContext(), AssessorDashboard.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }


                            /*if ((str_login_test != null && !str_login_test.toString().trim().equals(""))) {

                                Log.e("STDNT TRUE", ">>>>>>>>>>>>>." + str_login_test + ass_login_test);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            } else
                            {
                                Log.e("STDNT FALSE", ">>>>>>>>>>>>>." + ass_login_test + str_login_test);

                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);

                            }*/



                       /* if ((ass_login_test != null && !ass_login_test.toString().trim().equals(""))) {

                            Log.e("ASS TRUE", ">>>>>>>>>>>>>." + ass_login_test + str_login_test);

                            Intent intent = new Intent(getApplicationContext(), AssessorDashboard.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        } else
                        {
                            Log.e("ASS FALSE", ">>>>>>>>>>>>>." + ass_login_test + str_login_test);

                            Intent intent = new Intent(getApplicationContext(), Assessor_Login.class);
                            startActivity(intent);

                        }*/
                        //Remove activity
                        finish();

                    } catch (Exception e) {

                    }
                }
            };

            // start thread
            background.start();


        }

    }

    public void Internet_pemission() {

        //Checks Internet Connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            nextactivity(1);
        } else {
            nextactivity(1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Internet_pemission();


            } else

            {

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
