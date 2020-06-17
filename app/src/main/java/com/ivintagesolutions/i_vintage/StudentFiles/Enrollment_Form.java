package com.ivintagesolutions.i_vintage.StudentFiles;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ivintagesolutions.i_vintage.Database.DBHelper;
import com.ivintagesolutions.i_vintage.District;
import com.ivintagesolutions.i_vintage.R;
import com.ivintagesolutions.i_vintage.State;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Enrollment_Form extends AppCompatActivity {

    TextView header;
    ImageView logout,desc;
    EditText fname,lname,dob,age,mobile,landline,email,adharnum,doornum,streetName,village,dist,state,pin;
    EditText pdoornum,pstreetName,pvillage,ppin,fathersName,mname,foccu,moccu,fmobile,fincome,religion;
    EditText gname,relationshp,goccu,gnum,category,caste,qualify,poy,inst,marks,wrkexp,currEmployer,empAddress,accntNum,BankName,branch,ifsc;
    Button uploadpic,save,back;
    CheckBox sameAddress;
    private ImageView imageview;
    private static final String IMAGE_DIRECTORY = "/Ivintage_enrollment";
    private int GALLERY = 1, CAMERA = 2;

    String Gender = "";
    String Status = "";
    RadioGroup radioGroup1;
    private RadioButton rbmale,rbfmale,btn;
    String textgender,textstatus,base64,ageS;
    String buttonnIDgender,get_stdname,get_stdID,getAadhar,getStdMob,getGuardianN,getStdemail;

    AlertDialog alertDialog=null;
    NetworkChangeReceiver br;
    private ProgressDialog pDialog;

    Dialog dialog;
    String stdname,b_ID,b_name,j_role,enrol_num;
    TextView s_name,e_num,batch_id,bname,jobRole;


    final static String Enroll_Form_url = "http://ivintage.in/PMKVY2/student/student_api/update_enrollment_form";
    private static final String API_KEY = "H0Ki2GOEeFZdAyZ0f299h1qzNTpkBEBA";
    static final int DATE_DIALOG_ID = 0;
    DBHelper mydb;
    Context context;

    final static String m_status_api = "https://ivintage.in/PMKVY2/student/student_api/get_marital_status_options";
    final static String get_religion_api = "https://ivintage.in/PMKVY2/student/student_api/get_religion";
    final static String get_category = "https://ivintage.in/PMKVY2/student/student_api/get_category";
    final static String get_state = "https://ivintage.in/PMKVY2/student/student_api/get_state";
    final static String get_district = "https://ivintage.in/PMKVY2/student/student_api/get_district";
    Spinner m_Status_spinner,reg,categ,state1,dist1,pstate1,pdist1;
    ArrayList<String> Marital_Status,Religion,Cat;
    String m_status,getfreligion,getcat,getstate,getdist,getpdist,getpstate;
    ArrayList<District> Dist1,pDist1;
    ArrayList<State> State1,pState1;
    ArrayAdapter distAdapter,stateAdapter,pdistAdapter,pstateAdapter;
    private boolean selected_dist,selected_state,selected_pstate,selected_pdist;
    EditText ed_state,ed_dist;
    private LayoutInflater mInflator;
    TextView errorText;
    int s1,d1;
    ArrayList<String> Arr_StudentIDS;
    String get;


    String getfname,getlname,getdob,getage,getmobile,getlandline,getEmail,getadhar,get_adhar_picname,getdnum,getstrtname,getvillage,getpin;
    String getpdnum,getpstrtn,getpvillage,getppin,getfathern,getmname,getfoccu,getmoccu,getFmobile,getfincome;
    String getgname,getrship,getgoccu,getgnum,getcaste,getqua,getpoy,getinst,getmarks,getwrk,getcurrEmp,getempAddr,getacntNum,getbname,getbranch,getcode;

    String e_apikey,e_stdid,e_getfname,e_getlname,e_getdob,e_getage,e_gender,e_status,e_getmobile,e_getlandline,e_getEmail,e_getadhar,e_base64,e_get_adhar_picname,
            e_getdnum,e_getstrtname,e_getvillage,e_getdist,e_getstate,e_getpin,e_getpdnum,e_getpstrtn,e_getpvillage,
            e_getpdist,e_getpstate,e_getppin,e_getfathern,e_getmname,e_getfoccu,e_getmoccu,e_getFmobile,e_getfincome,
            e_getfreligion,e_getgname,e_getrship,e_getgoccu,e_getgnum,e_getcat,e_getcaste,e_getqua,e_expyears,e_getpoy,
            e_getinst,e_getmarks,e_getwrk,e_getcurrEmp,e_getempAddr,e_getacntNum,e_getbname,e_getbranch,e_getcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_form);

        br = new NetworkChangeReceiver();
        mydb = new DBHelper(this);

        header = (TextView)findViewById(R.id.header);
        header.setText("Enrollment Form");

        requestMultiplePermissions();
        mInflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);


        m_Status_spinner = (Spinner)findViewById(R.id.m_status);
        reg = (Spinner)findViewById(R.id.reg);
        categ = (Spinner)findViewById(R.id.cat);
        state1 = (Spinner)findViewById(R.id.state);
        dist1 = (Spinner)findViewById(R.id.dis);
        pstate1 = (Spinner)findViewById(R.id.statep);
        pdist1 = (Spinner)findViewById(R.id.disp);
        Religion = new ArrayList<>();
        Marital_Status = new ArrayList<>();
        Cat = new ArrayList<>();
        State1 = new ArrayList<>();
        Dist1 = new ArrayList<>();
        pState1 = new ArrayList<>();
        pDist1 = new ArrayList<>();


        get_stdname = Splash_Screen.sh.getString("get_stdname", null);
        get_stdID = Splash_Screen.sh.getString("student_id", null);
        getAadhar = Splash_Screen.sh.getString("aadhaarNum", null);
        getStdMob = Splash_Screen.sh.getString("StdMobile", null);
        getGuardianN = Splash_Screen.sh.getString("Gname", null);
        getStdemail = Splash_Screen.sh.getString("StdEmail", null);

        b_name = Splash_Screen.sh.getString("tb_name", null);
        j_role = Splash_Screen.sh.getString("trade_title", null);
        b_ID = Splash_Screen.sh.getString("tb_nsdc_id", null);
        enrol_num = Splash_Screen.sh.getString("SDMS_enrolment_number", null);

        logout = (ImageView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "You have successfully logout",
                        Toast.LENGTH_LONG).show();
                Splash_Screen.editor.remove("loginTest");
                Splash_Screen.editor.commit();

                Intent intent = new Intent(Enrollment_Form.this,Login.class);
                startActivity(intent);
            }
        });

        desc = (ImageView) findViewById(R.id.desc);

        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(Enrollment_Form.this);
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


       // image_name = (TextView) findViewById(R.id.image_name);
        fname = (EditText)findViewById(R.id.edit_fname);
        lname =(EditText) findViewById(R.id.ed_lname);
        dob = (EditText) findViewById(R.id.edt_bday);
        age = (EditText) findViewById(R.id.ed_age);
        mobile = (EditText) findViewById(R.id.ed_mobile);
        landline = (EditText) findViewById(R.id.ed_land);
        email = (EditText) findViewById(R.id.edt_email);
        adharnum = (EditText) findViewById(R.id.aadhar);
        doornum = (EditText) findViewById(R.id.dnum);
        streetName = (EditText) findViewById(R.id.strName);
        village = (EditText) findViewById(R.id.village);
        pin = (EditText) findViewById(R.id.pin);
        pdoornum = (EditText) findViewById(R.id.dnump);
        pstreetName = (EditText) findViewById(R.id.strNamep);
        pvillage = (EditText) findViewById(R.id.villagep);
        ppin = (EditText) findViewById(R.id.pinp);
        fathersName = (EditText) findViewById(R.id.FatherName);
        mname = (EditText) findViewById(R.id.mname);
        foccu = (EditText) findViewById(R.id.Foccu);
        moccu = (EditText) findViewById(R.id.moccu);
        fmobile = (EditText) findViewById(R.id.fatherMobile);
        fincome = (EditText) findViewById(R.id.fincome);
        ed_state = (EditText) findViewById(R.id.statepEdit);
        ed_dist = ( EditText) findViewById(R.id.dispEdit);

        ed_state.setVisibility(View.GONE);
        ed_dist.setVisibility(View.GONE);

        gname = (EditText) findViewById(R.id.gname);
        relationshp = (EditText) findViewById(R.id.relation);
        goccu = (EditText) findViewById(R.id.occu);
        gnum = (EditText) findViewById(R.id.Gcont);
        caste = (EditText)findViewById(R.id.caste);
        qualify = (EditText)findViewById(R.id.qua);
        poy = (EditText) findViewById(R.id.poy);
        inst = (EditText) findViewById(R.id.inst);
        marks = (EditText) findViewById(R.id.marks);
        wrkexp = (EditText) findViewById(R.id.year);
        currEmployer = (EditText) findViewById(R.id.cemploy);
        empAddress = (EditText) findViewById(R.id.empAddress);
        accntNum = (EditText) findViewById(R.id.accntNum);
        BankName = (EditText) findViewById(R.id.edbank);
        branch = (EditText) findViewById(R.id.edBranch);
        ifsc = (EditText) findViewById(R.id.edcode);

        uploadpic = (Button) findViewById(R.id.pic_upload);
        imageview = (ImageView) findViewById(R.id.adharImg);
        imageview.setVisibility(View.GONE);
        save = (Button) findViewById(R.id.save);
        back = (Button) findViewById(R.id.backbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Enrollment_Form.this,MainActivity.class);
                startActivity(intent);
            }
        });

        uploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // getImageFromAlbum();

                showPictureDialog();
            }
        });


        new GetMaritalStatus().execute();
        new GetReligionStatus().execute();
        new GetCategory().execute();
        new GetState().execute();
        new PermanentGetState().execute();

        //Spinner for marital status
        m_Status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
                Log.e("SPINNER CLICK", ">>>>>>>>>>>" + ">>>>>>..");
                m_status =   m_Status_spinner.getItemAtPosition(m_Status_spinner.getSelectedItemPosition()).toString();
                Log.e("Marital Status selectd", ">>>>>>>>>>>>" + m_status);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner for Religion
        reg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
                getfreligion =   reg.getItemAtPosition(reg.getSelectedItemPosition()).toString();
                Log.e("Religion selectd", ">>>>>>>>>>>>" + getfreligion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner for Category
        categ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
                getcat =   categ.getItemAtPosition(categ.getSelectedItemPosition()).toString();
                Log.e("Category selectd", ">>>>>>>>>>>>" + getcat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner for States [Current Address Details]
        state1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final State s = (State) state1.getItemAtPosition(position);

                getstate = s.getName();
                ((TextView)parent.getChildAt(0)).setText(getstate);
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);


                Log.e("Selected_State","???????????????/" + getstate);

                if(distAdapter != null){
                    distAdapter.clear();
                    distAdapter.notifyDataSetChanged();
                }

                new GetDistrict().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner for Districts [Current Address Details]
        dist1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final District d = (District) dist1.getItemAtPosition(position);

                getdist = d.getName();
                ((TextView)parent.getChildAt(0)).setText(getdist);
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);


                Log.e("Selected_Dist","???????????????/" + getdist);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner for State [Permanent Address Details]
        pstate1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final State s = (State) pstate1.getItemAtPosition(position);

                getpstate = s.getName();
                ((TextView)parent.getChildAt(0)).setText(getpstate);
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);


                Log.e("State_sel_Perm","????????/" + getpstate);

                if(pdistAdapter != null){
                    pdistAdapter.clear();
                    pdistAdapter.notifyDataSetChanged();
                }

                new PermanentGetDistrict().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner for Districts [Permanent Address Details]
        pdist1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final District d = (District) pdist1.getItemAtPosition(position);

                getpdist = d.getName();
                ((TextView)parent.getChildAt(0)).setText(getpdist);
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);


                Log.e("Sel_dist_perm","???????????????/" + getpdist);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sameAddress=(CheckBox)findViewById(R.id.checkBox1);

        radioGroup1 = (RadioGroup) findViewById(R.id.rdg1);
        rbmale = (RadioButton) findViewById(R.id.rbmale);
        rbfmale = (RadioButton) findViewById(R.id.rbfmale);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < radioGroup1.getChildCount(); i++) {
                    btn = (RadioButton) radioGroup1.getChildAt(i);
                    if (btn.getId() == checkedId) {
                        textgender = (String) btn.getText();
                        buttonnIDgender = Integer.toString(btn.getId());
                        Log.e("RADIOGRP1", " >>>>>>>>>>>>>>>" + textgender);

                        if(textgender.equals("Male")){
                            Gender = "M";

                            Log.e("Selected Gender", " >>>>>>>>>>>>>>>" + Gender);

                        }else if(textgender.equals("Female")){
                            Gender = "F";

                            Log.e("Selected Gender", " >>>>>>>>>>>>>>>" + Gender);
                        }

                        return;
                    } else {

                    }
                }
            }
        });


        dob.setInputType(InputType.TYPE_NULL);

        dob.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(v == dob)
                    showDialog(DATE_DIALOG_ID);
                return false;
            }
        });

        sameAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {

                    getdnum = doornum.getText().toString().trim();
                    getstrtname = streetName.getText().toString().trim();
                    getvillage = village.getText().toString().trim();
                    getpin = pin.getText().toString().trim();
                    s1 = stateAdapter.getPosition(getstate);
                    d1 = distAdapter.getPosition(getdist);
                    // checked
                    if(!getdnum.isEmpty() || !getstrtname.isEmpty() || !getvillage.isEmpty() || !getdist.isEmpty() || !getstate.isEmpty() || !getpin.isEmpty()){

                        pdoornum.setText(getdnum);
                        pstreetName.setText(getstrtname);
                        pvillage.setText(getvillage);

                        ed_state.setVisibility(View.VISIBLE);
                        ed_state.setText(getstate);
                        getpstate = ed_state.getText().toString();
                        pstate1.setVisibility(View.GONE);

                        ed_dist.setVisibility(View.VISIBLE);
                        ed_dist.setText(getdist);
                        getpdist = ed_dist.getText().toString();
                        pdist1.setVisibility(View.GONE);

                        ppin.setText(getpin);

                    }else{

                    }

                    if(getdnum.isEmpty()){
                        doornum.requestFocus();
                        doornum.setError("Please Enter Door Number");
                    }else if(getstrtname.isEmpty()){
                        streetName.requestFocus();
                        streetName.setError("Enter Street Name");
                    }else if(getvillage.isEmpty()){
                        village.requestFocus();
                        village.setError("Enter Village Name");
                    }else if(getdist.isEmpty()){
                        dist.requestFocus();
                        dist.setError("Enter District Name");
                    }else if(getstate.isEmpty()){
                        state.requestFocus();
                        state.setError("Enter State Name");
                    }else if(getpin.isEmpty()){
                        pin.requestFocus();
                        pin.setError("Enter Pincode");
                    }

                    Log.e("CHECKBOX CHECKED", ">>>>>>>>>>." + buttonView);
                }
                else
                {
                    // not checked
                    pdoornum.setText("");
                    pstreetName.setText("");
                    pvillage.setText("");
                    ed_state.setVisibility(View.GONE);
                    ed_dist.setVisibility(View.GONE);
                    pstate1.setVisibility(View.VISIBLE);
                    pdist1.setVisibility(View.VISIBLE);
                    ppin.setText("");
                    Log.e("CHECKBOX NOT CHECKED", ">>>>>>>>>>." + buttonView);
                }
            }
        });

        if(!get_stdname.isEmpty() || !getAadhar.isEmpty() || !getStdMob.isEmpty() || !getGuardianN.isEmpty() || !getStdemail.isEmpty()){
            fname.setText(get_stdname);
            adharnum.setText(getAadhar);
            mobile.setText(getStdMob);
            gname.setText(getGuardianN);
            email.setText(getStdemail);
        }

        if(getGuardianN.equals("null")){
            gname.setText("");
        }
        if(getAadhar.equals("0")){
            adharnum.setText("");
        }
        if(getStdMob.equals("0")){
            mobile.setText("");
        }

        save.setOnClickListener(listener);

    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        byte[] data1;
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);

                    imageview.setVisibility(View.VISIBLE);
                    Toast.makeText(Enrollment_Form.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageview.setImageBitmap(bitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    byte[] b = baos.toByteArray();
                    base64 = Base64.encodeToString(b, Base64.DEFAULT);

                    Log.e("AadharPic B64 Gallery", ">>>>>>>>>>>>>." + base64);


                    File f = new File(path);
                    get_adhar_picname = f.getName();


                    Log.e("Adhar Image name", ">>>>>>>>>>>>>>>"  + get_adhar_picname);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Enrollment_Form.this, "Failed!", Toast.LENGTH_SHORT).show();
                }

            }

        } else if (requestCode == CAMERA) {
            imageview.setVisibility(View.VISIBLE);
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);

            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG,100,baos1);
            byte[] b1 = baos1.toByteArray();
            base64 = Base64.encodeToString(b1, Base64.DEFAULT);

            Log.e("AadharPic B64 cam", ">>>>>>>>>>>>>." + base64);

            String camera_img = saveImage(thumbnail);

            File f = new File(camera_img);
            get_adhar_picname = f.getName();

            Log.e("Camera IMAGE name", ">>>>>>>>>>>>>>>" + get_adhar_picname);

            Toast.makeText(Enrollment_Form.this, "Image Saved!", Toast.LENGTH_SHORT).show();

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


    View.OnClickListener listener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {

            Log.e("GET STATES DIST", " ??????????????" + getstate + " " + getdist + " " + getpstate + " " + getpdist);

            editTextValidation();

            if(haveNetworkConnection()){

                if(!getfname.isEmpty() && !getlname.isEmpty() && !Gender.isEmpty() && !getdob.isEmpty() &&
                         dob.length() != 0 && !getmobile.isEmpty() && getmobile.length() >= 10 &&  !getEmail.isEmpty() && !getadhar.isEmpty() && base64 != null &&
                        !getdnum.isEmpty() && !getstrtname.isEmpty() && !getvillage.isEmpty() && !getdist.isEmpty() && !getstate.isEmpty() &&
                        !getpin.isEmpty() && !getfathern.isEmpty() && !getmname.isEmpty() && !getFmobile.isEmpty() && getFmobile.length() >= 10 && !getqua.isEmpty() && !getpoy.isEmpty() &&
                        !getinst.isEmpty() && !getmarks.isEmpty())
                {

                    new EnrollmentFormPostRequest().execute();

                }
            }else{
                editTextValidation();

                if(!getfname.isEmpty() && !getlname.isEmpty() && !Gender.isEmpty() && !getdob.isEmpty() &&
                        dob.length() != 0 && !getmobile.isEmpty() && getmobile.length() >= 10 &&  !getEmail.isEmpty() && !getadhar.isEmpty() && base64 != null &&
                        !getdnum.isEmpty() && !getstrtname.isEmpty() && !getvillage.isEmpty() && !getdist.isEmpty() && !getstate.isEmpty() &&
                        !getpin.isEmpty() && !getfathern.isEmpty() && !getmname.isEmpty() && !getFmobile.isEmpty() && getFmobile.length() >= 10 && !getqua.isEmpty() && !getpoy.isEmpty() &&
                        !getinst.isEmpty() && !getmarks.isEmpty() && isEmailValid(getEmail))
                {


                    if(Status.isEmpty()){
                        Status = "null";
                    }else if(getage.isEmpty()){
                        getage = "null";
                    }else if(getlandline.isEmpty()){
                        getlandline = "null";
                    }else if(get_adhar_picname.isEmpty()){
                        get_adhar_picname = "null";
                    }else if(getpdnum.isEmpty()){
                        getpdnum = "null";
                    }else if(getpstrtn.isEmpty()){
                        getpstrtn = "null";
                    }else if(getpvillage.isEmpty()){
                        getpvillage = "null";
                    }else if(getpdist.isEmpty()){
                        getpdist = "null";
                    }else if(getpstate.isEmpty()){
                        getpstate = "null";
                    }else if(getppin.isEmpty()){
                        getppin = "null";
                    }else if(getfoccu.isEmpty()){
                        getfoccu = "null";
                    }else if(getmoccu.isEmpty()){
                        getmoccu = "null";
                    }else if(getfincome.isEmpty()){
                        getfincome = "null";
                    }else if(getfreligion.isEmpty()){
                        getfreligion = "null";
                    }else if(getgname.isEmpty()){
                        getgname = "null";
                    }else if(getrship.isEmpty()){
                        getrship = "null";
                    }else if(getgoccu.isEmpty()){
                        getgoccu = "null";
                    }else if(getgnum.isEmpty()){
                        getgnum = "null";
                    }else if(getcat.isEmpty()){
                        getcat = "null";
                    }else if(getcaste.isEmpty()){
                        getcaste = "null";
                    }else if(getwrk.isEmpty()){
                        getwrk = "null";
                    }else if(getcurrEmp.isEmpty()){
                        getcurrEmp = "null";
                    }else if(getempAddr.isEmpty()){
                        getempAddr = "null";
                    }else if(getacntNum.isEmpty()){
                        getacntNum = "null";
                    }else if(getbname.isEmpty()){
                        getbname = "null";
                    }else if(getbranch.isEmpty()){
                        getbranch = "null";
                    }else if(getcode.isEmpty()){
                        getcode = "null";
                    }

                    boolean recordExists= mydb.checkStdIDExistEnrol(mydb.TABLE_NAME_ENROLMENT ,mydb.E_col_1 ,get_stdID);
                    if(recordExists)
                    {


                    }else{

                        mydb.addEnrollmentData(get_stdID,getfname,getlname,Gender,getdob,m_status,getage,getmobile,getlandline,getEmail,getadhar,base64,get_adhar_picname,getdnum,getstrtname,
                                getvillage,getdist,getstate,getpin,getpdnum,getpstrtn,getpvillage,getpdist,getpstate,getppin,getfathern,
                                getmname,getfoccu,getmoccu,getFmobile,getfincome,getfreligion,getgname,getrship,getgoccu,getgnum,getcat,getcaste,
                                getqua,getwrk,getpoy,getcurrEmp,getinst,getempAddr,getmarks,getacntNum,getbname,getbranch,getcode,API_KEY);
                    }

                    Intent intent = new Intent(Enrollment_Form.this, OtpScreen.class);
                    startActivity(intent);

                    Toast.makeText(Enrollment_Form.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                }

            }

        }
    };


    public void editTextValidation(){
        getfname = fname.getText().toString().trim();
        getlname = lname.getText().toString().trim();
        getdob = dob.getText().toString().trim();
        getage = age.getText().toString().trim();
        getmobile = mobile.getText().toString().trim();
        getlandline = landline.getText().toString().trim();
        getEmail = email.getText().toString().trim();
        getadhar = adharnum.getText().toString().trim();
        getdnum = doornum.getText().toString().trim();
        getstrtname = streetName.getText().toString().trim();
        getvillage = village.getText().toString().trim();
        getpin = pin.getText().toString().trim();
        getpdnum = pdoornum.getText().toString().trim();
        getpstrtn = pstreetName.getText().toString().trim();
        getpvillage = pvillage.getText().toString().trim();
        getppin = ppin.getText().toString().trim();
        getfathern = fathersName.getText().toString().trim();
        getmname = mname.getText().toString().trim();
        getfoccu = foccu.getText().toString().trim();
        getmoccu = moccu.getText().toString().trim();
        getFmobile = fmobile.getText().toString().trim();
        getfincome = fincome.getText().toString().trim();
        getgname = gname.getText().toString().trim();
        getrship = relationshp.getText().toString().trim();
        getgoccu = goccu.getText().toString().trim();
        getgnum = gnum.getText().toString().trim();
        getcaste = caste.getText().toString().trim();
        getqua = qualify.getText().toString().trim();
        getpoy = poy.getText().toString().trim();
        getinst = inst.getText().toString().trim();
        getmarks = marks.getText().toString().trim();
        getwrk = wrkexp.getText().toString().trim();
        getcurrEmp = currEmployer.getText().toString().trim();
        getempAddr = empAddress.getText().toString().trim();
        getacntNum = accntNum.getText().toString().trim();
        getbname = BankName.getText().toString().trim();
        getbranch = branch.getText().toString().trim();
        getcode = ifsc.getText().toString().trim();


        if (getfname.isEmpty()) {
            fname.requestFocus();
            fname.setError("Please enter First Name");
        }  else if (getlname.isEmpty()) {
            lname.requestFocus();
            lname.setError("Please enter Last Name");
        } else if (!rbfmale.isChecked() && !rbmale.isChecked()) {
            Toast.makeText(getApplicationContext(),
                    "Please select Gender", Toast.LENGTH_LONG).show();
        } else if (getdob.isEmpty()) {
            dob.requestFocus();
            Toast.makeText(getApplicationContext(),
                    "Please enter Date of Birth", Toast.LENGTH_LONG).show();
        } else if(getage.isEmpty()){
            age.requestFocus();
            age.setError("Please select your Age");
        }else if(getmobile.length() < 10){
            mobile.requestFocus();
            mobile.setError("Must exceed 10 characters!");
        }else if (getmobile.isEmpty()) {
            mobile.requestFocus();
            mobile.setError("Please enter Mobile Number");
        }
        else if (getEmail.isEmpty()|| (!isEmailValid(getEmail))) {
            email.requestFocus();
            email.setError( "Please enter valid Email Id");
        }else if(getadhar.isEmpty()){
            adharnum.requestFocus();
            adharnum.setError("Please Enter Aadhaar Card Number");
        } else if(base64 == null){
            Toast.makeText(Enrollment_Form.this, "Select Aadhaar Photo", Toast.LENGTH_SHORT).show();
        } else if(getdnum.isEmpty()){
            doornum.requestFocus();
            doornum.setError("Please Enter Door Number");
        }else if(getstrtname.isEmpty()){
            streetName.requestFocus();
            streetName.setError("Enter Street Name");
        }else if(getvillage.isEmpty()){
            village.requestFocus();
            village.setError("Enter Village Name");
        }else if(getdist.isEmpty()){
            dist.requestFocus();
            dist.setError("Enter District Name");
        }else if(getstate.isEmpty()){
            state.requestFocus();
            state.setError("Enter State Name");
        } else if(getpin.isEmpty()){
            pin.requestFocus();
            pin.setError("Enter Pincode");
        }else if(getfathern.isEmpty()){
            fathersName.requestFocus();
            fathersName.setError("Enter Father Name");
        }else if(getmname.isEmpty()){
            mname.requestFocus();
            mname.setError("Enter Mother Name");
        }else if(getFmobile.isEmpty()){
            fmobile.requestFocus();
            fmobile.setError("Enter Mobile Number");
        }else if(getFmobile.length() < 10){
            fmobile.requestFocus();
            fmobile.setError("Must exceed 10 characters!");
        } else if(getqua.isEmpty()){
            qualify.requestFocus();
            qualify.setError("Enter Qualification");
        }else if(getpoy.isEmpty()){
            poy.requestFocus();
            poy.setError("Enter Pass Out Year");
        }else if(getinst.isEmpty()){
            inst.requestFocus();
            inst.setError("Enter Institution");
        }else if(getmarks.isEmpty()){
            marks.requestFocus();
            marks.setError("Enter Marks Obtained");
        }

    }


    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        ageS = ageInt.toString();

        Log.e("AGES",">>>>>>>>>" +ageS);

        return ageS;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, cyear, cmonth, cday);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // onDateSet method
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date_selected = String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(year);

            String new_date = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);

            //Toast.makeText(getApplicationContext(), "Selected Date is ="+date_selected, Toast.LENGTH_SHORT).show();
            dob.setText(date_selected);

            getdob = dob.getText().toString();

            Log.e("GET_DOB", ">>>>>>>>" +getdob);

            getAge(year, monthOfYear,dayOfMonth);

            age.setText(ageS);

            Log.e("DATEPICKER",">>>>>>>>" +dayOfMonth + monthOfYear +year);
        }
    };


    public void dialogBoxForInternet() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Enrollment_Form.this);
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
    private class EnrollmentFormPostRequest extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {

            // Showing progress dialog
            pDialog = new ProgressDialog(Enrollment_Form.this);
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

                    if (message.equals("Success, Details Updated")) {

                        Toast.makeText(getApplicationContext(),
                                "Success, Details Updated", Toast.LENGTH_LONG).show();

                        Log.e("SUCCES MSG", ">>>>>>>>>>>>>>>" + message);

                        Intent intent = new Intent(Enrollment_Form.this, OtpScreen.class);
                        startActivity(intent);
                        pDialog.dismiss();

                    } else if (message.equals("Access Denied ! Authentication Failed")) {


                        Toast.makeText(getApplicationContext(),
                                "Access Denied", Toast.LENGTH_LONG).show();

                        Log.e("FAILED MSG", ">>>>>>>>>>>>>>>" + message);
                        pDialog.dismiss();

                    }else if (message.equals("Failure, Unable to upload aadhar photo")) {


                        Toast.makeText(getApplicationContext(),
                                "Failure, Unable to upload aadhar photo", Toast.LENGTH_LONG).show();

                        Log.e("FAILED MSG", ">>>>>>>>>>>>>>>" + message);
                        pDialog.dismiss();

                    }
                }else{


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        protected JSONObject doInBackground(Void... params) {

            return postJsonObject(Enroll_Form_url, makingJson());

        }
    }

    private JSONObject makingJson() {

        JSONObject postDataParams = new JSONObject();

        try {

            //following parameters to the API
            postDataParams.put("key", API_KEY);
            postDataParams.put("student_id", get_stdID);
            postDataParams.put("first_name", getfname);
            postDataParams.put("last_name", getlname);
            postDataParams.put("gender", Gender);
            postDataParams.put("date_of_birth", getdob);
            postDataParams.put("marital_status", m_status);
            postDataParams.put("age", getage);
            postDataParams.put("student_mobile", getmobile);
            postDataParams.put("student_landline_no", getlandline);
            postDataParams.put("student_email", getEmail);
            postDataParams.put("aadhaar_number", getadhar);
            postDataParams.put("aadhaar_photo", base64);
            postDataParams.put("aadhaar_photo_name", get_adhar_picname);
            postDataParams.put("curr_door_no", getdnum);
            postDataParams.put("curr_street_name", getstrtname);
            postDataParams.put("curr_village_city", getvillage);
            postDataParams.put("curr_district", getdist);
            postDataParams.put("curr_state", getstate);
            postDataParams.put("curr_pincode", getpin);
            postDataParams.put("perm_door_no", getpdnum);
            postDataParams.put("perm_street_name", getpstrtn);
            postDataParams.put("perm_village_city", getpvillage);
            postDataParams.put("perm_district", getpdist);
            postDataParams.put("perm_state", getpstate);
            postDataParams.put("perm_pincode", getppin);
            postDataParams.put("father_name", getfathern);
            postDataParams.put("mother_name", getmname);
            postDataParams.put("father_occupation", getfoccu);
            postDataParams.put("mother_occupation", getmoccu);
            postDataParams.put("father_cont_no", getFmobile );
            postDataParams.put("family_income", getfincome);
            postDataParams.put("religion", getfreligion );
            postDataParams.put("guardian_name", getgname);
            postDataParams.put("relationship", getrship);
            postDataParams.put("guardian_occupation", getgoccu);
            postDataParams.put("guardian_contact_no", getgnum);
            postDataParams.put("guardian_category", getcat);
            postDataParams.put("guardian_caste", getcaste);
            postDataParams.put("educational_qualification", getqua);
            postDataParams.put("exp_yrs", getwrk);
            postDataParams.put("passed_year", getpoy);
            postDataParams.put("current_employer", getcurrEmp);
            postDataParams.put("institution", getinst);
            postDataParams.put("employer_address", getempAddr);
            postDataParams.put("marks_obtained", getmarks);
            postDataParams.put("bank_account_no", getacntNum);
            postDataParams.put("bank_name", getbname);
            postDataParams.put("bank_branch", getbranch);
            postDataParams.put("ifsc_code", getcode);


           /* Log.e("POST PARAMS", "??????????" + get_stdID + " " + getdnum + " " +getstrtname + " " + getvillage + " " + getdist + " " +
                    getstate + " " + getpin + " " + getpdnum + " " + getpstrtn + " " + getpdist + " " + getpstate + " " + getppin );*/

            Log.e("ENROLLMENT PARAM", ">>>>>>>>>>.." + getfname + " " + getlname + " " + Gender + " " + getdob + " " + getmobile + " "+
                    getEmail + " " + getadhar + " " + base64 + " " + getdnum + " " + getstrtname + " " + getvillage + " " + getdist + " " +
                    getstate + " " + getpin + " " + getfathern + " " + getmname + " " +   getFmobile + " " + getqua + " " + getpoy + " "
                    + getinst + " " + getmarks + " " );

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

    //Get enrollment data from database
    public void getDBData(Context context){
        mydb = new DBHelper(context);
        Cursor data = mydb.getEnrollmentData(mydb);

        if(data.getCount()>0 && data.moveToFirst())
            Arr_StudentIDS = new ArrayList<>();
        do{

            String studnt_id = data.getString(1);

            Arr_StudentIDS.add(studnt_id);

            Log.e("ARRAY SIZE ENROLL", ">>>>>>>>>>>"  + Arr_StudentIDS.size() + " " + studnt_id);

            for(int i = 0 ; i < Arr_StudentIDS.size() ; i++){

                get = Arr_StudentIDS.get(i);
            }

            Log.e("GetStudntID enroll", ">>>>>>>>>>>"  + get);
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
        Cursor data = mydb.getEnrollSingleVal(mydb,val);

        Log.e("SINGLEVAL STDID Enroll", ">>>>>>>>>>>>>>>>" + val);

        if(data.getCount()>0 && data.moveToFirst())
            do{

                e_stdid = data.getString(1);
                e_getfname = data.getString(2);
                e_getlname = data.getString(3);
                e_gender = data.getString(4);
                e_getdob = data.getString(5);
                e_status = data.getString(6);
                e_getage = data.getString(7);
                e_getmobile = data.getString(8);
                e_getlandline = data.getString(9);
                e_getEmail = data.getString(10);
                e_getadhar = data.getString(11);
                e_base64 = data.getString(12);
                e_get_adhar_picname = data.getString(13);
                e_getdnum = data.getString(14);
                e_getstrtname = data.getString(15);
                e_getvillage = data.getString(16);
                e_getdist = data.getString(17);
                e_getstate = data.getString(18);
                e_getpin = data.getString(19);
                e_getpdnum = data.getString(20);
                e_getpstrtn = data.getString(21);
                e_getpvillage = data.getString(22);
                e_getpdist = data.getString(23);
                e_getpstate = data.getString(24);
                e_getppin = data.getString(25);
                e_getfathern = data.getString(26);
                e_getmname = data.getString(27);
                e_getfoccu = data.getString(28);
                e_getmoccu = data.getString(29);
                e_getFmobile = data.getString(30);
                e_getfincome = data.getString(31);
                e_getfreligion = data.getString(32);
                e_getgname = data.getString(33);
                e_getrship = data.getString(34);
                e_getgoccu = data.getString(35);
                e_getgnum = data.getString(36);
                e_getcat = data.getString(37);
                e_getcaste = data.getString(38);
                e_getqua = data.getString(39);
                e_expyears = data.getString(40);
                e_getpoy = data.getString(41);
                e_getcurrEmp = data.getString(42);
                e_getinst = data.getString(43);
                e_getempAddr = data.getString(44);
                e_getmarks = data.getString(45);
                e_getacntNum = data.getString(46);
                e_getbname = data.getString(47);
                e_getbranch = data.getString(48);
                e_getcode = data.getString(49);
                e_apikey = data.getString(50);

                Log.e("DO WHILE ENROLL", "Student ID" + e_stdid + " " + e_getfname + " " + e_getlname);

                // Log.e("Single DBVal enroll", ">>>>>>>>>>" +  e_getfname + " " + "" + e_getlname);

                final EnrollmentFormPostRequestOffline fDownload = new EnrollmentFormPostRequestOffline();
                fDownload.execute();

                // new EnrollmentFormPostRequestOffline().execute();

            }while (data.moveToNext());

    }

    // for getting the questions from server
    private class EnrollmentFormPostRequestOffline extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);


            try {
                if (jsonObject != null && jsonObject.getString("status_message") != null) {

                    String message = jsonObject.getString("status_message");  // Message

                    Log.e("MESSAGE", ">>>>>>>>>>>>>>>>" + message);

                    if (message.equals("Success, Details Updated")) {

                        Log.e("SUCCES ENROLL OFFLINE", ">>>>>>>>>>>>>>>" +  " " + message );

                        mydb.deleteSingleEnrollData(e_stdid);
                        Log.e("DB VAL DEL Enroll" , " ?????????????" + e_stdid);

                    } else if (message.equals("Access Denied ! Authentication Failed")) {

                        Log.e("FAILED MSG", ">>>>>>>>>>>>>>>" + message);

                    }
                }else{


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        protected JSONObject doInBackground(Void... params) {

            return postJsonObjectoff(Enroll_Form_url, makingJsonoff());

        }
    }

    private JSONObject makingJsonoff() {

        JSONObject postDataParams = new JSONObject();

        try {
            //following parameters to the API
            postDataParams.put("key", e_apikey);
            postDataParams.put("student_id", e_stdid);
            postDataParams.put("first_name", e_getfname);
            postDataParams.put("last_name", e_getlname);
            postDataParams.put("gender", e_gender);
            postDataParams.put("date_of_birth", e_getdob);
            postDataParams.put("marital_status", e_status);
            postDataParams.put("age", e_getage);
            postDataParams.put("student_mobile", e_getmobile);
            postDataParams.put("student_landline_no", e_getlandline);
            postDataParams.put("student_email", e_getEmail);
            postDataParams.put("aadhaar_number", e_getadhar);
            postDataParams.put("aadhaar_photo", e_base64);
            postDataParams.put("aadhaar_photo_name", e_get_adhar_picname);
            postDataParams.put("curr_door_no", e_getdnum);
            postDataParams.put("curr_street_name", e_getstrtname);
            postDataParams.put("curr_village_city", e_getvillage);
            postDataParams.put("curr_district", e_getdist);
            postDataParams.put("curr_state", e_getstate);
            postDataParams.put("curr_pincode", e_getpin);
            postDataParams.put("perm_door_no", e_getpdnum);
            postDataParams.put("perm_street_name", e_getpstrtn);
            postDataParams.put("perm_village_city", e_getpvillage);
            postDataParams.put("perm_district", e_getpdist);
            postDataParams.put("perm_state", e_getpstate);
            postDataParams.put("perm_pincode", e_getppin);
            postDataParams.put("father_name", e_getfathern);
            postDataParams.put("mother_name", e_getmname);
            postDataParams.put("father_occupation", e_getfoccu);
            postDataParams.put("mother_occupation", e_getmoccu);
            postDataParams.put("father_cont_no", e_getFmobile );
            postDataParams.put("family_income", e_getfincome);
            postDataParams.put("religion", e_getfreligion );
            postDataParams.put("guardian_name", e_getgname);
            postDataParams.put("relationship", e_getrship);
            postDataParams.put("guardian_occupation", e_getgoccu);
            postDataParams.put("guardian_contact_no", e_getgnum);
            postDataParams.put("guardian_category", e_getcat);
            postDataParams.put("guardian_caste", e_getcaste);
            postDataParams.put("educational_qualification", e_getqua);
            postDataParams.put("exp_yrs", e_getwrk);
            postDataParams.put("passed_year", e_getpoy);
            postDataParams.put("current_employer", e_getcurrEmp);
            postDataParams.put("institution", e_getinst);
            postDataParams.put("employer_address", e_getempAddr);
            postDataParams.put("marks_obtained", e_getmarks);
            postDataParams.put("bank_account_no", e_getacntNum);
            postDataParams.put("bank_name", e_getbname);
            postDataParams.put("bank_branch", e_getbranch);
            postDataParams.put("ifsc_code", e_getcode);


            Log.e("POST PARAMS 1", "??????????"  + " " + e_getdnum + " " +getstrtname + " " + getvillage + " " + getdist + " " +
                    getstate + " " + getpin + " " + getpdnum + " " + getpstrtn + " " + getpdist + " " + getpstate + " " + getppin );


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataParams;
    }



    public JSONObject postJsonObjectoff(String url, JSONObject loginJobj) {
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
                result = convertInputStreamToStringOff(inputStream);
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

    private String convertInputStreamToStringOff(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


    public void deleteSpValues(){

        mydb.deleteRecordEnrollment();

        mydb = new DBHelper(this);
        Cursor data = mydb.getEnrollmentData(mydb);

        if(data != null) {

            Log.e("Enrollment Data exists", ">>>>>>>>>>>.." + data);
        }else{

            Log.e("No values Enroll Table", "?????????????" + data);
        }

        Log.e("Enrollment Data","ENROLLMENT" + "TABLE DELETED");

    }


    //Getting the values of marital status from server
    // for getting the questions from server
    private class GetMaritalStatus extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            try {
                if (jsonObject != null && jsonObject.getString("status_message") != null) {

                    String message = jsonObject.getString("status_message");  // Message

                    Log.e("MESSAGE", ">>>>>>>>>>>>>>>>" + message);

                    if (message.contains("Success")) {

                        JSONArray a = jsonObject.getJSONArray("arr_marital_status_options");
                        for (int i = 0; i < a.length(); i++) {
                            Marital_Status.add(a.getString(i));
                            Log.e("MArital Status list", "????????????" +  a.getString(i));
                        }
                        m_Status_spinner.setAdapter(new ArrayAdapter<String>(Enrollment_Form.this, android.R.layout.simple_spinner_dropdown_item, Marital_Status));


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

            return postJsonObjectMstatus(m_status_api, makingJsonMstatus());

        }
    }

    private JSONObject makingJsonMstatus() {

        JSONObject postDataParams = new JSONObject();

        try {

            //following parameters to the API
            postDataParams.put("key", API_KEY);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataParams;
    }



    public JSONObject postJsonObjectMstatus(String url, JSONObject loginJobj) {
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
                result = convertIstostrMstatus(inputStream);
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

    private String convertIstostrMstatus(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


    // for getting the religion values from server
    private class GetReligionStatus extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            try {
                if (jsonObject != null && jsonObject.getString("status_message") != null) {

                    String message = jsonObject.getString("status_message");  // Message

                    Log.e("RELIGION MESSAGE", ">>>>>>>>>>>>>>>>" + message);

                    if (message.contains("Success")) {


                        JSONObject jo = jsonObject.getJSONObject("arr_religion");
                        String reg_1 = jo.getString("1");
                        String reg_2 = jo.getString("2");
                        String reg_3 = jo.getString("3");
                        String reg_4 = jo.getString("4");
                        String reg_5 = jo.getString("5");
                        String reg_6 = jo.getString("6");
                        String reg_7 = jo.getString("7");
                        String reg_8 = jo.getString("8");
                        String reg_9 = jo.getString("9");

                        Religion.add(reg_1);
                        Religion.add(reg_2);
                        Religion.add(reg_3);
                        Religion.add(reg_4);
                        Religion.add(reg_5);
                        Religion.add(reg_6);
                        Religion.add(reg_7);
                        Religion.add(reg_8);
                        Religion.add(reg_9);

                        Log.e("REG1", ">>>>>>>>>>" + reg_1 + " " +reg_2);

                        Log.e("Religion list", "????????????" +  Religion);

                        reg.setAdapter(new ArrayAdapter<String>(Enrollment_Form.this, android.R.layout.simple_spinner_dropdown_item, Religion));


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

            return postJsonObjectReg(get_religion_api, makingJsonReg());

        }
    }

    private JSONObject makingJsonReg() {

        JSONObject postDataParams = new JSONObject();

        try {

            //following parameters to the API
            postDataParams.put("key", API_KEY);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataParams;
    }



    public JSONObject postJsonObjectReg(String url, JSONObject loginJobj) {
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
                result = convertIstostrReg(inputStream);
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

    private String convertIstostrReg(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


    // for getting the Category values from server
    private class GetCategory extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject1) {
            super.onPostExecute(jsonObject1);

            try {
                if (jsonObject1 != null && jsonObject1.getString("status_message") != null) {

                    String message = jsonObject1.getString("status_message");  // Message

                    Log.e("Category MESSAGE", ">>>>>>>>>>>>>>>>" + message);

                    if (message.contains("Success")) {

                        JSONArray a = jsonObject1.getJSONArray("arr_category");
                        for (int i = 0; i < a.length(); i++) {
                            Cat.add(a.getString(i));
                            Log.e("MArital Status list", "????????????" +  a.getString(i));
                        }

                        Log.e("Category list", "????????????" +  Cat);

                        categ.setAdapter(new ArrayAdapter<String>(Enrollment_Form.this, android.R.layout.simple_spinner_dropdown_item, Cat));


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

            return postJsonObjectCat(get_category, makingJsonCat());

        }
    }

    private JSONObject makingJsonCat() {

        JSONObject postDataParams = new JSONObject();

        try {

            //following parameters to the API
            postDataParams.put("key", API_KEY);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataParams;
    }



    public JSONObject postJsonObjectCat(String url, JSONObject loginJobj) {
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
                result = convertIstostrCat(inputStream);
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

    private String convertIstostrCat(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


    // for getting the State values from server
    private class GetState extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject1) {
            super.onPostExecute(jsonObject1);

            State stateObject;

            try {
                if (jsonObject1 != null && jsonObject1.getString("status_message") != null) {

                    String message = jsonObject1.getString("status_message");  // Message

                    Log.e("States MESSAGE", ">>>>>>>>>>>>>>>>" + message);

                    if (message.contains("Success")) {

                        JSONArray a = jsonObject1.getJSONArray("arr_state");
                        for (int i = 0; i < a.length(); i++) {

                            JSONObject jObj = a.getJSONObject(i);
                            String id = jObj.getString("id");
                            String state_name = jObj.getString("name");

                            stateObject = new State(id,state_name);
                            State1.add(stateObject);
                            Log.e("State list", "????????????" +  a.getString(i));
                        }
/*
                        Log.e("Category list", "????????????" +  Cat);

                        state1.setAdapter(new ArrayAdapter<String>(Enrollment_Form.this, android.R.layout.simple_spinner_dropdown_item, State1));*/

                        stateAdapter = new ArrayAdapter<State>(getApplicationContext(), R.layout.spinner_item, State1) {

                            private TextView text;

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {

                                errorText = (TextView) dist1.getSelectedView();

                                if (convertView == null) {
                                    convertView = mInflator.inflate(R.layout.spinner_item, null);
                                }

                                text = (TextView) convertView.findViewById(R.id.spinnerTarget);
                                if (!selected_state) {
                                    text.setText("Select State");
                                } else {
                                    text.setText(State1.get(position).getName());
                                    text.setTextColor(getResources().getColor(R.color.primary_text));
                                }
                                return convertView;
                            }
                        };

                        if(State1.isEmpty()){
                            Toast.makeText(getApplicationContext(), "No States Found",
                                    Toast.LENGTH_LONG).show();
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }else{
                            stateAdapter.setDropDownViewResource(R.layout.spinner_item);
                            state1.setAdapter(stateAdapter);
                            state1.setEnabled(true);

                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }


                        /* dist1.setAdapter(new ArrayAdapter<String>(Enrollment_Form.this, android.R.layout.simple_spinner_dropdown_item, Dist1));*/

                        Log.e("District list", "????????????" +  Dist1);



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

            return postJsonObjectState(get_state, makingJsonState());

        }
    }

    private JSONObject makingJsonState() {

        JSONObject postDataParams = new JSONObject();

        try {

            //following parameters to the API
            postDataParams.put("key", API_KEY);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataParams;
    }



    public JSONObject postJsonObjectState(String url, JSONObject loginJobj) {
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
                result = convertIstostrState(inputStream);
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

    private String convertIstostrState(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }



    // for getting the Districts values from server
    private class GetDistrict extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject1) {
            super.onPostExecute(jsonObject1);

            District distObject;

            try {
                if (jsonObject1 != null && jsonObject1.getString("status_message") != null) {

                    String message = jsonObject1.getString("status_message");  // Message

                    Log.e("District MESSAGE", ">>>>>>>>>>>>>>>>" + message);

                    if (message.contains("Success")) {

                        JSONArray a = jsonObject1.getJSONArray("arr_district");
                        for (int i = 0; i < a.length(); i++) {

                            JSONObject jObj = a.getJSONObject(i);
                            String id = jObj.getString("id");
                            String dist_name = jObj.getString("name");

                            distObject = new District(id, dist_name);

                            Dist1.add(distObject);

                        }


                        distAdapter = new ArrayAdapter<District>(getApplicationContext(), R.layout.spinner_item, Dist1) {

                            private TextView text;

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {

                                errorText = (TextView) dist1.getSelectedView();

                                if (convertView == null) {
                                    convertView = mInflator.inflate(R.layout.spinner_item, null);
                                }

                                text = (TextView) convertView.findViewById(R.id.spinnerTarget);
                                if (!selected_dist) {
                                    text.setText("Select District");
                                } else {
                                    text.setText(Dist1.get(position).getName());
                                    text.setTextColor(getResources().getColor(R.color.primary_text));
                                }
                                return convertView;
                            }
                        };

                        if(Dist1.isEmpty()){
                            Toast.makeText(getApplicationContext(), "No Districts Found",
                                    Toast.LENGTH_LONG).show();
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }else{
                            distAdapter.setDropDownViewResource(R.layout.spinner_item);
                            dist1.setAdapter(distAdapter);
                            dist1.setEnabled(true);

                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }


                       /* dist1.setAdapter(new ArrayAdapter<String>(Enrollment_Form.this, android.R.layout.simple_spinner_dropdown_item, Dist1));*/

                        Log.e("District list", "????????????" +  Dist1);


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

            return postJsonObjectDist(get_district, makingJsonDist());

        }
    }

    private JSONObject makingJsonDist() {

        JSONObject postDataParams = new JSONObject();

        try {

            //following parameters to the API
            postDataParams.put("key", API_KEY);
            postDataParams.put("state_name", getstate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataParams;
    }



    public JSONObject postJsonObjectDist(String url, JSONObject loginJobj) {
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
                result = convertIstostrDist(inputStream);
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

    private String convertIstostrDist(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(this,MainActivity.class);
        finish();
        startActivity(a);
    }


    // for getting the State values from server [Permanent Address]
    private class PermanentGetState extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject1) {
            super.onPostExecute(jsonObject1);

            State stateObject;

            try {
                if (jsonObject1 != null && jsonObject1.getString("status_message") != null) {

                    String message = jsonObject1.getString("status_message");  // Message

                    Log.e("States MESSAGE", ">>>>>>>>>>>>>>>>" + message);

                    if (message.contains("Success")) {

                        JSONArray a = jsonObject1.getJSONArray("arr_state");
                        for (int i = 0; i < a.length(); i++) {

                            JSONObject jObj = a.getJSONObject(i);
                            String id = jObj.getString("id");
                            String state_name = jObj.getString("name");

                            stateObject = new State(id,state_name);
                            pState1.add(stateObject);
                            Log.e("State list", "????????????" +  a.getString(i));
                        }
/*
                        Log.e("Category list", "????????????" +  Cat);

                        state1.setAdapter(new ArrayAdapter<String>(Enrollment_Form.this, android.R.layout.simple_spinner_dropdown_item, State1));*/

                        pstateAdapter = new ArrayAdapter<State>(getApplicationContext(), R.layout.spinner_item, pState1) {

                            private TextView text;

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {

                                errorText = (TextView) dist1.getSelectedView();

                                if (convertView == null) {
                                    convertView = mInflator.inflate(R.layout.spinner_item, null);
                                }

                                text = (TextView) convertView.findViewById(R.id.spinnerTarget);
                                if (!selected_pstate) {
                                    text.setText("Select State");
                                } else {
                                    text.setText(pState1.get(position).getName());
                                    text.setTextColor(getResources().getColor(R.color.primary_text));
                                }
                                return convertView;
                            }
                        };

                        if(pState1.isEmpty()){
                            Toast.makeText(getApplicationContext(), "No States Found",
                                    Toast.LENGTH_LONG).show();
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }else{
                            pstateAdapter.setDropDownViewResource(R.layout.spinner_item);
                            pstate1.setAdapter(pstateAdapter);
                            pstate1.setEnabled(true);

                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }


                        /* dist1.setAdapter(new ArrayAdapter<String>(Enrollment_Form.this, android.R.layout.simple_spinner_dropdown_item, Dist1));*/

                        Log.e("District list", "????????????" +  Dist1);



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

            return postJsonObjectStatePerm(get_state, makingJsonStatePerm());

        }
    }

    private JSONObject makingJsonStatePerm() {

        JSONObject postDataParams = new JSONObject();

        try {

            //following parameters to the API
            postDataParams.put("key", API_KEY);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataParams;
    }



    public JSONObject postJsonObjectStatePerm(String url, JSONObject loginJobj) {
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
                result = convertIstostrStatePerm(inputStream);
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

    private String convertIstostrStatePerm(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }



    // for getting the Districts values from server [permanent Address]
    private class PermanentGetDistrict extends AsyncTask<Void, String, JSONObject> {

        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject1) {
            super.onPostExecute(jsonObject1);

            District distObject;

            try {
                if (jsonObject1 != null && jsonObject1.getString("status_message") != null) {

                    String message = jsonObject1.getString("status_message");  // Message

                    Log.e("District MESSAGE", ">>>>>>>>>>>>>>>>" + message);

                    if (message.contains("Success")) {

                        JSONArray a = jsonObject1.getJSONArray("arr_district");
                        for (int i = 0; i < a.length(); i++) {

                            JSONObject jObj = a.getJSONObject(i);
                            String id = jObj.getString("id");
                            String dist_name = jObj.getString("name");

                            distObject = new District(id, dist_name);

                            pDist1.add(distObject);

                        }


                        pdistAdapter = new ArrayAdapter<District>(getApplicationContext(), R.layout.spinner_item, pDist1) {

                            private TextView text;

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {

                                errorText = (TextView) dist1.getSelectedView();

                                if (convertView == null) {
                                    convertView = mInflator.inflate(R.layout.spinner_item, null);
                                }

                                text = (TextView) convertView.findViewById(R.id.spinnerTarget);
                                if (!selected_pdist) {
                                    text.setText("Select District");
                                } else {
                                    text.setText(pDist1.get(position).getName());
                                    text.setTextColor(getResources().getColor(R.color.primary_text));
                                }
                                return convertView;
                            }
                        };

                        if(pDist1.isEmpty()){
                            Toast.makeText(getApplicationContext(), "No Districts Found",
                                    Toast.LENGTH_LONG).show();
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }else{
                            pdistAdapter.setDropDownViewResource(R.layout.spinner_item);
                            pdist1.setAdapter(pdistAdapter);
                            pdist1.setEnabled(true);

                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }


                        /* dist1.setAdapter(new ArrayAdapter<String>(Enrollment_Form.this, android.R.layout.simple_spinner_dropdown_item, Dist1));*/

                        Log.e("District list", "????????????" +  Dist1);


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

            return postJsonObjectDistPerm(get_district, makingJsonDistPerm());

        }
    }

    private JSONObject makingJsonDistPerm() {

        JSONObject postDataParams = new JSONObject();

        try {

            //following parameters to the API
            postDataParams.put("key", API_KEY);
            postDataParams.put("state_name", getpstate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDataParams;
    }



    public JSONObject postJsonObjectDistPerm(String url, JSONObject loginJobj) {
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
                result = convertIstostrDistPerm(inputStream);
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

    private String convertIstostrDistPerm(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}
