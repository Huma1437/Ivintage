package com.ivintagesolutions.i_vintage.AssessorFiles;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivintagesolutions.i_vintage.StudentFiles.Contact;
import com.ivintagesolutions.i_vintage.StudentFiles.Login;
import com.ivintagesolutions.i_vintage.StudentFiles.Main_Exam;
import com.ivintagesolutions.i_vintage.StudentFiles.Profile;
import com.ivintagesolutions.i_vintage.R;
import com.ivintagesolutions.i_vintage.StudentFiles.ReadingMaterial;
import com.ivintagesolutions.i_vintage.StudentFiles.Splash_Screen;

public class AssessorDashboard extends AppCompatActivity {

    TextView logout;
    ImageView profile,Bassessed,Bcomplete,contact;

    AlertDialog alertDialog = null;
    NetworkChangeReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessor_dashboard);


        br = new NetworkChangeReceiver();

        if (haveNetworkConnection()) {

        } else {
            Toast.makeText(AssessorDashboard.this, "No Internet Connection!!! Please Enable Internet", Toast.LENGTH_LONG).show();
        }

        logout = (TextView)findViewById(R.id.logout);
        profile = (ImageView)findViewById(R.id.profile);
        Bassessed = (ImageView)findViewById(R.id.batchAss);
        Bcomplete = (ImageView)findViewById(R.id.batchCom);
        contact = (ImageView)findViewById(R.id.contact);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "You have successfully logout",
                        Toast.LENGTH_LONG).show();
                Splash_Screen.editor.remove("loginTestAssessor");
                Splash_Screen.editor.commit();

                Intent intent = new Intent(AssessorDashboard.this, Assessor_Login.class);
                startActivity(intent);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AssessorDashboard.this, AssessorProfile.class);
                startActivity(intent);

            }
        });

        Bassessed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AssessorDashboard.this, Batches_Assessed.class);
                startActivity(intent);

            }
        });


        Bcomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AssessorDashboard.this, Batches_Completed.class);
                startActivity(intent);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AssessorDashboard.this, Assessor_Contact.class);
                startActivity(intent);
            }
        });

    }


    public void dialogBoxForInternet() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AssessorDashboard.this);
        alertDialogBuilder.setTitle("No Internet Connection.");
        alertDialogBuilder
                .setMessage("Go to Settings to enable Internet Connectivity")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivityForResult(
                                        new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (haveNetworkConnection()) {
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }

            } else {

                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }

                dialogBoxForInternet();
            }
        }
    }


    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
}
