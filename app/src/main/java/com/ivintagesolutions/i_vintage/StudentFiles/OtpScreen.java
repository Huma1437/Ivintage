package com.ivintagesolutions.i_vintage.StudentFiles;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivintagesolutions.i_vintage.Database.DBHelper;
import com.ivintagesolutions.i_vintage.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class OtpScreen extends Activity implements View.OnClickListener {

    TextView header,enrolnum,stdname;
    ImageView logout,desc;
    EditText otp;
    Button submit,back,stdpic_btn,asspic_btn;
    ImageView stdpic,asspic;
    String getOtp,getSPOtp,get_examID,get_stdID,get_sscID,get_tradeID,get_tbID,get_stdImage,get_AssImage,enrol_num,std_name;
    String base64_stdimg,base64_assimg;
    private static final String IMAGE_DIRECTORY = "/Ivintage_OtpScreen";
    private int  CAMERA = 2;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_IMAGE_2 = 2;
    Intent intent;
    private ProgressDialog pDialog;
    AlertDialog alertDialog=null;
    NetworkChangeReceiver br;
    final static String otp_api = "http://ivintage.in/PMKVY2/student/student_api/update_student_assessor_photos";
    private static final String API_KEY = "H0Ki2GOEeFZdAyZ0f299h1qzNTpkBEBA";

    Dialog dialog;
    String b_ID,b_name,j_role;
    TextView s_name,e_num,batch_id,bname,jobRole;
    DBHelper mydb;
    String sp_examID,sp_stdid,sp_base64,sp_stdimg,sp_sscID,sp_tradeID,sp_tbid,sp_apikey,sp_assb6img,sp_assimgname;
    ArrayList<String> Arr_StudentIDS;
    String get;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);

        desc = (ImageView) findViewById(R.id.desc);
        header = (TextView)findViewById(R.id.header);
        back = (Button) findViewById(R.id.backbtn);


        br = new NetworkChangeReceiver();
        mydb = new DBHelper(this);

        requestMultiplePermissions();

        stdpic_btn = (Button) findViewById(R.id.stdpic_upload);
        asspic_btn = (Button)findViewById(R.id.assimg_upload);
        stdpic = (ImageView) findViewById(R.id.stdpic);
        asspic = (ImageView) findViewById(R.id.assimage);
        enrolnum = (TextView) findViewById(R.id.enrolnum);
        stdname = (TextView) findViewById(R.id.stdname);

        get_examID = Splash_Screen.sh.getString("exam_id", null);
        get_stdID = Splash_Screen.sh.getString("student_id", null);
        get_sscID = Splash_Screen.sh.getString("ssc_id", null);
        get_tradeID = Splash_Screen.sh.getString("trade_id", null);
        get_tbID = Splash_Screen.sh.getString("tb_id", null);
        enrol_num = Splash_Screen.sh.getString("SDMS_enrolment_number", null);
        std_name = Splash_Screen.sh.getString("get_stdname", null);
        getSPOtp = Splash_Screen.sh.getString("exam_otp", null);

        Log.e("SP OTP SCREEN", ">>>>>>>>>>>>>>" + getSPOtp);


        b_name = Splash_Screen.sh.getString("tb_name", null);
        j_role = Splash_Screen.sh.getString("trade_title", null);
        b_ID = Splash_Screen.sh.getString("tb_nsdc_id", null);


        enrolnum.setText("SDMS ENROLLMENT NO. : "  + enrol_num);
      //  stdname.setText("STUDENT NAME : " + std_name);

        Log.e("SPVALUESOTP DELETEDMAIN", ">>>>>>>>>>" +  sp_examID + " " + "" + sp_stdid+ " " +  sp_base64 +
                " " + sp_stdimg +" " +sp_sscID+" " +sp_tradeID + " " + sp_tbid);

        header.setText("Exam OTP");

        Log.e("SP VALUES", ">>>>>>>> " + get_examID + " " + get_stdID + " " + get_sscID + " " + get_tradeID + " " + get_tbID);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OtpScreen.this,MainActivity.class);
                startActivity(intent);
            }
        });

        logout = (ImageView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "You have successfully logout",
                        Toast.LENGTH_LONG).show();
                Splash_Screen.editor.remove("loginTest");
                Splash_Screen.editor.commit();

                Intent intent = new Intent(OtpScreen.this,Login.class);
                startActivity(intent);
            }
        });


        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(OtpScreen.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialogue_desc);
                dialog.show();

                s_name = (TextView) dialog .findViewById(R.id.stdname);
                e_num = (TextView) dialog.findViewById(R.id.e_num);
                batch_id = (TextView) dialog .findViewById(R.id.batchId);
                bname = (TextView) dialog.findViewById(R.id.batchN);
                jobRole = (TextView) dialog.findViewById(R.id.jobrole);

                s_name.setText(std_name);
                e_num.setText(enrol_num);
                batch_id.setText(b_ID);
                bname.setText(b_name);
                jobRole.setText(j_role);

                Button close = (Button)dialog.findViewById(R.id.close);


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        otp = (EditText)findViewById(R.id.otp);
        submit = (Button)findViewById(R.id.submit);


        stdpic_btn.setOnClickListener(this);
        asspic_btn.setOnClickListener(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                getOtp = otp.getText().toString().trim();

                Log.e("OTP VALIDATION", ">>>>>>>>>" + getSPOtp + getOtp);

                if(getOtp.isEmpty()){

                    otp.setError("Enter OTP");
                    otp.requestFocus();
                }

                if(!getOtp.isEmpty() && !getOtp.equals(getSPOtp)){

                    otp.setError("Enter Correct OTP");
                    otp.setText("");
                    otp.requestFocus();
                    Toast.makeText(getApplicationContext(), "Enter Correct OTP",
                            Toast.LENGTH_LONG).show();
                }

                if(base64_stdimg == null || base64_assimg == null){
                    Toast.makeText(getApplicationContext(), "Please Upload the photos",
                            Toast.LENGTH_LONG).show();
                }

                if(getOtp.equals(getSPOtp) && !getOtp.isEmpty() && !get_examID.isEmpty() && !get_stdID.isEmpty() &&
                        base64_stdimg != null && base64_assimg != null && !get_sscID.isEmpty() && !get_tradeID.isEmpty() && !get_tbID.isEmpty()){

                    if(haveNetworkConnection()){
                        new ProfilePostRequest().execute();
                    }else{

                        mydb.adddata_OTP(get_examID,get_stdID,base64_stdimg,get_stdImage,get_sscID,get_tradeID,get_tbID,API_KEY,base64_assimg,get_AssImage);

                        Log.e("OTP DB DATA", ">>>>>>>>>>" + get_examID + " " +get_stdID + " " + base64_stdimg + " " +
                                get_stdImage + " " +get_sscID + " " + get_tradeID  + " " + get_tbID + API_KEY + " " + base64_assimg + " " + get_AssImage);

                        Intent intent = new Intent(OtpScreen.this, Test_Main.class);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }



                }else{



                }

            }
        });

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.stdpic_upload: {
                // do something for button 1 click

                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, PICK_IMAGE);
                break;
            }

            case R.id.assimg_upload: {
                // do something for button 2 click
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, PICK_IMAGE_2);
                break;
            }

        }
    }

    public void getOTPDBData(Context context){
        mydb = new DBHelper(context);
        Cursor data = mydb.getOTPData(mydb);

        if(data.getCount()>0 && data.moveToFirst())
            Arr_StudentIDS = new ArrayList<>();
        do{

            String studnt_id = data.getString(2);

            Arr_StudentIDS.add(studnt_id);

            Log.e("ARRAY SIZE OTP", ">>>>>>>>>>>"  + Arr_StudentIDS.size() + " " + studnt_id);

            for(int i = 0 ; i < Arr_StudentIDS.size() ; i++){

                get = Arr_StudentIDS.get(i);
            }

            Log.e("GET STUDNT ID OTP", ">>>>>>>>>>>" + get);
            getOnlyOneRow(context,get);
            try {
                Thread.sleep(2000);
                Log.e("THREAD","????????????????/" );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }while (data.moveToNext());

    }

    public void getOnlyOneRow(Context context,String val){
        mydb = new DBHelper(context);
        Cursor data = mydb.getOTPSingleVal(mydb,val);

        Log.e("GET SINGLE VAL OTP", ">>>STUDENT ID>>>" + val);

        if(data.getCount()>0 && data.moveToFirst())
            do{
                sp_examID = data.getString(1);
                sp_stdid = data.getString(2);
                sp_base64 = data.getString(3);
                sp_stdimg = data.getString(4);
                sp_sscID = data.getString(5);
                sp_tradeID = data.getString(6);
                sp_tbid = data.getString(7);
                sp_apikey = data.getString(8);
                sp_assb6img = data.getString(9);
                sp_assimgname = data.getString(10);

                Log.e("DO WHILE OTP", "STUDENT ID" + sp_stdid);

             /*  Log.e("Single DbVal OTP", ">>>>>>>>>>" +  sp_examID + " " + "" + sp_stdid+ " " +  sp_base64 +
                       " " + sp_stdimg +" " +sp_sscID+" " +sp_tradeID + " " + sp_tbid + " " + sp_assb6img + " " + sp_assimgname);*/

                final OTPPostRequestOffline fDownload = new OTPPostRequestOffline();
                fDownload.execute();

                // new OTPPostRequestOffline().execute();

            }while (data.moveToNext());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

       if(requestCode == PICK_IMAGE){

           Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
           stdpic.setImageBitmap(thumbnail);


           //reduce image size in disk. We can compress our bitmap by using compress method of Bitmap.
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           thumbnail.compress(Bitmap.CompressFormat.JPEG,100,baos);
           byte[] b = baos.toByteArray();
           base64_stdimg = Base64.encodeToString(b, Base64.DEFAULT);

           Log.e("STD BASE 64 IMAGE", ">>>>>>>>>>>>>" + base64_stdimg);

           String camera_img = saveImage(thumbnail);

           File f = new File(camera_img);
           get_stdImage = f.getName();

           Log.e("Student IMAGE Name", ">>>>>>>>>>>>>>>"  + " " + get_stdImage);

           Toast.makeText(OtpScreen.this, "Image Saved!", Toast.LENGTH_SHORT).show();
       } else if(requestCode == PICK_IMAGE_2){

           Bitmap thumbnail1 = (Bitmap) data.getExtras().get("data");
           asspic.setImageBitmap(thumbnail1);

           ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
           thumbnail1.compress(Bitmap.CompressFormat.JPEG,100,baos1);
           byte[] b1 = baos1.toByteArray();
           base64_assimg = Base64.encodeToString(b1, Base64.DEFAULT);

           Log.e("ass BASE 64 IMAGE", ">>>>>>>>>>>>>" + "\n" + base64_assimg);

           String ass_image = saveImage(thumbnail1);

           File f = new File(ass_image);
           get_AssImage = f.getName();

           Log.e("Assessor IMAGE", ">>>>>>>>>>>>>>>"  + " " + get_AssImage);

           Toast.makeText(OtpScreen.this, "Image Saved!", Toast.LENGTH_SHORT).show();

       }

    }




    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    public void dialogBoxForInternet() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OtpScreen.this);
        alertDialogBuilder.setTitle("No Internet Connection.");
        alertDialogBuilder
                .setMessage("Go to Settings to enable Internet Connectivity")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivityForResult(
                                        new Intent(android.provider.Settings.ACTION_SETTINGS),0);
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


    // for getting the questions from server
    private class ProfilePostRequest extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {

            // Showing progress dialog
            pDialog = new ProgressDialog(OtpScreen.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                if (jsonObject != null && jsonObject.getString("status_message") != null) {

                    String message = jsonObject.getString("status_message");  // Message

                    Log.e("MESSAGE", ">>>>>>>>>>>>>>>>" + message);

                    if (message.contains("Success - Photos uploaded successfully")) {

                        Toast.makeText(getApplicationContext(),
                                "Success - Photos uploaded successfully", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(OtpScreen.this, Test_Main.class);
                        startActivity(intent);


                    } else if (message.equals("Access Denied ! Authentication Failed")) {


                        Toast.makeText(getApplicationContext(),
                                "Access Denied", Toast.LENGTH_LONG).show();


                    }
                }else{


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        protected JSONObject doInBackground(Void... params) {

            return postJsonObject(otp_api, makingJson());

        }
    }

    private JSONObject makingJson() {

        JSONObject postDataParams = new JSONObject();

        try {

            //following parameters to the API
            postDataParams.put("key", API_KEY);
            postDataParams.put("exam_id", get_examID);
            postDataParams.put("student_id", get_stdID);
            postDataParams.put("student_image", base64_stdimg);
            postDataParams.put("student_image_name", get_stdImage);
            postDataParams.put("assessor_image", base64_assimg);
            postDataParams.put("assessor_image_name", get_AssImage);
            postDataParams.put("ssc_id", get_sscID);
            postDataParams.put("trade_id", get_tradeID);
            postDataParams.put("tb_id", get_tbID);

            HashMap<String ,String> params=new HashMap<String, String>();
            params.put("params",postDataParams.toString());

            Log.e("OTP SUBMITTED SERVER", ">>>>>>>>>>>."+ params);
            Log.e("BASE 64 params", ">>>>>>>>>>>>>>" + base64_stdimg + " " + base64_assimg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataParams;
    }



    public JSONObject postJsonObject(String url, JSONObject loginJobj) {
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String
            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);
            httpPost.setEntity(new StringEntity(loginJobj.toString(), "UTF-8"));

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else
                result = "Unable to retrieve any data from server";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;
        try {

            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


// OFFLINE WORKING TO POST DATA TO SERVER
    // for getting the questions from server
    private class OTPPostRequestOffline extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {


        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            try {
                if (jsonObject != null && jsonObject.getString("status_message") != null) {

                    String message = jsonObject.getString("status_message");  // Message


                    if (message.contains("Success - Photos uploaded successfully")) {

                        Log.e("OTP SUCCESS OFFLINE", ">>>>>>>>>>>>.." +sp_stdid+ " " + message);

                        mydb.deleteSingleOTPData(sp_stdid);
                        Log.e("DB VAL DEL OTP" , " ?????????????" + sp_stdid);

                    } else if (message.equals("Access Denied ! Authentication Failed")) {

                        Log.e("MSG FAILED", ">>>>>>>>>>>>.." + message);


                    }
                }else{


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        protected JSONObject doInBackground(Void... params) {

            return postJsonObjectOffline(otp_api, makingJsonoffline());

        }
    }


    private JSONObject makingJsonoffline() {

        JSONObject postDataParams = new JSONObject();

        try {

            //following parameters to the API
            postDataParams.put("key", API_KEY);
            postDataParams.put("exam_id", sp_examID);
            postDataParams.put("student_id", sp_stdid);
            postDataParams.put("student_image", sp_base64);
            postDataParams.put("student_image_name", sp_stdimg);
            postDataParams.put("assessor_image", sp_assb6img);
            postDataParams.put("assessor_image_name", sp_assimgname);
            postDataParams.put("ssc_id", sp_sscID);
            postDataParams.put("trade_id", sp_tradeID);
            postDataParams.put("tb_id", sp_tbid);

            HashMap<String ,String> params=new HashMap<String, String>();
            params.put("params",postDataParams.toString());

            Log.e("OTP SUb_ SERVER OFFLINE", ">>>>>>>>>>>."+ params);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataParams;
    }



    public JSONObject postJsonObjectOffline(String url, JSONObject loginJobj) {
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String
            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);
            httpPost.setEntity(new StringEntity(loginJobj.toString(), "UTF-8"));

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null) {
                result = convertInputStreamToStringOffline(inputStream);
            } else
                result = "Unable to retrieve any data from server";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;
        try {

            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;
    }

    private String convertInputStreamToStringOffline(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public void deleteSPValues() {

        mydb.deleteRecordfromOTP();

        mydb = new DBHelper(getApplicationContext());
        Cursor data = mydb.getOTPData(mydb);

        if(data != null) {

            Log.e("OTP SCREEN Data exists", ">>>>>>>>>>>.." + data);
        }else{

            Log.e("No values OTP Table", "?????????????" + data);
        }

        Log.e("OTP Data","OTP SCREEN" + "TABLE DELETED");
    }


    @Override
    public void onBackPressed() {
        Intent a = new Intent(this,MainActivity.class);
        finish();
        startActivity(a);
    }
}
