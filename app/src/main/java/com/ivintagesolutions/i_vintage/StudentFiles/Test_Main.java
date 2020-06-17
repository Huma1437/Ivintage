package com.ivintagesolutions.i_vintage.StudentFiles;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ivintagesolutions.i_vintage.Database.DBHelper;
import com.ivintagesolutions.i_vintage.NetworkSchedulerService;
import com.ivintagesolutions.i_vintage.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.ivintagesolutions.i_vintage.StudentFiles.Splash_Screen.editor;


public class Test_Main extends AppCompatActivity implements View.OnClickListener{

    TextView header,duration,time_left,question,op1,op2,op3,op4,clear,ans,not_ans,not_visited,review_later;
    TextView hindi_question,hindi_op1,hindi_op2,hindi_op3,hindi_op4;
    Button back,previous,next,review,finish,view;
    ImageView logout,desc;

    AlertDialog alertDialog=null;
    NetworkChangeReceiver br;
    private ProgressDialog pDialog;
    int id=0, position=0;
    JSONObject reader;
    DBHelper mydb;
    String exam_StartTym,exam_EndTym,ip,exam_date;

    Dialog dialog;
    String b_ID,b_name,j_role,enrol_num,qns_string;
    TextView s_name,e_num,batch_id,bname,jobRole;

    String get_examID,get_stdname,get_stdID,get_stdImage,get_assImage,get_sscID,get_tradeID,get_tbID,get_snap,get_exmName,get_tradeCode,get_tradeTitle,get_enrollment_form;
    String  get_img_dir_src,get_img_dir, get_duration,nos_id,nos_code,nos_title,q_id,Question,typeofquestion,option_a,option_b,option_c,option_d;
    String get_hques,get_hop1,get_hop2,get_hop3,get_hop4;
    String path1,path2,path3,str_completePath;
    ArrayList<String> CompletePath;


    int assign_Qnum = 1;
    int assign_QnumOffline = 1;
    String get_asqn,get_asqnOffline;
    int get_totalqns,get_totalqnsOffline;
    final static String Questions_url = "http://ivintage.in/PMKVY2/student/student_api/get_exam_questions";
    final static String Answers_url = "http://ivintage.in/PMKVY2/student/student_api/main_result";
    final static String UPLOAD_URL = "http://ivintage.in/PMKVY2/student/student_api/upload_snapshots_api";
    String base64;
    String a16;
    private static final String API_KEY = "H0Ki2GOEeFZdAyZ0f299h1qzNTpkBEBA";

    String completedTime,time,get_selectedOpt;
    int numofQue,dynamicTimer;
    Handler handler;
    Long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int Hours, Seconds, Minutes, MilliSeconds ;
    private static final String FORMAT = "%02d:%02d:%02d";
    private CountDownTimer mCountDown;
    LinearLayout l1,l2,l3,l4;
    ImageView quesImage,op1_image,op2_image,op3_image,op4_image;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String text1,text2;


    String Image_url,OfflineValue,Image_urlOffline,E_name;
    String DbQnum,Dbq_id,DbSelectedOpt,m_review,notans,notvisit,qns,opa,opb,opc,opd,hqns,hopa,hopb,hopc,hopd;
    String option_Name,get_OPname,get_OP;
    int notVisited,not_answered,answered,reviewLater;
    int notVisitedoffline,not_answeredoffline,answeredoffline,reviewLateroffline;
    ArrayList<String> QuestionNumbers = new ArrayList<>();
    ArrayList<String> QuestionNumbersOffline = new ArrayList<>();
    public final static String DEBUG_TAG = "Test_Main";
    public Camera camera;
    public int cameraId = 0;
    ViewPaletteFragment viewPaletteFragment;
    String fileOutput,get_snames,get_sdate,img_name_date;
    ArrayList<String> SnapsNames;
    ArrayList<String> QuestionsID;
    ArrayList<String> SelectedAnswers;
    ArrayList<String> SnapsDate;
    ArrayList<String> Image_Path;
    ScrollView scrollView;
    String a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,aoptions,take_snap,getb64,getQid,get_Snapshots;
    ArrayList<String> newQues;
    ArrayList<String> getExactQnum;
    ArrayList<String> getDbsltAns;
    String Db_stdID,Db_nosID,Db_nosCode,Db_nosTitle,Db_qid,Db_ques,Db_op1,Db_op2,Db_op3,Db_op4,Db_hques,Db_hop1,
            Db_hop2,Db_hop3,Db_hop4,Db_sopt,Db_review,Db_na,Db_nv,Db_qnum;
    Context context;
    File fileDirectory;
    ArrayList<String> Arr_StudentIDS;
    String get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

        br = new NetworkChangeReceiver();
        mydb = new DBHelper(this);
        newQues = new ArrayList<>();

        if(haveNetworkConnection()){

        }else{
           /* String getQnsT = Splash_Screen.sh.getString("total_qns", null);
            get_totalqnsOffline = Integer.parseInt(getQnsT);*/
        }

        //Arraylist for storing selected Answers and snapshots date
        SelectedAnswers = new ArrayList<>();
        SnapsDate = new ArrayList<>();
        Image_Path = new ArrayList<>();
        CompletePath = new ArrayList<>();


        get_examID = Splash_Screen.sh.getString("exam_id", null);
        get_stdname = Splash_Screen.sh.getString("get_stdname", null);
        get_stdID = Splash_Screen.sh.getString("student_id", null);
        get_stdImage = Splash_Screen.sh.getString("student_image", null);
        get_assImage = Splash_Screen.sh.getString("assessor_image", null);
        get_sscID = Splash_Screen.sh.getString("ssc_id", null);
        get_tradeID = Splash_Screen.sh.getString("trade_id", null);
        get_tbID = Splash_Screen.sh.getString("tb_id", null);
        E_name = Splash_Screen.sh.getString("Exam_name", null);

        b_name = Splash_Screen.sh.getString("tb_name", null);
        j_role = Splash_Screen.sh.getString("trade_title", null);
        b_ID = Splash_Screen.sh.getString("tb_nsdc_id", null);
        enrol_num = Splash_Screen.sh.getString("SDMS_enrolment_number", null);


        Splash_Screen.editor.putString("get_tbID", get_tbID);

        get_snap = Splash_Screen.sh.getString("Snap", null);

        Log.e("SNAPSHOT", ">>>>>>>>>>>>>>>>>>>.." + get_snap);

        header = (TextView)findViewById(R.id.header);
        duration = (TextView) findViewById(R.id.duration);
        time_left = (TextView) findViewById(R.id.time_left);
        question = (TextView)findViewById(R.id.ques);
        op1 = (TextView) findViewById(R.id.op1);
        op2 = (TextView) findViewById(R.id.op2);
        op3 = (TextView) findViewById(R.id.op3);
        op4 = (TextView) findViewById(R.id.op4);

        //for displaying questions and ans in hindi
        hindi_question = (TextView)findViewById(R.id.hindi_ques);
        hindi_op1 = (TextView) findViewById(R.id.hindi_op1);
        hindi_op2 = (TextView) findViewById(R.id.hindi_op2);
        hindi_op3 = (TextView) findViewById(R.id.hindi_op3);
        hindi_op4 = (TextView) findViewById(R.id.hindi_op4);


        clear = (TextView) findViewById(R.id.clear);
        ans = (TextView) findViewById(R.id.cir1);
        not_visited = (TextView) findViewById(R.id.cir2);
        not_ans = (TextView) findViewById(R.id.cir3);
        review_later = (TextView)findViewById(R.id.cir4);


        previous = (Button) findViewById(R.id.pre);
        next = (Button)findViewById(R.id.next);
        review = (Button) findViewById(R.id.review);
        finish = (Button) findViewById(R.id.finish);
        view = (Button) findViewById(R.id.palatte);

        l1 = (LinearLayout)findViewById(R.id.op1Layout);
        l2 = (LinearLayout)findViewById(R.id.op2Layout);
        l3 = (LinearLayout) findViewById(R.id.op3Layout);
        l4 = (LinearLayout) findViewById(R.id.op4Layout);

        quesImage = (ImageView) findViewById(R.id.quesImg);
        op1_image = (ImageView) findViewById(R.id.optImg1);
        op2_image = (ImageView) findViewById(R.id.optImg2);
        op3_image = (ImageView) findViewById(R.id.optImg3);
        op4_image = (ImageView) findViewById(R.id.optImg4);

        scrollView = (ScrollView) findViewById(R.id.scrollView);

        //clear button click listener... clears selected option for that question.
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearAll();
            }
        });

        //click listener of each option
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);


        // Get connect mangaer
        final ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // check for wifi
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        // check for mobile data
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if( wifi.isAvailable() ) {
            //getting the ip address of the device/mobile
            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

            Log.e("WIFI IPADDRESSS",">>>>>>>>>>" +ip);

        } else if( mobile.isAvailable() ) {

            //Get the ip address of the device when connected to cellular network
            ip = getMobileIPAddress();

            Log.e("MOBILE DATA IPADDRESSS",">>>>>>>>>>" +ip);

        } else {
            //   networkStatus = "noNetwork";
        }


        //getting the value of question number from view pallette.
        if(get_asqn != null){
            //assigning the value of question number from pallette to qnum.
            assign_Qnum = Integer.parseInt(get_asqn);
            Log.e("GET_SELECTED_NUM",">>>>>>>>>>>>" +get_asqn);
        }

        //getting the value of question number from view pallette in OFFLINE WORKING.
        if(get_asqnOffline != null){
            //assigning the value of question number from pallette to qnum.
            assign_QnumOffline = Integer.parseInt(get_asqnOffline);
            Log.e("GET_SELECTED_NUM",">>>>>>>>>>>>" +get_asqnOffline);
        }

        //mark for review click listener
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(haveNetworkConnection()){

                    //check if student selected any option for that question...
                    //Allow to mark the question only if option is selected for it else not.
                    if(option_Name == null){

                        Toast.makeText(getApplicationContext(), "Select the option please", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean recordExists= mydb.checkIfRecordExist(mydb.TABLE_NAME ,mydb.COLUMN_1 ,Dbq_id);
                        if(recordExists)
                        {
                            //do your stuff
                            selectUpdateReview(Dbq_id,0,1);
                            Log.e("SELECTED", "??????????" + String.valueOf(1));

                            Log.e("RECORDEXIST",">>>>>>>>>.." + recordExists);

                        }else{


                        }

                        getUpdateOnAllClicks();

                        Toast.makeText(getApplicationContext(), "MARK CLICKED", Toast.LENGTH_SHORT).show();
                    }


                }else{

                    //check if student selected any option for that question...
                    //Allow to mark the question only if option is selected for it else not.
                    if(option_Name == null){

                        Toast.makeText(getApplicationContext(), "Select the option please", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean recordExists= mydb.checkIfRecordExistQns(mydb.QUESTIONS_TABLE ,mydb.q_col_5 ,Db_qid);
                        if(recordExists)
                        {
                            //do your stuff
                            selectUpdateReviewOffline(Db_qid,get_stdID,0,1);
                            Log.e("SELECTED", "??????????" + String.valueOf(1));

                            Log.e("RECORDEXIST",">>>>>>>>>.." + recordExists);

                        }else{


                        }

                        getUpdateOnAllClicksOffline();

                        Toast.makeText(getApplicationContext(), "MARK CLICKED", Toast.LENGTH_SHORT).show();
                    }



                }




            }
        });


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(haveNetworkConnection()){

                    //if any of the question is not answered don't allow the student to submit the answers.
                    if(not_answered == 0){
                        submitDialogBox();
                    }else{

                        Toast.makeText(getApplicationContext(), "Please Answer all the Questions", Toast.LENGTH_SHORT).show();
                    }


                }else{

                    //if any of the question is not answered don't allow the student to submit the answers.
                    if(not_answeredoffline == 0){
                        submitDialogBox();
                    }else{

                        Toast.makeText(getApplicationContext(), "Please Answer all the Questions", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });

        handler = new Handler();
        MillisecondTime = 0L;
        StartTime = 0L;
        TimeBuff = 0L;
        UpdateTime = 0L;
        Seconds = 0;
        Hours = 0;
        Minutes = 0;
        MilliSeconds = 0;


        //click listener to view the pallette
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showEditDialog();
                Log.e("VIEWPALETTECLICK", ">>>>>>>>>>>>>>>> " +"");
            }
        });


        back = (Button) findViewById(R.id.backbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Test_Main.this,Main_Exam.class);
                startActivity(intent);
            }
        });


        // setting the header for action bar.
        header.setText(E_name);

        desc = (ImageView) findViewById(R.id.desc);

        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(Test_Main.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialogue_desc);
                dialog.show();

                s_name = (TextView) dialog .findViewById(R.id.stdname);
                e_num = (TextView) dialog.findViewById(R.id.e_num);
                batch_id = (TextView) dialog .findViewById(R.id.batchId);
                bname = (TextView) dialog.findViewById(R.id.batchN);
                jobRole = (TextView) dialog.findViewById(R.id.jobrole);

                s_name.setText(get_stdname);
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

        next.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UnsupportedChromeOsCameraSystemFeature")
            @Override
            public void onClick(View v) {

                if(haveNetworkConnection()){

                    //increament the position and the question number
                    position++;
                    assign_Qnum++;

                    Log.e("GETPOS",">>>>>>>>>>>>>." + position + assign_Qnum);

                    //get the next question from the database
                    getData();

                    //calculate the middle number of the total questions
                    int getHalf = (1 + (get_totalqns)/2);

                    QuesOption();
                    //making scrollview to focus to top when going to next question
                    scrollView.fullScroll(ScrollView.FOCUS_UP);

                    if(get_snap.equals("Yes")){


                        if(assign_Qnum == getHalf) {
                            // do we have a camera?
                            if (!getPackageManager()
                                    .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                                Toast.makeText(Test_Main.this, "No camera on this device", Toast.LENGTH_LONG)
                                        .show();

                                Log.e("No camera",">>>>>>>>>>>>>" + cameraId);
                            } else {
                                cameraId = findFrontFacingCamera();

                                Log.e("findFrontFacingCamera",">>>>>>>>>>>>>" + cameraId);
                                if (cameraId < 0) {
                                    Toast.makeText(Test_Main.this, "No front facing camera found.",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    camera = Camera.open(cameraId);

                                    try {
                                        camera.setPreviewTexture(new SurfaceTexture(10));
                                    } catch (IOException e1) {
                                        Log.e("VERSION", e1.getMessage());
                                    }


                                    camera.startPreview();
                                    camera.takePicture(null, null,
                                            new PhotoHandler(getApplicationContext(), get_stdID));


                                    Log.e("camera > 0",">>>>>>>>>>>>>" + cameraId);
                                }
                            }
                        }


                    }else if(get_snap.equals("No")){

                        //if take_snapshot: "No" pass null values for snapshots while submitting answers
                        base64 = "null";
                        get_snames = "null";
                        get_sdate = "null";

                    }


                    if (assign_Qnum == get_totalqns) {

                        next.setVisibility(View.GONE);
                    }else{
                        next.setEnabled(true);
                    }
                    previous.setEnabled(true);


                    if(get_snap.equals("Yes")){

                        if(assign_Qnum == get_totalqns){
                            // do we have a camera?
                            if (!getPackageManager()
                                    .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                                Toast.makeText(Test_Main.this, "No camera on this device", Toast.LENGTH_LONG)
                                        .show();

                                Log.e("No camera",">>>>>>>>>>>>>" + cameraId);
                            } else {
                                cameraId = findFrontFacingCamera();

                                Log.e("findFrontFacingCamera",">>>>>>>>>>>>>" + cameraId);
                                if (cameraId < 0) {
                                    Toast.makeText(Test_Main.this, "No front facing camera found.",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    camera = Camera.open(cameraId);

                                    try {
                                        camera.setPreviewTexture(new SurfaceTexture(10));
                                    } catch (IOException e1) {
                                        Log.e("VERSION", e1.getMessage());
                                    }


                                    camera.startPreview();
                                    camera.takePicture(null, null,
                                            new PhotoHandler(getApplicationContext(), get_stdID));
                                    Log.e("camera > 0",">>>>>>>>>>>>>" + cameraId);
                                }
                            }
                        }

                    }else if(get_snap.equals("No")){

                        //if take_snapshot: "No" pass null values for snapshots while submitting answers
                        base64 = "null";
                        get_snames = "null";
                        get_sdate = "null";
                    }

                }else{

                    //offline working>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                    //increament the position and the question number
                    position++;
                    assign_QnumOffline++;

                    Log.e("GETPOS",">>>>>>>>>>>>>." + position + assign_Qnum);

                    //get the next question from the database
                    getDataOffline();

                    //calculate the middle number of the total questions
                    int getHalf = (1 + (get_totalqnsOffline)/2);

                    QuesOptionOffline();
                    //making scrollview to focus to top when going to next question
                    scrollView.fullScroll(ScrollView.FOCUS_UP);

                    if(get_snap.equals("Yes")){


                        if(assign_QnumOffline == getHalf) {
                            // do we have a camera?
                            if (!getPackageManager()
                                    .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                                Toast.makeText(Test_Main.this, "No camera on this device", Toast.LENGTH_LONG)
                                        .show();

                                Log.e("No camera",">>>>>>>>>>>>>" + cameraId);
                            } else {
                                cameraId = findFrontFacingCamera();

                                Log.e("findFrontFacingCamera",">>>>>>>>>>>>>" + cameraId);
                                if (cameraId < 0) {
                                    Toast.makeText(Test_Main.this, "No front facing camera found.",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    camera = Camera.open(cameraId);

                                    try {
                                        camera.setPreviewTexture(new SurfaceTexture(10));
                                    } catch (IOException e1) {
                                        Log.e("VERSION", e1.getMessage());
                                    }


                                    camera.startPreview();
                                    camera.takePicture(null, null,
                                            new PhotoHandler(getApplicationContext(), get_stdID));


                                    Log.e("camera > 0",">>>>>>>>>>>>>" + cameraId);
                                }
                            }
                        }


                    }else if(get_snap.equals("No")){

                        //if take_snapshot: "No" pass null values for snapshots while submitting answers
                        base64 = "null";
                        get_snames = "null";
                        get_sdate = "null";

                    }

                    if (assign_QnumOffline == get_totalqnsOffline) {

                        next.setVisibility(View.GONE);
                    }else{
                        next.setEnabled(true);
                    }
                    previous.setEnabled(true);


                    if(get_snap.equals("Yes")){

                        if(assign_QnumOffline == get_totalqnsOffline){
                            // do we have a camera?
                            if (!getPackageManager()
                                    .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                                Toast.makeText(Test_Main.this, "No camera on this device", Toast.LENGTH_LONG)
                                        .show();

                                Log.e("No camera",">>>>>>>>>>>>>" + cameraId);
                            } else {
                                cameraId = findFrontFacingCamera();

                                Log.e("findFrontFacingCamera",">>>>>>>>>>>>>" + cameraId);
                                if (cameraId < 0) {
                                    Toast.makeText(Test_Main.this, "No front facing camera found.",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    camera = Camera.open(cameraId);

                                    try {
                                        camera.setPreviewTexture(new SurfaceTexture(10));
                                    } catch (IOException e1) {
                                        Log.e("VERSION", e1.getMessage());
                                    }


                                    camera.startPreview();
                                    camera.takePicture(null, null,
                                            new PhotoHandler(getApplicationContext(), get_stdID));
                                    Log.e("camera > 0",">>>>>>>>>>>>>" + cameraId);
                                }
                            }
                        }

                    }else if(get_snap.equals("No")){

                        //if take_snapshot: "No" pass null values for snapshots while submitting answers
                        base64 = "null";
                        get_snames = "null";
                        get_sdate = "null";

                    }
                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(haveNetworkConnection()){

                    position--;
                    assign_Qnum--;

                    Log.e("GETPOS",">>>>>>>>>>>>>." + position + assign_Qnum);

                    getData();
                    QuesOption();
                    scrollView.fullScroll(ScrollView.FOCUS_UP);

                    if(assign_Qnum == (get_totalqns-1)){
                        next.setVisibility(View.VISIBLE);
                    }else if(assign_Qnum == 1){
                        previous.setEnabled(false);
                        scrollView.fullScroll(ScrollView.FOCUS_UP);

                    }else{
                        previous.setEnabled(true);
                    }

                }else{

                    //Offline working
                    position--;
                    assign_QnumOffline--;

                    Log.e("GETPOS",">>>>>>>>>>>>>." + position + assign_Qnum);

                    getDataOffline();
                    QuesOptionOffline();
                    scrollView.fullScroll(ScrollView.FOCUS_UP);

                    if(assign_QnumOffline == (get_totalqnsOffline-1)){
                        next.setVisibility(View.VISIBLE);
                    }else if(assign_QnumOffline == 1){
                        previous.setEnabled(false);
                        scrollView.fullScroll(ScrollView.FOCUS_UP);

                    }else{
                        previous.setEnabled(true);
                    }
                }
            }
        });

        logout = (ImageView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "You have successfully logout",
                        Toast.LENGTH_LONG).show();
                editor.remove("loginTest");
                editor.commit();

                Intent intent = new Intent(Test_Main.this,Login.class);
                startActivity(intent);

            }
        });


        if (!checkPermission()) {

            mainMethod();
        } else {
            if (checkPermission()) {
                requestPermissionAndContinue();
            } else {
                mainMethod();
            }
        }

        database();

    }

    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {

                        Log.e("DATA IP ADDRESS", "?????????????" + addr.getHostAddress());

                        return  addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }

    //Get the question number from palette and display the question
    public void setDatafromFragment(String position){

        if(haveNetworkConnection()){

            Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();

            get_asqn = position;
            assign_Qnum = Integer.parseInt(get_asqn);

            if(assign_Qnum != get_totalqns){
                next.setVisibility(View.VISIBLE);
            }else{
                next.setVisibility(View.GONE);
            }

            Log.e("GETASQN",">>>>>>>>>>>>>>" +get_asqn);

            getData();
            QuesOption();
            //making scrollview to focus to top when going to next question
            scrollView.fullScroll(ScrollView.FOCUS_UP);
            viewPaletteFragment.dismiss();


        }else{

            //Offline Working>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();

            get_asqnOffline = position;
            assign_QnumOffline = Integer.parseInt(get_asqnOffline);

            if(assign_QnumOffline != get_totalqnsOffline){
                next.setVisibility(View.VISIBLE);
            }else{
                next.setVisibility(View.GONE);
            }

            Log.e("GETASQN",">>>>>>>>>>>>>>" +get_asqn);

            getDataOffline();
            QuesOptionOffline();
            //making scrollview to focus to top when going to next question
            scrollView.fullScroll(ScrollView.FOCUS_UP);
            viewPaletteFragment.dismiss();

        }


    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.d(DEBUG_TAG, "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    @Override
    protected void onPause() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
        super.onPause();
    }

    public void submitDialogBox() {

        SnapsNames = new ArrayList<>();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Test_Main.this);
        alertDialogBuilder.setTitle("Alert!!");
        alertDialogBuilder
                .setMessage(
                        "Do you want to submit your answers?")
                .setCancelable(false)
                .setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            public void onClick(DialogInterface dialog, int id) {

                                String get_end_tym = getDateTime();
                                byte[] data1;

                                String date = get_end_tym.toString().split(" ")[0];
                                exam_EndTym = get_end_tym.toString().split(" ")[1];
                                Log.e("EXAM_END_TYNM",">>>>>>>>>>>" +exam_EndTym);


                                if(get_snap.equals("Yes")){
                                    // gets the files in the directory
                                    fileDirectory = new File(Environment.getExternalStorageDirectory().getPath() +"/.Ivintage/"+get_stdID);
                                    // lists all the files into an array
                                    File[] dirFiles = fileDirectory.listFiles();

                                    if (dirFiles.length != 0){
                                        // loops through the array of files, outputing the name to console
                                        for (int ii = 0; ii <= 2; ii++) {
                                            fileOutput = dirFiles[ii].toString(); // Complete path of the pics

                                            path1 = dirFiles[0].toString(); // path of first image
                                            path2 = dirFiles[1].toString(); // path of Second image
                                            path3 = dirFiles[2].toString(); // path of third image

                                            //Convert first image to base64
                                            Bitmap bm1 = BitmapFactory.decodeFile(path1);
                                            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                                            bm1.compress(Bitmap.CompressFormat.JPEG, 10, baos1); //bm is the bitmap object
                                            byte[] b1 = baos1.toByteArray();
                                            String base64_1 = Base64.encodeToString(b1, Base64.DEFAULT);
                                            Log.e("BASE 64 IMAGE 1", ">>>>>>>>>>>>" + base64_1);

                                            //Convert second image
                                            Bitmap bm2 = BitmapFactory.decodeFile(path2);
                                            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                                            bm2.compress(Bitmap.CompressFormat.JPEG, 10, baos2); //bm is the bitmap object
                                            byte[] b2 = baos2.toByteArray();
                                            String base64_2 = Base64.encodeToString(b2, Base64.DEFAULT);
                                            Log.e("BASE 64 IMAGE 2", ">>>>>>>>>>>>" + base64_1);

                                            //Convert 3rd Image to base64
                                            Bitmap bm3 = BitmapFactory.decodeFile(path3);
                                            ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
                                            bm3.compress(Bitmap.CompressFormat.JPEG, 10, baos3); //bm is the bitmap object
                                            byte[] b3 = baos3.toByteArray();
                                            String base64_3 = Base64.encodeToString(b3, Base64.DEFAULT);
                                            Log.e("BASE 64 IMAGE 3", ">>>>>>>>>>>>" + base64_1);

                                            //Storing all three paths in arraylist and converting & storing into string by joining using comma separator
                                            CompletePath.add(base64_1);
                                            CompletePath.add(base64_2);
                                            CompletePath.add(base64_3);
                                            base64 = TextUtils.join(",",CompletePath);

                                            Log.e("COMPLETE PATH", ">>>>>>>>>>>" +  " " + base64);

                                            Image_Path.add(dirFiles[ii].getName()); //Arraylist of images with timestamp
                                            img_name_date = TextUtils.join(",", Image_Path); //String of images [name with timestamp]
                                            Log.e("FILEOUTPUT", ">>>>>>>>>>>." + Image_Path + " " + img_name_date );


                                            fileOutput = fileOutput.substring(fileOutput.lastIndexOf("/")+1);
                                            SnapsNames.add(fileOutput);  // Arraylist of names

                                            fileOutput = fileOutput.substring(fileOutput.lastIndexOf("_")+1);

                                            int index = fileOutput.indexOf (".");
                                            String str = fileOutput.substring (0, index);
                                            SnapsDate.add(str);
                                            get_sdate = TextUtils.join(",", SnapsDate); // timestamp of each images [String with comma separator]
                                            Log.e("JOINED PICSDATE",">>>>>>>>>>>>>>>" +get_sdate);

                                        }


                                        get_snames = TextUtils.join(",",SnapsNames); // pics name with comma
                                        editor.putString("uploadImgName", get_snames);
                                        editor.commit();

                                        Log.e("JOINED STRING",">>>>>>>>>>>>>>." + get_snames);

                                    }


                                }else if(get_snap.equals("No")){

                                    get_snames = "null";
                                    get_sdate = "null";
                                }

                                if(haveNetworkConnection()){

                                    Cursor data = mydb.getListContents();

                                    if(data != null) {
                                        while (data.moveToNext()) {
                                            Dbq_id = data.getString(1);
                                            DbSelectedOpt = data.getString(2);

                                            SelectedAnswers.add(DbSelectedOpt);

                                            Log.e("SELECTEDANS",">>>>>>>>>>>>>>>>" +SelectedAnswers);
                                        }


                                    }else{

                                        Log.e("NULLDB",">>>>>>>>>>>>>>>>" + DbQnum);
                                    }

                                    //If connection available directly pass the values to server
                                    new AnswersPostRequest().execute();

                                    dialog.cancel();


                                }else{

                                    Cursor data = mydb.getOptbyStdID(get_stdID);

                                    if(data != null) {
                                        while (data.moveToNext()) {

                                            Db_qid = data.getString(5);
                                            Db_sopt = data.getString(16);

                                            SelectedAnswers.add(Db_sopt);

                                            Log.e("DB SELECTED ANS",">>>>>>>>>>>>>>>>" +SelectedAnswers  + Db_stdID);
                                        }


                                    }else{

                                        Log.e("NULLDB",">>>>>>>>>>>>>>>>" + DbQnum);
                                    }

                                    //Adding details to answer table
                                    mydb.addAnswers(API_KEY,get_examID,get_stdID,get_stdname,exam_date,get_sscID,
                                            get_tradeID,get_tbID,exam_StartTym,exam_EndTym,ip,"mobile_app",QuestionNumbersOffline.toString(),
                                            get_snames,get_sdate,SelectedAnswers.toString(),base64,newQues.toString(),get_snap);

                                    Log.e("ADD ANSWERS", ">>>>>>>>>>>>>." + API_KEY + " " + get_examID + " " + get_stdID + " "+
                                            get_stdname+ " " + exam_date + " " + get_sscID + " " + get_tradeID + " " + get_tbID + " " +exam_StartTym+
                                            " " + exam_EndTym + " " + ip + " " + QuestionNumbersOffline.toString() + " " + SelectedAnswers.toString() + " " + newQues.toString());

                                    scheduleJob();
                                    Log.e("NOINTERNET", ">>>>>>>" + "SUBMIT BUTTON");


                                    Intent intent = new Intent(Test_Main.this, Main_Exam.class);
                                    startActivity(intent);

                                }
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    //get the current date and time
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void clearAll(){

        if(haveNetworkConnection()){

            l1.setBackgroundColor(Color.WHITE);
            l2.setBackgroundColor(Color.WHITE);
            l3.setBackgroundColor(Color.WHITE);
            l4.setBackgroundColor(Color.WHITE);
            get_selectedOpt = "";
            option_Name = null;

            Log.e("CLEARED", ">>>>>>>>>." + get_selectedOpt);
            Log.e("OPTCLEARED", ">>>>>>>>>." + option_Name);

            boolean recordExists= mydb.checkIfRecordExist(mydb.TABLE_NAME ,mydb.COLUMN_1 ,Dbq_id);
            if(recordExists)
            {

                if(option_Name == null){

                    selectUpdateOption(Dbq_id,DbSelectedOpt,null);

                    if(notans.equals("1")){

                        Log.e("DBQID",">>>>>>>>" + Dbq_id + notans);
                        selectUpdateNotAns(Dbq_id,1,0);
                    }

                }

            }else{

            }

            getUpdateOnAllClicks();
        }else{

            l1.setBackgroundColor(Color.WHITE);
            l2.setBackgroundColor(Color.WHITE);
            l3.setBackgroundColor(Color.WHITE);
            l4.setBackgroundColor(Color.WHITE);
            get_selectedOpt = "";
            option_Name = null;

            Log.e("CLEARED", ">>>>>>>>>." + get_selectedOpt);
            Log.e("OPTCLEARED", ">>>>>>>>>." + option_Name);

            boolean recordExists= mydb.checkIfRecordExistQns(mydb.QUESTIONS_TABLE ,mydb.q_col_5 ,Db_qid);
            if(recordExists)
            {

                if(option_Name == null){

                    selectUpdateOptionOffline(Db_qid,get_stdID,Db_sopt,null);

                    if(Db_na.equals("1")){

                        Log.e("DBQID",">>>>>>>>" + Db_qid + notans);
                        selectUpdateNotAnsOffline(Db_qid,get_stdID,1,0);
                    }

                }

            }else{

            }

            getUpdateOnAllClicksOffline();

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob() {
        JobInfo myJob = new JobInfo.Builder(0, new ComponentName(this, NetworkSchedulerService.class))
                .setRequiresCharging(false)
                .setMinimumLatency(1000)
                .setOverrideDeadline(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(myJob);
    }


    @Override
    protected void onStop() {
        // A service can be "started" and/or "bound". In this case, it's "started" by this Activity
        // and "bound" to the JobScheduler (also called "Scheduled" by the JobScheduler). This call
        // to stopService() won't prevent scheduled jobs to be processed. However, failing
        // to call stopService() would keep it alive indefinitely.
        stopService(new Intent(this, NetworkSchedulerService.class));
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start service and provide it a way to communicate with this class.
        Intent startServiceIntent = new Intent(this, NetworkSchedulerService.class);
        startService(startServiceIntent);
    }

    //get the data from the database
    private void database() {
        mydb = new DBHelper(this);
        Cursor data = mydb.getListContents();

        if(data != null) {
            while (data.moveToNext()) {
                Dbq_id = data.getString(1);
                DbSelectedOpt = data.getString(2);
                m_review = data.getString(3);
                notans = data.getString(4);
                notvisit = data.getString(5);
                qns = data.getString(6);
                opa = data.getString(7);
                opb = data.getString(8);
                opc = data.getString(9);
                opd = data.getString(10);
                DbQnum = data.getString(11);
                hqns = data.getString(12);
                hopa = data.getString(13);
                hopb = data.getString(14);
                hopc = data.getString(15);
                hopd = data.getString(16);

                Log.e("DBVALUESMAIN", ">>>>>>>>>>" +  DbQnum + " " + "" + Dbq_id+ " " +  DbSelectedOpt +
                        " " + m_review +" " +notans+" " +notvisit + " " + qns + " " + opa + " " + opb + " " + opc + " " + opd +
                        " " + hqns + " " + hopa + " " + hopb + " " + hopc + " " + hopd);
            }
        }else{

            Log.e("NULLDB",">>>>>>>>>>>>>>>>" + DbQnum);
        }
    }

    //for updating the selected options for the question in database
    public void selectUpdateOption(String qid, String old_option, String new_option) {

        ContentValues contentValues = new ContentValues();

        final String whereClause = mydb.COLUMN_1 + " =?";
        final String[] whereArgs = {
                qid
        };


        contentValues.put(mydb.COLUMN_2, option_Name);

        SQLiteDatabase sqLiteDatabase = mydb.getWritableDatabase();
        sqLiteDatabase.update(mydb.TABLE_NAME, contentValues,
                whereClause, whereArgs);
    }

    //for updating the mark for review in database
    public void selectUpdateReview(String qid, Integer old_option, Integer new_option) {

        ContentValues contentValues = new ContentValues();

        final String whereClause = mydb.COLUMN_1 + " =?";
        final String[] whereArgs = {
                qid
        };


        contentValues.put(mydb.COLUMN_3, 1);

        SQLiteDatabase sqLiteDatabase = mydb.getWritableDatabase();
        sqLiteDatabase.update(mydb.TABLE_NAME, contentValues,
                whereClause, whereArgs);
    }

    //for updating not answered field in database
    public void selectUpdateNotAns(String qid, Integer old_option, Integer new_option) {

        ContentValues contentValues = new ContentValues();

        final String whereClause = mydb.COLUMN_1 + " =?";
        final String[] whereArgs = {
                qid
        };


        contentValues.put(mydb.COLUMN_4, new_option);

        SQLiteDatabase sqLiteDatabase = mydb.getWritableDatabase();
        sqLiteDatabase.update(mydb.TABLE_NAME, contentValues,
                whereClause, whereArgs);
    }

    //for updating not visited field in database
    public void selectNotVisited(String qid, Integer old_option, Integer new_option) {

        ContentValues contentValues = new ContentValues();

        final String whereClause = mydb.COLUMN_1 + " =?";
        final String[] whereArgs = {
                qid
        };


        contentValues.put(mydb.COLUMN_5, 1);

        SQLiteDatabase sqLiteDatabase = mydb.getWritableDatabase();
        sqLiteDatabase.update(mydb.TABLE_NAME, contentValues,
                whereClause, whereArgs);
    }

    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,CAMERA) != PackageManager.PERMISSION_GRANTED
                ;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, CAMERA)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle(getString(R.string.permission_necessary));
                alertBuilder.setMessage(R.string.storage_permission_is_encessary_to_write_event);
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(Test_Main.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE , CAMERA}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(Test_Main.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE, CAMERA}, PERMISSION_REQUEST_CODE);
            }
        } else {
            mainMethod();
        }
    }

    // When you have the request results
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternalFile = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if(cameraPermission && readExternalFile && writeExternalFile)
                    {
                        // write your logic here
                        mainMethod();
                    } else {
                        Snackbar.make(this.findViewById(android.R.id.content),
                                "Please Grant Permissions",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ActivityCompat.requestPermissions(Test_Main.this, new String[]{WRITE_EXTERNAL_STORAGE
                                                , READ_EXTERNAL_STORAGE , CAMERA}, PERMISSION_REQUEST_CODE);
                                    }
                                }).show();
                    }
                }
                break;
        }

    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Hours = Minutes / 60;

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 100);

            completedTime = ""
                    /* +String.format("%02d", Hours)*/
                    +":" +String.format("%02d", Minutes)  + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%02d", MilliSeconds);
            // Log.e("actualtime","" +completedTime);

            handler.postDelayed(this, 0);
        }

    };

    //for starting the timer when exam gets started
    public void myCounter(){
        numofQue = 0;
        dynamicTimer = 0;
        String get_offTime = Splash_Screen.sh.getString("dur", null);
        Log.e("OFF_TIME", ">>>>>>>>>>>" + get_offTime);

        if(haveNetworkConnection()){
            dynamicTimer= (Integer.parseInt(get_duration)*60000);

        }else{
            dynamicTimer = (Integer.parseInt(get_offTime)*60000);
            Log.e("OFF_TIME_ELSEPART", ">>>>>>>>>>>" + get_offTime);
        }


        Log.e("dynamictimer",""+dynamicTimer);


        mCountDown = new CountDownTimer(dynamicTimer, 1000) {

            @Override
            public void onFinish() {
                isAlertDialogShowing(alertDialog);
                Log.e("dynamictimer1",""+dynamicTimer);
                timeUp();
            }

            private void timeUp() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Test_Main.this);
                builder.setTitle("Times up!")
                        //.setMessage("Game over")
                        .setCancelable(false)
                        .setNeutralButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        handler.removeCallbacks(runnable);

                                        submitDialogBox();

                                    }
                                });
                AlertDialog alert = builder.create();
                if (!isFinishing()) {
                    alert.show();
                }

            }

            @Override
            public void onTick(long millisUntilFinished) {
                time= ""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                time_left.setText("Time Left: " + time);

            }
        }.start();

    }


    public void isAlertDialogShowing(AlertDialog alertDialog) {
        if (alertDialog != null)
            alertDialog.hide();
        return;
    }

    //open up the view palette fragment
    private void showEditDialog() {

        if(haveNetworkConnection()){

            FragmentManager fm = getSupportFragmentManager();
            viewPaletteFragment = ViewPaletteFragment.newInstance("Some Title");
            viewPaletteFragment.show(fm, "fragment_edit_name");

            viewPaletteFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);


            Bundle data = new Bundle();//Use bundle to pass data
            data.putString("Answered", String.valueOf(answered)); //put string, int, etc in bundle with a key value
            data.putString("NotAnswered", String.valueOf(not_answered) );
            data.putString("NotVisited", String.valueOf(notVisited));
            data.putString("ReviewLater", String.valueOf(reviewLater));
            data.putStringArrayList("Numbers", QuestionNumbers);

            Log.e("NOT_ANS",">>>>>>>>>>>>" +String.valueOf(not_answered));
            Log.e("MARK",">>>>>>>>>>>>" +String.valueOf(reviewLater));
            Log.e("VISIT",">>>>>>>>>>>>" +String.valueOf(notVisited));


            viewPaletteFragment.setArguments(data);//Finally set argument bundle to fragment

        }else{
            FragmentManager fm = getSupportFragmentManager();
            viewPaletteFragment = ViewPaletteFragment.newInstance("Some Title");
            viewPaletteFragment.show(fm, "fragment_edit_name");

            viewPaletteFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);


            Bundle data = new Bundle();//Use bundle to pass data
            data.putString("Answered", String.valueOf(answeredoffline)); //put string, int, etc in bundle with a key value
            data.putString("NotAnswered", String.valueOf(not_answeredoffline) );
            data.putString("NotVisited", String.valueOf(notVisitedoffline));
            data.putString("ReviewLater", String.valueOf(reviewLateroffline));
            data.putStringArrayList("Numbers", QuestionNumbersOffline);

            Log.e("NOT_ANS",">>>>>>>>>>>>" +String.valueOf(not_answered));
            Log.e("MARK",">>>>>>>>>>>>" +String.valueOf(reviewLater));
            Log.e("VISIT",">>>>>>>>>>>>" +String.valueOf(notVisited));


            viewPaletteFragment.setArguments(data);//Finally set argument bundle to fragment
        }
    }

    public void dialogBoxForInternet() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Test_Main.this);
        alertDialogBuilder.setTitle("No Internet Connection.");
        alertDialogBuilder
                .setMessage(
                        "Go to Settings to enable Internet Connecivity.")
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
                                finish();
                            }
                        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @SuppressLint("UnsupportedChromeOsCameraSystemFeature")
    public void mainMethod() {
        if (haveNetworkConnection()) {

            new QuestionsPostRequest().execute();

        } else {
            // dialogBoxForInternet();
            boolean recordExists= mydb.checkStdIDExistinQuesTB(get_stdID);
            if(recordExists) {
                Cursor data1 = mydb.getSingleStudentQns(get_stdID);

                if (data1 != null) {
                    while (data1.moveToNext()) {

                        Db_stdID = data1.getString(1);
                        Db_nosID = data1.getString(2);
                        Db_nosCode = data1.getString(3);
                        Db_nosTitle = data1.getString(4);
                        Db_qid = data1.getString(5);
                        Db_ques = data1.getString(6);
                        Db_op1 = data1.getString(7);
                        Db_op2 = data1.getString(8);
                        Db_op3 = data1.getString(9);
                        Db_op4 = data1.getString(10);
                        Db_hques = data1.getString(11);
                        Db_hop1 = data1.getString(12);
                        Db_hop2 = data1.getString(13);
                        Db_hop3 = data1.getString(14);
                        Db_hop4 = data1.getString(15);
                        Db_sopt = data1.getString(16);
                        Db_review = data1.getString(17);
                        Db_na = data1.getString(18);
                        Db_nv = data1.getString(19);
                        Db_qnum = data1.getString(20);

                        QuestionNumbersOffline.add(Db_qnum);
                        newQues.add(Db_qid);

                        Log.e("DB DATA_QUES_TABLE", ">>>>>>>>>" + "Student id:" +  Db_stdID + " "+
                                " " + Db_sopt + " " + Db_review + " " + Db_na + " " + Db_nv + " "+ Db_qnum);
                    }
                }
            }

            getDataOffline();
            QuesOptionOffline();

            myCounter();
            StartTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);

            String get_start_tym = getDateTime();

            exam_date = get_start_tym.toString().split(" ")[0];
            exam_StartTym = get_start_tym.toString().split(" ")[1];
            Log.e("EXAM_START_TYM",">>>>>>>>>>>>" +exam_StartTym + " " +exam_date);

            if(get_snap.equals("Yes")){

                if(Db_qnum.equals("1")){
                    // do we have a camera?
                    if (!getPackageManager()
                            .hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                        Toast.makeText(Test_Main.this, "No camera on this device", Toast.LENGTH_LONG)
                                .show();

                        Log.e("No camera",">>>>>>>>>>>>>" + cameraId);

                    } else {
                        cameraId = findFrontFacingCamera();

                        Log.e("findFrontFacingCamera",">>>>>>>>>>>>>" + cameraId);
                        if (cameraId < 0) {
                            Toast.makeText(Test_Main.this, "No front facing camera found.",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            camera = Camera.open(cameraId);

                            try {
                                camera.setPreviewTexture(new SurfaceTexture(10));
                            } catch (IOException e1) {
                                Log.e("VERSION", e1.getMessage());
                            }


                            camera.startPreview();
                            camera.takePicture(null, null,
                                    new PhotoHandler(getApplicationContext(), get_stdID));
                            Log.e("camera > 0",">>>>>>>>>>>>>" + cameraId);
                        }
                    }
                }

            }else if(get_snap.equals("No")){

                //if take_snapshot: "No" pass null values for snapshots while submitting answers
                base64 = "null";
                get_snames = "null";
                get_sdate = "null";

            }

            //Toast.makeText(getApplicationContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }

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

    //onclick of each options
    @Override
    public void onClick(View v) {

        if((v.getId() == R.id.op1Layout)){
            //corresponding button logic should below here
            get_selectedOpt = op1.getText().toString();

            Log.e("SELECTEDOPT1", ">>>>>>>>>>" +get_selectedOpt);

            // Initialize a new GradientDrawable instance
            GradientDrawable gd = new GradientDrawable();
// Set the gradient drawable background to transparent
            gd.setColor(Color.parseColor("#FFFFFF"));

// Set a border for the gradient drawable
            gd.setStroke(5,Color.GREEN);

// Finally, apply the gradient drawable to the edit text background
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                l1.setBackground(gd);
                l2.setBackgroundColor(Color.WHITE);
                l3.setBackgroundColor(Color.WHITE);
                l4.setBackgroundColor(Color.WHITE);

            }

            option_Name = "A";



        } else if ((v.getId() == R.id.op2Layout)) {
            //corresponding button logic should below here

            get_selectedOpt = op2.getText().toString();
            Log.e("SELECTEDOPT2", ">>>>>>>>>>" +get_selectedOpt);


            // Initialize a new GradientDrawable instance
            GradientDrawable gd = new GradientDrawable();
// Set the gradient drawable background to transparent
            gd.setColor(Color.parseColor("#FFFFFF"));

// Set a border for the gradient drawable
            gd.setStroke(5,Color.GREEN);

// Finally, apply the gradient drawable to the edit text background
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                l2.setBackground(gd);
                l1.setBackgroundColor(Color.WHITE);
                l3.setBackgroundColor(Color.WHITE);
                l4.setBackgroundColor(Color.WHITE);

            }

            option_Name = "B";


        } else if ((v.getId() == R.id.op3Layout)) {
            //corresponding button logic should below here
            get_selectedOpt = op3.getText().toString();

            Log.e("SELECTEDOPT3", ">>>>>>>>>>" +get_selectedOpt);


            // Initialize a new GradientDrawable instance
            GradientDrawable gd = new GradientDrawable();
// Set the gradient drawable background to transparent
            gd.setColor(Color.parseColor("#FFFFFF"));

// Set a border for the gradient drawable
            gd.setStroke(5,Color.GREEN);

// Finally, apply the gradient drawable to the edit text background
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                l3.setBackground(gd);
                l2.setBackgroundColor(Color.WHITE);
                l1.setBackgroundColor(Color.WHITE);
                l4.setBackgroundColor(Color.WHITE);

            }

            option_Name = "C";


        }else if ((v.getId() == R.id.op4Layout)) {
            //corresponding button logic should below here
            get_selectedOpt = op4.getText().toString();

            Log.e("SELECTEDOPT4", ">>>>>>>>>>" +get_selectedOpt);

            // Initialize a new GradientDrawable instance
            GradientDrawable gd = new GradientDrawable();
// Set the gradient drawable background to transparent
            gd.setColor(Color.parseColor("#FFFFFF"));

// Set a border for the gradient drawable
            gd.setStroke(5,Color.GREEN);

// Finally, apply the gradient drawable to the edit text background
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                l4.setBackground(gd);
                l2.setBackgroundColor(Color.WHITE);
                l3.setBackgroundColor(Color.WHITE);
                l1.setBackgroundColor(Color.WHITE);


            }

            option_Name = "D";

        }


        get_OPname = get_selectedOpt;
        get_OP = option_Name;

        if(haveNetworkConnection()){

            boolean recordExists= mydb.checkIfRecordExist(mydb.TABLE_NAME ,mydb.COLUMN_1 ,Dbq_id);
            if(recordExists)
            {
                //do your stuff
                if(get_OP == null){


                }else{

                    selectUpdateOption(Dbq_id,null,get_OP);
                    Log.e("SELECTED", "??????????" + get_OP);
                    selectUpdateNotAns(Dbq_id,0,1);

                }

            }

            else{

            }

            getUpdateOnAllClicks();

            Log.e("FinalValueText",">>>>>>>>>>." + get_OPname  + get_OP );


        }else{

            boolean recordExists= mydb.checkIfRecordExistQns(mydb.QUESTIONS_TABLE ,mydb.q_col_5 ,Db_qid);
            if(recordExists)
            {
                //do your stuff
                if(get_OP == null){


                }else{

                    selectUpdateOptionOffline(Db_qid,get_stdID,null,get_OP);
                    Log.e("SELECTED", "??????????" + get_OP);
                    selectUpdateNotAnsOffline(Db_qid,get_stdID,0,1);

                }

            }

            else{

            }

            getUpdateOnAllClicksOffline();

            Log.e("FinalValueText",">>>>>>>>>>." + get_OPname  + get_OP );


        }




    }
    public class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {

            if (haveNetworkConnection()) {
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                Toast.makeText(context, "Connection established ", Toast.LENGTH_SHORT).show();
                /* new QuestionsPostRequest().execute();*/
            } else {

                dialogBoxForInternet();
            }
        }
    }


    // for getting the questions from server
    private class QuestionsPostRequest extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {

            // Showing progress dialog
            pDialog = new ProgressDialog(Test_Main.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressLint("UnsupportedChromeOsCameraSystemFeature")
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            QuestionsID = new ArrayList<>();

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                if (jsonObject != null && jsonObject.getString("status_message") != null) {

                    mydb.deleteRecord();
                    String message = jsonObject.getString("status_message");  // Message

                    reader = jsonObject;
                    Log.e("MESSAGE", ">>>>>>>>>>>>>>>>" + message);

                    if (message.equals("Success")) {

                        get_img_dir_src = jsonObject.getString("image_dir_source");
                        get_img_dir = jsonObject.getString("image_dir");
                        get_duration = jsonObject.getString("exam_duration");
                        get_exmName = jsonObject.getString("exam_name");
                        get_tradeCode = jsonObject.getString("trade_code");
                        get_tradeTitle = jsonObject.getString("trade_title");
                        get_totalqns = jsonObject.getInt("total_question");

                        duration.setText("Duration: " + get_duration + " Min");

                        Image_url = get_img_dir.replace("\\", "");
                        Log.e("IMageURL",">>>>>>>>>" +Image_url);

                        // Creating JSONArray from JSONObject
                        JSONArray userdetails = jsonObject.getJSONArray("questions");

                        for (int y = 0; y < userdetails.length(); y++) {

                            JSONObject jObj = userdetails.getJSONObject(y);

                            nos_id = jObj.getString("nos_id");
                            nos_code = jObj.getString("nos_code");
                            nos_title = jObj.getString("nos_title");

                            q_id = jObj.getString("q_id");
                            Question = jObj.getString("question");
                            typeofquestion = jObj.getString("typeofquestion");
                            option_a = jObj.getString("option_a");
                            option_b = jObj.getString("option_b");
                            option_c = jObj.getString("option_c");
                            option_d = jObj.getString("option_d");

                            get_hques = jObj.getString("lang_question");
                            get_hop1 = jObj.getString("lang_option_a");
                            get_hop2 = jObj.getString("lang_option_b");
                            get_hop3 = jObj.getString("lang_option_c");
                            get_hop4 = jObj.getString("lang_option_d");


                            QuestionsID.add(q_id);
                            Log.e("QUESTIONS_ID","??????????????" +QuestionsID);

                            QuestionNumbers.add(String.valueOf(y+1));
                            Log.e("QUES_NUMBER",">>>>>>>>>>.." + QuestionNumbers);

                            boolean recordExists= mydb.checkIfRecordExist(mydb.TABLE_NAME ,mydb.COLUMN_1 ,q_id);
                            if(recordExists)
                            {


                            }else{

                                mydb.addData(q_id,null,0,0,0,Question,option_a,option_b,option_c,option_d,(y+1),get_hques,get_hop1,get_hop2,get_hop3,get_hop4);

                            }

                        }

                    } else if (message.equals("Access Denied ! Authentication Failed")) {


                        Toast.makeText(getApplicationContext(),
                                "Access Denied", Toast.LENGTH_LONG).show();

                    }else if (message.equals("Not Found")) {


                        Toast.makeText(getApplicationContext(),
                                "Student Data Not Found", Toast.LENGTH_LONG).show();

                    }else if (message.equals("Error")) {


                        Toast.makeText(getApplicationContext(),
                                "Error Found", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Test_Main.this,Main_Exam.class);
                        startActivity(intent);

                    }
                }else{

                    Toast.makeText(getApplicationContext(),
                            "Cannot Get Values from server", Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getData();
            QuesOption();

            myCounter();
            StartTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
            pDialog.dismiss();

            String get_start_tym = getDateTime();

            exam_date = get_start_tym.toString().split(" ")[0];
            exam_StartTym = get_start_tym.toString().split(" ")[1];
            Log.e("EXAM_START_TYM",">>>>>>>>>>>>" +exam_StartTym + " " +exam_date);

            if(get_snap.equals("Yes")){

                if(DbQnum.equals("1")){
                    // do we have a camera?
                    if (!getPackageManager()
                            .hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                        Toast.makeText(Test_Main.this, "No camera on this device", Toast.LENGTH_LONG)
                                .show();

                        Log.e("No camera",">>>>>>>>>>>>>" + cameraId);

                    } else {
                        cameraId = findFrontFacingCamera();

                        Log.e("findFrontFacingCamera",">>>>>>>>>>>>>" + cameraId);
                        if (cameraId < 0) {
                            Toast.makeText(Test_Main.this, "No front facing camera found.",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            camera = Camera.open(cameraId);

                            try {
                                camera.setPreviewTexture(new SurfaceTexture(10));
                            } catch (IOException e1) {
                                Log.e("VERSION", e1.getMessage());
                            }


                            camera.startPreview();
                            camera.takePicture(null, null,
                                    new PhotoHandler(getApplicationContext(), get_stdID));
                            Log.e("camera > 0",">>>>>>>>>>>>>" + cameraId);
                        }
                    }
                }

            }else if(get_snap.equals("No")){

                //if take_snapshot: "No" pass null values for snapshots while submitting answers
                base64 = "null";
                get_snames = "null";
                get_sdate = "null";

            }

        }

        protected JSONObject doInBackground(Void... params) {

            return postJsonObject(Questions_url, makingJson());

        }
    }

    private JSONObject makingJson() {

        JSONObject postDataParams = new JSONObject();

        try {

            //following parameters to the API
            postDataParams.put("key", API_KEY);
            postDataParams.put("exam_id", get_examID);
            postDataParams.put("student_id", get_stdID);
            postDataParams.put("ssc_id", get_sscID);
            postDataParams.put("trade_id", get_tradeID);
            postDataParams.put("tb_id", get_tbID);

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



    public void getData(){

        mydb = new DBHelper(Test_Main.this);
        Cursor data = mydb.getSingleContents(String.valueOf(assign_Qnum));

        if(data != null) {
            while (data.moveToNext()) {
                Dbq_id = data.getString(1);
                DbSelectedOpt = data.getString(2);
                m_review = data.getString(3);
                notans = data.getString(4);
                notvisit = data.getString(5);
                qns = data.getString(6);
                opa = data.getString(7);
                opb = data.getString(8);
                opc = data.getString(9);
                opd = data.getString(10);
                DbQnum = data.getString(11);
                hqns = data.getString(12);
                hopa = data.getString(13);
                hopb = data.getString(14);
                hopc = data.getString(15);
                hopd = data.getString(16);


                Log.e("JSONDBVALUES", ">>>>>>>>>>" +  DbQnum + " " + "" + Dbq_id+ " " +  DbSelectedOpt +
                        " " + m_review +" " +notans+" " +notvisit + " " + qns + " " + opa + " " + opb + " " + opc + " " + opd +
                        " " + hqns + " " + hopa + " " + hopb + " " + hopc + " " + hopd);

                notVisited = mydb.notVisitedCount();
                mydb.close();
                Log.e("NOT_VISIT_COUNT",">>>>>>>>>>>" + notVisited);

                not_answered = mydb.notAnsweredCount();
                mydb.close();
                Log.e("NOT_Answered_COUNT",">>>>>>>>>>>" + not_answered);

                answered = mydb.answeredCount();
                mydb.close();
                Log.e("Answered_COUNT",">>>>>>>>>>>" + answered);

                reviewLater = mydb.reviewCount();
                mydb.close();
                Log.e("Review_COUNT",">>>>>>>>>>>" + reviewLater);

                ans.setText(String.valueOf(answered));
                not_ans.setText(String.valueOf(not_answered));
                not_visited.setText(String.valueOf(notVisited));
                review_later.setText(String.valueOf(reviewLater));


                boolean recordExists= mydb.checkIfRecordExist(mydb.TABLE_NAME ,mydb.COLUMN_1 ,Dbq_id);
                if(recordExists)
                {
                    //do your stuff
                    if(notvisit.equals("0")){
                        Log.e("DBQID",">>>>>>>>" + Dbq_id + notvisit);
                        selectNotVisited(Dbq_id,0,1);
                    }else{

                    }

                    if(DbSelectedOpt == null){

                        clearAll();

                        if(notans.equals("1")){

                            Log.e("DBQID",">>>>>>>>" + Dbq_id + notans);
                            selectUpdateNotAns(Dbq_id,1,0);
                        }

                    }else{

                        if(notans.equals("0")){

                            Log.e("DBQID",">>>>>>>>" + Dbq_id + notans);
                            selectUpdateNotAns(Dbq_id,0,1);
                        }

                        Log.e("DBSELECXTIO",">>>>>>>>>>>> " + DbSelectedOpt);

                        if(DbSelectedOpt.equals("A")){
                            // Initialize a new GradientDrawable instance
                            GradientDrawable gd = new GradientDrawable();
// Set the gradient drawable background to transparent
                            gd.setColor(Color.parseColor("#FFFFFF"));

// Set a border for the gradient drawable
                            gd.setStroke(5,Color.GREEN);

// Finally, apply the gradient drawable to the edit text background
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                l1.setBackground(gd);
                                l2.setBackgroundColor(Color.WHITE);
                                l3.setBackgroundColor(Color.WHITE);
                                l4.setBackgroundColor(Color.WHITE);

                            }
                            Log.e("CLICK A",">>>>>>>>>>>> " + DbSelectedOpt);
                        }else if(DbSelectedOpt.equals("B")){
                            // Initialize a new GradientDrawable instance
                            GradientDrawable gd = new GradientDrawable();
// Set the gradient drawable background to transparent
                            gd.setColor(Color.parseColor("#FFFFFF"));

// Set a border for the gradient drawable
                            gd.setStroke(5,Color.GREEN);

// Finally, apply the gradient drawable to the edit text background
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                l2.setBackground(gd);
                                l1.setBackgroundColor(Color.WHITE);
                                l3.setBackgroundColor(Color.WHITE);
                                l4.setBackgroundColor(Color.WHITE);

                            }
                            Log.e("CLICK B",">>>>>>>>>>>> " + DbSelectedOpt);
                        }else if(DbSelectedOpt.equals("C")){
                            // Initialize a new GradientDrawable instance
                            GradientDrawable gd = new GradientDrawable();
// Set the gradient drawable background to transparent
                            gd.setColor(Color.parseColor("#FFFFFF"));

// Set a border for the gradient drawable
                            gd.setStroke(5,Color.GREEN);

// Finally, apply the gradient drawable to the edit text background
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                l3.setBackground(gd);
                                l2.setBackgroundColor(Color.WHITE);
                                l1.setBackgroundColor(Color.WHITE);
                                l4.setBackgroundColor(Color.WHITE);

                            }
                            Log.e("CLICK C",">>>>>>>>>>>> " + DbSelectedOpt);
                        }else if(DbSelectedOpt.equals("D")){
                            // Initialize a new GradientDrawable instance
                            GradientDrawable gd = new GradientDrawable();
// Set the gradient drawable background to transparent
                            gd.setColor(Color.parseColor("#FFFFFF"));

// Set a border for the gradient drawable
                            gd.setStroke(5,Color.GREEN);

// Finally, apply the gradient drawable to the edit text background
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                l4.setBackground(gd);
                                l2.setBackgroundColor(Color.WHITE);
                                l3.setBackgroundColor(Color.WHITE);
                                l1.setBackgroundColor(Color.WHITE);

                            }
                            Log.e("CLICK D",">>>>>>>>>>>> " + DbSelectedOpt);
                        }
                    }

                }else{

                }
            }
        }else{
            Log.e("NULLDB",">>>>>>>>>>>>>>>>" + DbQnum);
        }
    }


    public void getUpdateOnAllClicks(){

        mydb = new DBHelper(Test_Main.this);
        Cursor data = mydb.getSingleContents(String.valueOf(assign_Qnum));

        if(data != null) {
            while (data.moveToNext()) {
                Dbq_id = data.getString(1);
                DbSelectedOpt = data.getString(2);
                m_review = data.getString(3);
                notans = data.getString(4);
                notvisit = data.getString(5);
                qns = data.getString(6);
                opa = data.getString(7);
                opb = data.getString(8);
                opc = data.getString(9);
                opd = data.getString(10);
                DbQnum = data.getString(11);
                hqns = data.getString(12);
                hopa = data.getString(13);
                hopb = data.getString(14);
                hopc = data.getString(15);
                hopd = data.getString(16);


                Log.e("JSONDBVALUES", ">>>>>>>>>>" + DbQnum + " " + "" + Dbq_id + " " + DbSelectedOpt +
                        " " + m_review + " " + notans + " " + notvisit + " " + qns + " " + opa + " " + opb + " " + opc + " " + opd +
                        " " + hqns + " " + hopa + " " + hopb + " " + hopc + " " + hopd);

                notVisited = mydb.notVisitedCount();
                mydb.close();
                Log.e("NOT_VISIT_COUNT", ">>>>>>>>>>>" + notVisited);

                not_answered = mydb.notAnsweredCount();
                mydb.close();
                Log.e("NOT_Answered_COUNT", ">>>>>>>>>>>" + not_answered);

                answered = mydb.answeredCount();
                mydb.close();
                Log.e("Answered_COUNT", ">>>>>>>>>>>" + answered);

                reviewLater = mydb.reviewCount();
                mydb.close();
                Log.e("Review_COUNT", ">>>>>>>>>>>" + reviewLater);

                ans.setText(String.valueOf(answered));
                not_ans.setText(String.valueOf(not_answered));
                not_visited.setText(String.valueOf(notVisited));
                review_later.setText(String.valueOf(reviewLater));

            }
        }

    }

    public void QuesOption(){

        if(qns.contains("|")){
            quesImage.setVisibility(View.VISIBLE);
            String[] str1 = qns.trim().split("[|]");
            String spt1 = str1[0];
            String spt2 = str1[1];

            String QuesImage = spt2.replaceAll("[%]", "");

            Log.e("splitQ1", ">>>>>>>>>>" +spt1);
            Log.e("splitQ2", ">>>>>>>>>>" +spt2);
            Log.e("QuesImg", ">>>>>>>>>>>" +QuesImage);

            Glide.with(Test_Main.this).load(Image_url +"/" + QuesImage).into(quesImage);

            question.setText(DbQnum + ") "  + Html.fromHtml(spt1).toString());

            //  hindi_question.setText(assign_Qnum + ") " + get_hques);
            if(hqns.equals("null")){
                hindi_question.setVisibility(View.GONE);
            }else{
                hindi_question.setVisibility(View.VISIBLE);
                hindi_question.setText(Html.fromHtml(hqns).toString());
            }


        }else{

            question.setText(DbQnum + ") " +  Html.fromHtml(qns).toString());
            quesImage.setVisibility(View.GONE);

            // hindi_question.setText(assign_Qnum + ") " + get_hques);
            //  hindi_question.setText(assign_Qnum + ") " + get_hques);
            if(hqns.equals("null")){
                hindi_question.setVisibility(View.GONE);
            }else{
                hindi_question.setVisibility(View.VISIBLE);
                hindi_question.setText(Html.fromHtml(hqns).toString());
            }

        }

        if(opa.contains("|")){
            op1_image.setVisibility(View.VISIBLE);

            String[] op1str1 = opa.trim().split("[|]");
            String op1spt1 = op1str1[0];
            String op1spt2 = op1str1[1];

            String Image = op1spt2.replaceAll("[%]", "");

            Log.e("ImageNameOp1",">>>>>>>>>." +Image);

            Glide.with(Test_Main.this).load(Image_url +"/" + Image).into(op1_image);


            Log.e("splitOPTIONA.1", ">>>>>>>>>>" +op1spt1);
            Log.e("splitOPTIONA.2", ">>>>>>>>>>" +op1spt2);
            Log.e("optionAImg", ">>>>>>>>>>>" +Image);



            op1.setText("A) " + Html.fromHtml(op1spt1).toString());
            //  hindi_op1.setText("A) " + get_hop1);

            if(hopa.equals("null")){
                hindi_op1.setVisibility(View.GONE);
            }else{
                hindi_op1.setVisibility(View.VISIBLE);
                hindi_op1.setText(Html.fromHtml(hopa).toString());
            }


        }else{

            if(opa.equals("NA")){
                l1.setVisibility(View.GONE);
                op1_image.setVisibility(View.GONE);
            }else{
                l1.setVisibility(View.VISIBLE);
                op1.setText("A) " + Html.fromHtml(opa).toString());
                op1_image.setVisibility(View.GONE);
            }

            // hindi_op1.setText("A) " + get_hop1);
            if(hopa.equals("null")){
                hindi_op1.setVisibility(View.GONE);
            }else{
                hindi_op1.setVisibility(View.VISIBLE);
                hindi_op1.setText(Html.fromHtml(hopa).toString());
            }
        }

        if(opb.contains("|")){
            op2_image.setVisibility(View.VISIBLE);
            String[] op2str1 = opb.trim().split("[|]");
            String op2spt1 = op2str1[0];
            String op2spt2 = op2str1[1];

            String Image = op2spt2.replaceAll("[%]", "");

            Log.e("ImageNameOp2",">>>>>>>>>." +Image);

            Glide.with(Test_Main.this).load(Image_url +"/" + Image).into(op2_image);
            Log.e("splitOPTIONB.1", ">>>>>>>>>>" +op2spt1);
            Log.e("splitOPTIONB.2", ">>>>>>>>>>" +op2spt2);
            Log.e("optionBImg", ">>>>>>>>>>>" +Image);

            op2.setText("B) " + Html.fromHtml(op2spt1).toString());
            //  hindi_op2.setText("B) " + get_hop2);

            if(hopb.equals("null")){
                hindi_op2.setVisibility(View.GONE);
            }else{
                hindi_op2.setVisibility(View.VISIBLE);
                hindi_op2.setText(Html.fromHtml(hopb).toString());
            }

        }else{

            if(opb.equals("NA")){
                l2.setVisibility(View.GONE);
                op2_image.setVisibility(View.GONE);
            }else{
                l2.setVisibility(View.VISIBLE);
                op2.setText("B) " + Html.fromHtml(opb).toString());
                op2_image.setVisibility(View.GONE);
            }


            // hindi_op2.setText("B) " + get_hop2);
            if(hopb.equals("null")){
                hindi_op2.setVisibility(View.GONE);
            }else{
                hindi_op2.setVisibility(View.VISIBLE);
                hindi_op2.setText(Html.fromHtml(hopb).toString());
            }
        }

        if(opc.contains("|")){
            op3_image.setVisibility(View.VISIBLE);
            String[] op3str1 = opc.trim().split("[|]");
            String op3spt1 = op3str1[0];
            String op3spt2 = op3str1[1];

            String Image = op3spt2.replaceAll("[%]", "");

            Log.e("ImageNameOp3",">>>>>>>>>." +Image);

            Glide.with(Test_Main.this).load(Image_url +"/" + Image).into(op3_image);

            Log.e("splitOPTIONC.1", ">>>>>>>>>>" +op3spt1);
            Log.e("splitOPTIONC.2", ">>>>>>>>>>" +op3spt2);
            Log.e("optionCImg", ">>>>>>>>>>>" +Image);

            op3.setText("C) " + Html.fromHtml(op3spt1).toString());
            // hindi_op3.setText("C) " + get_hop3);

            if(hopc.equals("null")){
                hindi_op3.setVisibility(View.GONE);
            }else{
                hindi_op3.setVisibility(View.VISIBLE);
                hindi_op3.setText(Html.fromHtml(hopc).toString());
            }

        }else{

            if(opc.equals("NA")){
                l3.setVisibility(View.GONE);
                op3_image.setVisibility(View.GONE);
            }else{
                l3.setVisibility(View.VISIBLE);
                op3.setText("C) " + Html.fromHtml(opc).toString());
                op3_image.setVisibility(View.GONE);
            }


            // hindi_op3.setText("C) " + get_hop3);
            if(hopc.equals("null")){
                hindi_op3.setVisibility(View.GONE);
            }else{
                hindi_op3.setVisibility(View.VISIBLE);
                hindi_op3.setText(Html.fromHtml(hopc).toString());
            }
        }

        if(opd.contains("|")){
            op4_image.setVisibility(View.VISIBLE);
            String[] op4str1 = opd.trim().split("[|]");
            String op4spt1 = op4str1[0];
            String op4spt2 = op4str1[1];

            String Image = op4spt2.replaceAll("[%]", "");

            Log.e("ImageNameOp4",">>>>>>>>>." +Image);

            Glide.with(Test_Main.this).load(Image_url +"/" + Image).into(op4_image);

            Log.e("splitOPTIOND.1", ">>>>>>>>>>" +op4spt1);
            Log.e("splitOPTIOND.2", ">>>>>>>>>>" +op4spt2);
            Log.e("optionDImg", ">>>>>>>>>>>" +Image);

            op4.setText("D) " + Html.fromHtml(op4spt1).toString());
            // hindi_op4.setText("D) " + get_hop4);

            if(hopd.equals("null")){
                hindi_op4.setVisibility(View.GONE);
            }else{
                hindi_op4.setVisibility(View.VISIBLE);
                hindi_op4.setText(Html.fromHtml(hopd).toString());
            }


        }else{

            if(opd.equals("NA")){
                l4.setVisibility(View.GONE);
                op4_image.setVisibility(View.GONE);
            }else{
                l4.setVisibility(View.VISIBLE);
                op4.setText("D) " + Html.fromHtml(opd).toString());
                op4_image.setVisibility(View.GONE);
            }


            //hindi_op4.setText("D) " + get_hop4);

            if(hopd.equals("null")){
                hindi_op4.setVisibility(View.GONE);
            }else{
                hindi_op4.setVisibility(View.VISIBLE);
                hindi_op4.setText(Html.fromHtml(hopd).toString());
            }
        }
    }

    @Override
    public void onBackPressed() {

        if(haveNetworkConnection()){
            if(not_answered == 0){
                submitDialogBox();
            }else{

                Toast.makeText(getApplicationContext(), "Please Answer all the Questions", Toast.LENGTH_SHORT).show();
            }
        }else{

            //Offline Working>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            if(not_answeredoffline == 0){
                submitDialogBox();
            }else{

                Toast.makeText(getApplicationContext(), "Please Answer all the Questions", Toast.LENGTH_SHORT).show();
            }


        }



    }

    // for getting the questions from server
    public class AnswersPostRequest extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {

            // Showing progress dialog
            pDialog = new ProgressDialog(Test_Main.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);


            try {
                if (jsonObject != null && jsonObject.getString("status_message") != null) {

                    String message = jsonObject.getString("status_message");  // Message

                    reader = jsonObject;
                    Log.e("MESSAGE", ">>>>>>>>>>>>>>>>" + message);

                    if (message.contains("Success Answer submitted successfully.")) {

                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Test_Main.this,Main_Exam.class);
                        startActivity(intent);

                        if(get_snap.equals("Yes")){
                            //working of uploading image to server
                            new UploadImagePostRequest().execute();
                        }else if(get_snap.equals("No")){

                            pDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "NO Snapshots", Toast.LENGTH_SHORT).show();
                        }


                    } else if (message.equals("Access Denied ! Authentication Failed")) {


                        Toast.makeText(getApplicationContext(),
                                "Access Denied", Toast.LENGTH_LONG).show();
                        pDialog.dismiss();

                    }else if(message.equals("This exam has already been taken!")){

                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }else{


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        protected JSONObject doInBackground(Void... params) {

            return postJsonObject1(Answers_url, makingJson1());

        }
    }

    private JSONObject makingJson1() {

        JSONObject postDataParams = new JSONObject();
        String qid_list_string = Arrays.toString(QuestionsID.toArray()).replace("[", "").replace("]", "");


        try {

            //following parameters to the API
            postDataParams.put("key", API_KEY);
            postDataParams.put("exam_id", get_examID);
            postDataParams.put("student_id", get_stdID);
            postDataParams.put("student_name", get_stdname);
            postDataParams.put("examdate", exam_date);
            postDataParams.put("ssc_id", get_sscID);
            postDataParams.put("trade_id", get_tradeID);
            postDataParams.put("tb_id", get_tbID);
            postDataParams.put("starttime", exam_StartTym);
            postDataParams.put("endtime", exam_EndTym);
            postDataParams.put("IP_address", ip);
            postDataParams.put("browser", "mobile_app");
            postDataParams.put("question_ids", qid_list_string);
            postDataParams.put("snapshot_image_name", get_snames);
            postDataParams.put("snapshot_image_date", get_sdate);

            Log.e("CONNECT_SUBMITSERVER", ">>>>>>> " + API_KEY + " " + get_examID + " " + get_stdID + " " + get_stdname + " " + exam_date +
                    " " + get_sscID + " " + get_tradeID + " " + get_tbID + " " + exam_StartTym + " " + exam_EndTym + " " +
                    ip + " " + " mobile_app " + " " + qid_list_string + " " + get_snames + " " + get_sdate);



            for(int i=0;i<QuestionsID.size();i++)
            {

                postDataParams.put("radio_"+QuestionsID.get(i),SelectedAnswers.get(i));

                Log.e("Connectoptions",">>>>>>>>>>." + "radio_"+QuestionsID.get(i) + SelectedAnswers.get(i));
            }
            HashMap<String ,String> params=new HashMap<String, String>();
            params.put("params",postDataParams.toString());

            Log.e("INTERNET", ">>>>>>>>>>>."+ params);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataParams;
    }



    public JSONObject postJsonObject1(String url, JSONObject loginJobj1) {
        InputStream inputStream1 = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String
            json = loginJobj1.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);
            httpPost.setEntity(new StringEntity(loginJobj1.toString(), "UTF-8"));

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream1 = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream1 != null) {
                result = convertInputStreamToString1(inputStream1);
                Log.e("RESULT RESPONSE", ">>>>>>>>>>>>>>>>>>." + result);
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

    private String convertInputStreamToString1(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    private class UploadImagePostRequest extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);


            try {
                if (jsonObject != null && jsonObject.getString("status_message") != null) {

                    String message = jsonObject.getString("status_message");  // Message

                    reader = jsonObject;
                    Log.e("MESSAGE_UPLOAD", ">>>>>>>>>>>>>>>>" + message);

                    if (message.equals("Success, Snapshot uploaded")) {

                        deleteRecursive(fileDirectory);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    } else if (message.equals("Access Denied ! Authentication Failed")) {

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    }
                }else{


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        protected JSONObject doInBackground(Void... params) {

            return postJsonObject2(UPLOAD_URL, makingJson2());

        }
    }

    public void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }

        fileOrDirectory.delete();
    }

    private JSONObject makingJson2() {

        JSONObject postDataParams = new JSONObject();
        String[] arr =new String[get_totalqns];

        try {
            //following parameters to the API

            postDataParams.put("key", API_KEY);
            postDataParams.put("batch_id", get_tbID);
            postDataParams.put("image", base64);
            postDataParams.put("image_name", get_snames);

            Log.e("UPLOAD IMAGE ONLINE", ">>>>>>>>" + API_KEY + get_tbID + base64 + get_snames);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataParams;
    }



    public JSONObject postJsonObject2(String url, JSONObject loginJobj1) {
        InputStream inputStream2 = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String
            json = loginJobj1.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);
            httpPost.setEntity(new StringEntity(loginJobj1.toString(), "UTF-8"));

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream2 = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream2 != null) {
                result = convertInputStreamToString2(inputStream2);
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

    private String convertInputStreamToString2(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    //calling method from networkSchedulerService class
    public void callFromService(Context context){
        mydb = new DBHelper(context);
        Cursor data = mydb.getAnswerData(mydb);

        if(data.getCount()>0 && data.moveToFirst())
            Arr_StudentIDS = new ArrayList<>();
        do{

            String studnt_id = data.getString(3);
            Arr_StudentIDS.add(studnt_id);

            Log.e("ARRAY SIZE TEST", ">>>>>>>>>>>"  + Arr_StudentIDS.size() + " " + studnt_id);

            for(int i = 0 ; i < Arr_StudentIDS.size() ; i++){

                get = Arr_StudentIDS.get(i);

            }

            Log.e("GET STDID TEST", ">>>>>>>>>>>"  + get);
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
        getExactQnum = new ArrayList<>();
        getDbsltAns = new ArrayList<>();

        mydb = new DBHelper(context);
        Cursor data = mydb.getAnswerSingleVal(mydb,val);

        Log.e("SINGLE VAL TEST","???STUDENT ID??" + val);

        if(data.getCount()>0 && data.moveToFirst())
            do{

                a1 = data.getString(1);
                a2 = data.getString(2);
                a3 = data.getString(3);
                a4 = data.getString(4);
                a5 = data.getString(5);
                a6 = data.getString(6);
                a7 = data.getString(7);
                a8 = data.getString(8);
                a9 = data.getString(9);
                a10 = data.getString(10);
                a11 = data.getString(11);
                a12 = data.getString(12); //browser
                a13 = data.getString(13); // question ids
                a14 = data.getString(14); //snapshot image name
                a15 = data.getString(15); //snapshot image date
                aoptions = data.getString(16); //selected_ans
                getb64 = data.getString(17); //encrypted_image
                getQid = data.getString(18); // Exact Qnum
                get_Snapshots = data.getString(19); // Exact Qnum


                text1 = getQid.replace("[", "").replace("]", "").replace(" ","");
                text2 = aoptions.replace("[", "").replace("]", "").replace(" ","");

                Log.e("DO WHILE", "TEST STDID" + a3 + " " + a4);

                //   Log.e("DB ARRAYLIST", ">>>>>>>>>>>>>" + " " +text1 + " " + text2);

          /*      Log.e("Single DB value TEST", ">>>>>>>>>>>>"
                        + a1 + a2 +a3 + a4 + a5 + a6 +a7 +a8 +a9 + a10 +a11 +
                        "Browser: "+ a12 +
                        "Question IDS: "+ a13 +
                        "Snapshot Image: " + a14 +
                        "Snapshot image date: " + a15 +
                        "Selected Ans: " + aoptions  +
                        "Encrypted Image: " + getb64 +
                        "Exact Qnum: " + getExactQnum);

                Log.e("ARRAYLIST", ">>>>>>>>>>>." + getExactQnum + " " + getDbsltAns + " " + "New Array" );*/

                final OfflineAnswersPostRequest fDownload = new OfflineAnswersPostRequest();
                fDownload.execute();

                //  new OfflineAnswersPostRequest().execute();

            }while (data.moveToNext());
    }


    // for getting the questions from server
    public class OfflineAnswersPostRequest extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            try {
                if (jsonObject != null && jsonObject.getString("status_message") != null) {

                    String message = jsonObject.getString("status_message");  // Message

                    reader = jsonObject;
                    Log.e("SERVER MESSAGE", ">>>>>>>>>>>>>>>>" + message);

                    if (message.contains("Success Answer submitted successfully.")) {

                        Log.e("EXAM Offline Success", ">>>>>>>>>>>>>>>" +a3+ " " +message);

                        Log.e("GET SNAP SUCCESS","?????????/" + get_Snapshots);

                        if(get_Snapshots.equals("Yes")){
                            //working of uploading image to server
                            Log.e("GET SNAP SUCCESS","YES/" + get_Snapshots);
                            new UploadImagePostRequestOffline().execute();

                        }else if(get_Snapshots.equals("No")){

                            mydb.deleteSingleAnswerData(a3);
                            Log.e("GET SNAP SUCCESS","NO/" + get_Snapshots);
                            Log.e("DBVAL dele anw" , "No Snapshots" + a3+ " ");

                        }

                    } else if (message.equals("Access Denied ! Authentication Failed")) {


                        Log.e("msg 2 offline", ">>>>>>>>>>>>>>>" + message);

                    }else if(message.equals("This exam has already been taken!")){

                        Log.e("msg 3 offline", ">>>>>>>>>>>>>>>" + message);
                    }
                }else{


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        protected JSONObject doInBackground(Void... params) {

            // step one : converting comma separate String to array of String
            String[] elements = text1.split(",");

// step two : convert String array to list of String
            List<String> fixedLenghtList = Arrays.asList(elements);

// step three : copy fixed list to an ArrayList
            getExactQnum = new ArrayList<String>(fixedLenghtList);

            Log.e("list from Exact:",">>>>>>>>>>>." +getExactQnum);

            // step one : converting comma separate String to array of String
            String[] elements2 = text2.split(",");

// step two : convert String array to list of String
            List<String> fixedLenghtList2 = Arrays.asList(elements2);

// step three : copy fixed list to an ArrayList
            getDbsltAns = new ArrayList<String>(fixedLenghtList2);

            Log.e("list from Answers:",">>>>>>>>>>>." + getDbsltAns);

            return postJsonObjectOffline(Answers_url, makingJsonOffline());

        }
    }

    private JSONObject makingJsonOffline() {

        JSONObject postDataParams = new JSONObject();

        try {

//
            //following parameters to the API
            postDataParams.put("key", a1);
            postDataParams.put("exam_id", a2);
            postDataParams.put("student_id", a3);
            postDataParams.put("student_name", a4);
            postDataParams.put("examdate", a5);
            postDataParams.put("ssc_id", a6);
            postDataParams.put("trade_id", a7);
            postDataParams.put("tb_id", a8);
            postDataParams.put("starttime", a9);
            postDataParams.put("endtime", a10);
            postDataParams.put("IP_address", a11);
            postDataParams.put("browser", a12);
            postDataParams.put("question_ids", text1);
            postDataParams.put("snapshot_image_name", a14);
            postDataParams.put("snapshot_image_date", a15);


            for(int i=0;i<getDbsltAns.size();i++)
            {
                postDataParams.put("radio_"+getExactQnum.get(i),getDbsltAns.get(i));

                Log.e("MARKS TAKEN POST FOR",">>>>>>>>>>." + "radio_"+getExactQnum.get(i)+ getDbsltAns.get(i));

            }

            HashMap<String ,String> params=new HashMap<String, String>();
            params.put("params",postDataParams.toString());

            Log.e("Answer Submitted 1", ">>>>>>>>>>>."+ params);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataParams;
    }



    public JSONObject postJsonObjectOffline(String url, JSONObject loginJobjOffline) {
        InputStream inputStreamOffline = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String
            json = loginJobjOffline.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);
            httpPost.setEntity(new StringEntity(loginJobjOffline.toString(), "UTF-8"));

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStreamOffline = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStreamOffline != null) {
                result = convertInputStreamToStringOffline(inputStreamOffline);

                Log.e("RESULT RESPONSE", ">>>>>>>>>>>>>>>>>>." + result);
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

    private class UploadImagePostRequestOffline extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);


            try {
                if (jsonObject != null && jsonObject.getString("status_message") != null) {

                    String message = jsonObject.getString("status_message");  // Message

                    reader = jsonObject;
                    Log.e("MESSAGE_UPLOAD", ">>>>>>>>>>>>>>>>" + message);

                    if (message.contains("Success, Snapshot uploaded")) {

                        Log.e("OFFLINE_IMgsuccesUp", ">>>>>>>>>>>>>" + message + " " + a3);
                        mydb.deleteSingleAnswerData(a3);
                        deleteRecursive(fileDirectory);
                        Log.e("DB VAL dele answers ", "Snapshots YES" + a3);

                    } else if (message.equals("Access Denied ! Authentication Failed")) {

                        Log.e("OFFLINEUPLOADIMG", ">>>>>>>>>>>>>" + message);

                    }
                }else{


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        protected JSONObject doInBackground(Void... params) {

            return postJsonObjectImgOff(UPLOAD_URL, ImgOffmakingJson2());

        }
    }

    private JSONObject ImgOffmakingJson2() {

        JSONObject postDataParams = new JSONObject();
        String[] arr =new String[get_totalqnsOffline];

        try {
            //following parameters to the API
            postDataParams.put("key", a1);
            postDataParams.put("batch_id", a8);
            postDataParams.put("image", getb64);
            postDataParams.put("image_name", a14);

            Log.e("UPLOAD IMAGE oFFLINE", ">>>>>>>>" + a1 + " " +a8 + " " + getb64 + a14);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataParams;
    }



    public JSONObject postJsonObjectImgOff(String url, JSONObject loginJobj1) {
        InputStream inputStreamImgOff = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String
            json = loginJobj1.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);
            httpPost.setEntity(new StringEntity(loginJobj1.toString(), "UTF-8"));

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStreamImgOff = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStreamImgOff != null) {
                result = convertInputStreamToStringImgOff(inputStreamImgOff);
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

    private String convertInputStreamToStringImgOff(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


    //Offline working....methods

    public void getDataOffline(){

        boolean recordExists= mydb.checkStdIDExistinQuesTB(get_stdID);
        if(recordExists) {
            Cursor data1 = mydb.getQnsbyStdQnum(get_stdID,String.valueOf(assign_QnumOffline));

            if (data1 != null) {
                while (data1.moveToNext()) {

                    Db_stdID = data1.getString(1);
                    Db_nosID = data1.getString(2);
                    Db_nosCode = data1.getString(3);
                    Db_nosTitle = data1.getString(4);
                    Db_qid = data1.getString(5);
                    Db_ques = data1.getString(6);
                    Db_op1 = data1.getString(7);
                    Db_op2 = data1.getString(8);
                    Db_op3 = data1.getString(9);
                    Db_op4 = data1.getString(10);
                    Db_hques = data1.getString(11);
                    Db_hop1= data1.getString(12);
                    Db_hop2 = data1.getString(13);
                    Db_hop3 = data1.getString(14);
                    Db_hop4 = data1.getString(15);
                    Db_sopt = data1.getString(16);
                    Db_review= data1.getString(17);
                    Db_na = data1.getString(18);
                    Db_nv = data1.getString(19);
                    Db_qnum= data1.getString(20);

                    Log.e("CHECKING", ">>>>>>>>>>." + Db_stdID + " " + assign_QnumOffline + " " + Db_qnum + " " + Db_qid);

                    Log.e("DB DATA_QUES_TABLE", ">>>>>>>>>" + "Student id:" +  Db_stdID + " "+ Db_nosID + Db_nosCode + " " +
                            Db_nosTitle + " " + Db_qid + " " + Db_ques + " " + Db_op1 + " " + Db_op2 + " " + Db_op3 +
                            " " +Db_op4 +  " " + Db_hques + " " + Db_hop1 + " " + Db_hop2 + " " + Db_hop3 + " " +Db_hop4 +
                            " " + Db_sopt + " " + Db_review + " " + Db_na + " " + Db_nv + " "+ Db_qnum);


                    notVisitedoffline = mydb.notVisitedCountOffline(get_stdID);
                    mydb.close();
                    Log.e("NOT_VISIT_COUNTOffline",">>>>>>>>>>>" + notVisitedoffline);

                    not_answeredoffline = mydb.notAnsweredCountOffline(get_stdID);
                    mydb.close();
                    Log.e("NOT_Answ_Offline",">>>>>" + not_answeredoffline);

                    answeredoffline = mydb.answeredCountOffline(get_stdID);
                    mydb.close();
                    Log.e("Answered_COUNTOffline",">>>>>>>>>>>" + answeredoffline);

                    reviewLateroffline = mydb.reviewCountOffline(get_stdID);
                    mydb.close();
                    Log.e("Review_COUNTOffline",">>>>>>>>>>>" + reviewLateroffline);

                    ans.setText(String.valueOf(answeredoffline));
                    not_ans.setText(String.valueOf(not_answeredoffline));
                    not_visited.setText(String.valueOf(notVisitedoffline));
                    review_later.setText(String.valueOf(reviewLateroffline));


                    boolean recordExists1= mydb.checkIfRecordExistQns(mydb.QUESTIONS_TABLE ,mydb.q_col_5 ,Db_qid);
                    if(recordExists1)
                    {
                        //do your stuff
                        if(Db_nv.equals("0")){
                            Log.e("DBQID",">>>>>>>>" + Db_qid + Db_nv);
                            selectNotVisitedOffline(Db_qid,get_stdID,0,1);
                        }else{

                        }

                        if(Db_sopt == null){

                            clearAll();

                            if(Db_na.equals("1")){

                                Log.e("DBQID",">>>>>>>>" + Db_qid + Db_na);
                                selectUpdateNotAnsOffline(Db_qid,get_stdID,1,0);
                            }

                        }else{

                            if(Db_na.equals("0")){

                                Log.e("DBQID",">>>>>>>>" + Db_qid + Db_na);
                                selectUpdateNotAnsOffline(Db_qid,get_stdID,0,1);
                            }

                            Log.e("DBSELECXTIO",">>>>>>>>>>>> " + DbSelectedOpt);

                            if(Db_sopt.equals("A")){
                                // Initialize a new GradientDrawable instance
                                GradientDrawable gd = new GradientDrawable();
// Set the gradient drawable background to transparent
                                gd.setColor(Color.parseColor("#FFFFFF"));

// Set a border for the gradient drawable
                                gd.setStroke(5,Color.GREEN);

// Finally, apply the gradient drawable to the edit text background
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    l1.setBackground(gd);
                                    l2.setBackgroundColor(Color.WHITE);
                                    l3.setBackgroundColor(Color.WHITE);
                                    l4.setBackgroundColor(Color.WHITE);

                                }
                                Log.e("CLICK A",">>>>>>>>>>>> " + DbSelectedOpt);
                            }else if(Db_sopt.equals("B")){
                                // Initialize a new GradientDrawable instance
                                GradientDrawable gd = new GradientDrawable();
// Set the gradient drawable background to transparent
                                gd.setColor(Color.parseColor("#FFFFFF"));

// Set a border for the gradient drawable
                                gd.setStroke(5,Color.GREEN);

// Finally, apply the gradient drawable to the edit text background
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    l2.setBackground(gd);
                                    l1.setBackgroundColor(Color.WHITE);
                                    l3.setBackgroundColor(Color.WHITE);
                                    l4.setBackgroundColor(Color.WHITE);

                                }
                                Log.e("CLICK B",">>>>>>>>>>>> " + DbSelectedOpt);
                            }else if(Db_sopt.equals("C")){
                                // Initialize a new GradientDrawable instance
                                GradientDrawable gd = new GradientDrawable();
// Set the gradient drawable background to transparent
                                gd.setColor(Color.parseColor("#FFFFFF"));

// Set a border for the gradient drawable
                                gd.setStroke(5,Color.GREEN);

// Finally, apply the gradient drawable to the edit text background
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    l3.setBackground(gd);
                                    l2.setBackgroundColor(Color.WHITE);
                                    l1.setBackgroundColor(Color.WHITE);
                                    l4.setBackgroundColor(Color.WHITE);

                                }
                                Log.e("CLICK C",">>>>>>>>>>>> " + DbSelectedOpt);
                            }else if(Db_sopt.equals("D")){
                                // Initialize a new GradientDrawable instance
                                GradientDrawable gd = new GradientDrawable();
// Set the gradient drawable background to transparent
                                gd.setColor(Color.parseColor("#FFFFFF"));

// Set a border for the gradient drawable
                                gd.setStroke(5,Color.GREEN);

// Finally, apply the gradient drawable to the edit text background
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    l4.setBackground(gd);
                                    l2.setBackgroundColor(Color.WHITE);
                                    l3.setBackgroundColor(Color.WHITE);
                                    l1.setBackgroundColor(Color.WHITE);

                                }
                                Log.e("CLICK D",">>>>>>>>>>>> " + DbSelectedOpt);
                            }
                        }

                    }else{

                    }


                }
            }

        }


    }


    public void getUpdateOnAllClicksOffline(){

        boolean recordExists= mydb.checkStdIDExistinQuesTB(get_stdID);
        if(recordExists) {

            Cursor data1 = mydb.getQnsbyStdQnum(get_stdID,String.valueOf(assign_QnumOffline));

            if (data1 != null) {
                while (data1.moveToNext()) {

                    Db_stdID = data1.getString(1);
                    Db_nosID = data1.getString(2);
                    Db_nosCode = data1.getString(3);
                    Db_nosTitle = data1.getString(4);
                    Db_qid = data1.getString(5);
                    Db_ques = data1.getString(6);
                    Db_op1 = data1.getString(7);
                    Db_op2 = data1.getString(8);
                    Db_op3 = data1.getString(9);
                    Db_op4 = data1.getString(10);
                    Db_hques = data1.getString(11);
                    Db_hop1 = data1.getString(12);
                    Db_hop2 = data1.getString(13);
                    Db_hop3 = data1.getString(14);
                    Db_hop4 = data1.getString(15);
                    Db_sopt = data1.getString(16);
                    Db_review = data1.getString(17);
                    Db_na = data1.getString(18);
                    Db_nv = data1.getString(19);
                    Db_qnum = data1.getString(20);

                    Log.e("DB VAL", ">>>>>>>>>>" + DbQnum + " " + "" + Dbq_id + " " + DbSelectedOpt +
                            " " + m_review + " " + notans + " " + notvisit + " " + qns + " " + opa + " " + opb + " " + opc + " " + opd +
                            " " + hqns + " " + hopa + " " + hopb + " " + hopc + " " + hopd);

                    notVisitedoffline = mydb.notVisitedCountOffline(get_stdID);
                    mydb.close();
                    Log.e("NOT_VISIT_COUNTOffline", ">>>>>>>>>>>" + notVisitedoffline);

                    not_answeredoffline = mydb.notAnsweredCountOffline(get_stdID);
                    mydb.close();
                    Log.e("NOT_Answ_Offline", ">>>>>" + not_answeredoffline);

                    answeredoffline = mydb.answeredCountOffline(get_stdID);
                    mydb.close();
                    Log.e("Answered_COUNTOffline", ">>>>>>>>>>>" + answeredoffline);

                    reviewLateroffline = mydb.reviewCountOffline(get_stdID);
                    mydb.close();
                    Log.e("Review_COUNTOffline", ">>>>>>>>>>>" + reviewLateroffline);

                    ans.setText(String.valueOf(answeredoffline));
                    not_ans.setText(String.valueOf(not_answeredoffline));
                    not_visited.setText(String.valueOf(notVisitedoffline));
                    review_later.setText(String.valueOf(reviewLateroffline));
                }
            }
        }

    }

    public void QuesOptionOffline(){

        duration.setText("Duration: " + Splash_Screen.sh.getString("dur", null) + " Min");

        Image_urlOffline = Splash_Screen.sh.getString("img_dir", null);
        Image_urlOffline = Image_urlOffline.replace("\\", "");

        Log.e("IMAGE DIR", ">>>>>>>>>>>>>>>." + Image_urlOffline);

        if(Db_ques.contains("|")){
            quesImage.setVisibility(View.VISIBLE);
            String[] str1 = Db_ques.trim().split("[|]");
            String spt1 = str1[0];
            String spt2 = str1[1];

            String QuesImage = spt2.replaceAll("[%]", "");

            Log.e("splitQ1", ">>>>>>>>>>" +spt1);
            Log.e("splitQ2", ">>>>>>>>>>" +spt2);
            Log.e("QuesImg", ">>>>>>>>>>>" +QuesImage);

            Glide.with(Test_Main.this).load(Image_urlOffline +"/" + QuesImage).into(quesImage);

            question.setText(Db_qnum + ") " + Html.fromHtml(spt1).toString());

            //  hindi_question.setText(assign_Qnum + ") " + get_hques);
            if(Db_hques.equals("null")){
                hindi_question.setVisibility(View.GONE);
            }else{
                hindi_question.setVisibility(View.VISIBLE);
                hindi_question.setText(Html.fromHtml(Db_hques).toString());
            }


        }else{

            question.setText(Db_qnum + ") " + Html.fromHtml(Db_ques).toString());
            quesImage.setVisibility(View.GONE);

            // hindi_question.setText(assign_Qnum + ") " + get_hques);
            //  hindi_question.setText(assign_Qnum + ") " + get_hques);
            if(Db_hques.equals("null")){
                hindi_question.setVisibility(View.GONE);
            }else{
                hindi_question.setVisibility(View.VISIBLE);
                hindi_question.setText(Html.fromHtml(Db_hques).toString());
            }

        }

        if(Db_op1.contains("|")){
            op1_image.setVisibility(View.VISIBLE);

            String[] op1str1 = Db_op1.trim().split("[|]");
            String op1spt1 = op1str1[0];
            String op1spt2 = op1str1[1];

            String Image = op1spt2.replaceAll("[%]", "");

            Log.e("ImageNameOp1",">>>>>>>>>." +Image);

            Glide.with(Test_Main.this).load(Image_urlOffline +"/" + Image).into(op1_image);


            Log.e("splitOPTIONA.1", ">>>>>>>>>>" +op1spt1);
            Log.e("splitOPTIONA.2", ">>>>>>>>>>" +op1spt2);
            Log.e("optionAImg", ">>>>>>>>>>>" +Image);



            op1.setText("A) " + Html.fromHtml(op1spt1).toString());
            //  hindi_op1.setText("A) " + get_hop1);

            if(Db_hop1.equals("null")){
                hindi_op1.setVisibility(View.GONE);
            }else{
                hindi_op1.setVisibility(View.VISIBLE);
                hindi_op1.setText(Html.fromHtml(Db_hop1).toString());
            }


        }else{

            if(Db_op1.equals("NA")){
                l1.setVisibility(View.GONE);
                op1_image.setVisibility(View.GONE);
            }else{
                l1.setVisibility(View.VISIBLE);
                op1.setText("A) " +  Html.fromHtml(Db_op1).toString());
                op1_image.setVisibility(View.GONE);
            }

            // hindi_op1.setText("A) " + get_hop1);
            if(Db_hop1.equals("null")){
                hindi_op1.setVisibility(View.GONE);
            }else{
                hindi_op1.setVisibility(View.VISIBLE);
                hindi_op1.setText(Html.fromHtml(Db_hop1).toString());
            }
        }

        if(Db_op2.contains("|")){
            op2_image.setVisibility(View.VISIBLE);
            String[] op2str1 = Db_op2.trim().split("[|]");
            String op2spt1 = op2str1[0];
            String op2spt2 = op2str1[1];

            String Image = op2spt2.replaceAll("[%]", "");

            Log.e("ImageNameOp2",">>>>>>>>>." +Image);

            Glide.with(Test_Main.this).load(Image_urlOffline +"/" + Image).into(op2_image);
            Log.e("splitOPTIONB.1", ">>>>>>>>>>" +op2spt1);
            Log.e("splitOPTIONB.2", ">>>>>>>>>>" +op2spt2);
            Log.e("optionBImg", ">>>>>>>>>>>" +Image);

            op2.setText("B) " +  Html.fromHtml(op2spt1).toString());
            //  hindi_op2.setText("B) " + get_hop2);

            if(Db_hop2.equals("null")){
                hindi_op2.setVisibility(View.GONE);
            }else{
                hindi_op2.setVisibility(View.VISIBLE);
                hindi_op2.setText(Html.fromHtml(Db_hop2).toString());
            }

        }else{

            if(Db_op2.equals("NA")){
                l2.setVisibility(View.GONE);
                op2_image.setVisibility(View.GONE);
            }else{
                l2.setVisibility(View.VISIBLE);
                op2.setText("B) " + Html.fromHtml(Db_op2).toString());
                op2_image.setVisibility(View.GONE);
            }


            // hindi_op2.setText("B) " + get_hop2);
            if(Db_hop2.equals("null")){
                hindi_op2.setVisibility(View.GONE);
            }else{
                hindi_op2.setVisibility(View.VISIBLE);
                hindi_op2.setText(Html.fromHtml(Db_hop2).toString());
            }
        }

        if(Db_op3.contains("|")){
            op3_image.setVisibility(View.VISIBLE);
            String[] op3str1 = Db_op3.trim().split("[|]");
            String op3spt1 = op3str1[0];
            String op3spt2 = op3str1[1];

            String Image = op3spt2.replaceAll("[%]", "");

            Log.e("ImageNameOp3",">>>>>>>>>." +Image);

            Glide.with(Test_Main.this).load(Image_urlOffline +"/" + Image).into(op3_image);

            Log.e("splitOPTIONC.1", ">>>>>>>>>>" +op3spt1);
            Log.e("splitOPTIONC.2", ">>>>>>>>>>" +op3spt2);
            Log.e("optionCImg", ">>>>>>>>>>>" +Image);

            op3.setText("C) " + Html.fromHtml(op3spt1).toString());
            // hindi_op3.setText("C) " + get_hop3);

            if(Db_hop3.equals("null")){
                hindi_op3.setVisibility(View.GONE);
            }else{
                hindi_op3.setVisibility(View.VISIBLE);
                hindi_op3.setText(Html.fromHtml(Db_hop3).toString());
            }

        }else{

            if(Db_op3.equals("NA")){
                l3.setVisibility(View.GONE);
                op3_image.setVisibility(View.GONE);
            }else{
                l3.setVisibility(View.VISIBLE);
                op3.setText("C) " + Html.fromHtml(Db_op3).toString());
                op3_image.setVisibility(View.GONE);
            }


            // hindi_op3.setText("C) " + get_hop3);
            if(Db_hop3.equals("null")){
                hindi_op3.setVisibility(View.GONE);
            }else{
                hindi_op3.setVisibility(View.VISIBLE);
                hindi_op3.setText(Html.fromHtml(Db_hop3).toString());
            }
        }

        if(Db_op4.contains("|")){
            op4_image.setVisibility(View.VISIBLE);
            String[] op4str1 = Db_op4.trim().split("[|]");
            String op4spt1 = op4str1[0];
            String op4spt2 = op4str1[1];

            String Image = op4spt2.replaceAll("[%]", "");

            Log.e("ImageNameOp4",">>>>>>>>>." +Image);

            Glide.with(Test_Main.this).load(Image_urlOffline +"/" + Image).into(op4_image);

            Log.e("splitOPTIOND.1", ">>>>>>>>>>" +op4spt1);
            Log.e("splitOPTIOND.2", ">>>>>>>>>>" +op4spt2);
            Log.e("optionDImg", ">>>>>>>>>>>" +Image);

            op4.setText("D) " + Html.fromHtml(op4spt1).toString());
            // hindi_op4.setText("D) " + get_hop4);

            if(Db_hop4.equals("null")){
                hindi_op4.setVisibility(View.GONE);
            }else{
                hindi_op4.setVisibility(View.VISIBLE);
                hindi_op4.setText(Html.fromHtml(Db_hop4).toString());
            }


        }else{

            if(Db_op4.equals("NA")){
                l4.setVisibility(View.GONE);
                op4_image.setVisibility(View.GONE);
            }else{
                l4.setVisibility(View.VISIBLE);
                op4.setText("D) " + Html.fromHtml(Db_op4).toString());
                op4_image.setVisibility(View.GONE);
            }


            //hindi_op4.setText("D) " + get_hop4);

            if(Db_hop4.equals("null")){
                hindi_op4.setVisibility(View.GONE);
            }else{
                hindi_op4.setVisibility(View.VISIBLE);
                hindi_op4.setText(Html.fromHtml(Db_hop4).toString());
            }
        }
    }


    //for updating the selected options for the question in database
    public void selectUpdateOptionOffline(String qid, String std_id, String old_option, String new_option) {

        ContentValues contentValues = new ContentValues();

        final String whereClause = mydb.q_col_5 + " =?" + " AND " +mydb.q_col_1 + "=?";
        final String[] whereArgs = {
                qid,std_id
        };


        contentValues.put(mydb.q_col_16, option_Name);

        SQLiteDatabase sqLiteDatabase = mydb.getWritableDatabase();
        sqLiteDatabase.update(mydb.QUESTIONS_TABLE, contentValues,
                whereClause, whereArgs);
    }

    //for updating the mark for review in database
    public void selectUpdateReviewOffline(String qid, String std_id, Integer old_option, Integer new_option) {

        ContentValues contentValues = new ContentValues();

        final String whereClause = mydb.q_col_5 + " =?" + " AND " +mydb.q_col_1 + "=?";
        final String[] whereArgs = {
                qid,std_id
        };


        contentValues.put(mydb.q_col_17, 1);

        SQLiteDatabase sqLiteDatabase = mydb.getWritableDatabase();
        sqLiteDatabase.update(mydb.QUESTIONS_TABLE, contentValues,
                whereClause, whereArgs);
    }

    //for updating not answered field in database
    public void selectUpdateNotAnsOffline(String qid, String std_id, Integer old_option, Integer new_option) {

        ContentValues contentValues = new ContentValues();

        final String whereClause = mydb.q_col_5 + " =?" + " AND " +mydb.q_col_1 + "=?";
        final String[] whereArgs = {
                qid,std_id
        };


        contentValues.put(mydb.q_col_18, new_option);

        SQLiteDatabase sqLiteDatabase = mydb.getWritableDatabase();
        sqLiteDatabase.update(mydb.QUESTIONS_TABLE, contentValues,
                whereClause, whereArgs);
    }

    //for updating not visited field in database
    public void selectNotVisitedOffline(String qid, String std_id, Integer old_option, Integer new_option) {

        ContentValues contentValues = new ContentValues();

        final String whereClause = mydb.q_col_5 + " =?" + " AND " +mydb.q_col_1 + "=?";
        final String[] whereArgs = {
                qid,std_id
        };


        contentValues.put(mydb.q_col_19, 1);

        SQLiteDatabase sqLiteDatabase = mydb.getWritableDatabase();
        sqLiteDatabase.update(mydb.QUESTIONS_TABLE, contentValues,
                whereClause, whereArgs);
    }

}
