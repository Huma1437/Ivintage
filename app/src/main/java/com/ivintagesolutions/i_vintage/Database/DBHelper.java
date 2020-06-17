package com.ivintagesolutions.i_vintage.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Queue;

public class DBHelper extends SQLiteOpenHelper {

    //Questions table with its column [Online working]
    public static final String DATABASE_NAME = "I-Vintage";
    public static final String TABLE_NAME = "StudentData";
    public static final String COLUMN_1 = "q_id";
    public static final String COLUMN_2 = "selected_option";
    public static final String COLUMN_3 = "mf_review";
    public static final String COLUMN_4 = "not_ans";
    public static final String COLUMN_5 = "not_visited";
    public static final String COLUMN_6 = "Question";
    public static final String COLUMN_7 = "optionA";
    public static final String COLUMN_8 = "optionB";
    public static final String COLUMN_9 = "optionC";
    public static final String COLUMN_10 = "optionD";
    public static final String COLUMN_11 = "qnum";
    public static final String COLUMN_12 = "hindi_Question";
    public static final String COLUMN_13 = "hindi_optionA";
    public static final String COLUMN_14 = "hindi_optionB";
    public static final String COLUMN_15 = "hindi_optionC";
    public static final String COLUMN_16 = "hindi_optionD";

    //Student details table with its columns [offline working,Download information]
    public static final String STD_DETAILS_TABLE = "Student_Details";
    public static final String s_col_1 = "std_id";
    public static final String s_col_2 = "sdms_num";
    public static final String s_col_3 = "first_name";
    public static final String s_col_4 = "last_name";
    public static final String s_col_5 = "password";
    public static final String s_col_6 = "student_mobile";
    public static final String s_col_7 = "student_email";
    public static final String s_col_8 = "aadhaar_number";
    public static final String s_col_9 = "date_of_birth";
    public static final String s_col_10 = "address";
    public static final String s_col_11 = "city";
    public static final String s_col_12 = "state";
    public static final String s_col_13 = "district";
    public static final String s_col_14 = "pincode";
    public static final String s_col_15 = "tb_nsdc_id";
    public static final String s_col_16 = "tb_name";
    public static final String s_col_17 = "trade_title";
    public static final String s_col_18 = "exam_status";
    public static final String s_col_19 = "enrollment_status";
    public static final String s_col_20 = "exam_otp";
    public static final String s_col_21 = "exam_duration";
    public static final String s_col_22 = "image_dir_source";
    public static final String s_col_23 = "image_dir";
    public static final String s_col_24 = "exam_name";
    public static final String s_col_25 = "trade_code";
    public static final String s_col_26 = "total_ques";
    public static final String s_col_27 = "guardian_name";
    public static final String s_col_28 = "exam_id";
    public static final String s_col_29 = "ssc_id";
    public static final String s_col_30 = "trade_id";
    public static final String s_col_31 = "tb_id";
    public static final String s_col_32 = "take_snaps";

    //Questions table with its column [Offline working, Download info]
    public static final String QUESTIONS_TABLE = "Questions";
    public static final String q_col_1 = "std_id";
    public static final String q_col_2 = "nos_id";
    public static final String q_col_3 = "nos_code";
    public static final String q_col_4 = "nos_title";
    public static final String q_col_5 = "q_id";
    public static final String q_col_6 = "question";
    public static final String q_col_7 = "op1";
    public static final String q_col_8 = "op2";
    public static final String q_col_9 = "op3";
    public static final String q_col_10 = "op4";
    public static final String q_col_11 = "h_ques";
    public static final String q_col_12 = "h_op1";
    public static final String q_col_13 = "h_op2";
    public static final String q_col_14 = "h_op3";
    public static final String q_col_15 = "h_op4";
    public static final String q_col_16 = "selected_opt";
    public static final String q_col_17 = "review";
    public static final String q_col_18 = "na";
    public static final String q_col_19 = "nv";
    public static final String q_col_20 = "Qnum";

    //Enrollment table with its columns
    public static final String TABLE_NAME_ENROLMENT = "Enrollment_Data";
    public static final String E_col_1 = "std_id";
    public static final String E_col_2 = "first_name";
    public static final String E_col_3 = "last_name";
    public static final String E_col_4 = "gender";
    public static final String E_col_5 = "date_of_birth";
    public static final String E_col_6 = "marital_status";
    public static final String E_col_7 = "age";
    public static final String E_col_8 = "student_mobile";
    public static final String E_col_9 = "student_landline_no";
    public static final String E_col_10 = "student_email";
    public static final String E_col_11 = "aadhaar_number";
    public static final String E_col_12 = "aadhaar_photo";
    public static final String E_col_13 = "aadhaar_photo_name";
    public static final String E_col_14 = "curr_door_no";
    public static final String E_col_15 = "curr_street_name";
    public static final String E_col_16 = "curr_village_city";
    public static final String E_col_17 = "curr_district";
    public static final String E_col_18 = "curr_state";
    public static final String E_col_19 = "curr_pincode";
    public static final String E_col_20 = "perm_door_no";
    public static final String E_col_21 = "perm_street_name";
    public static final String E_col_22 = "perm_village_city";
    public static final String E_col_23 = "perm_district";
    public static final String E_col_24 = "perm_state";
    public static final String E_col_25 = "perm_pincode";
    public static final String E_col_26 = "father_name";
    public static final String E_col_27 = "mother_name";
    public static final String E_col_28 = "father_occupation";
    public static final String E_col_29 = "mother_occupation";
    public static final String E_col_30 = "father_cont_no";
    public static final String E_col_31 = "family_income";
    public static final String E_col_32 = "religion";
    public static final String E_col_33 = "guardian_name";
    public static final String E_col_34 = "relationship";
    public static final String E_col_35 = "guardian_occupation";
    public static final String E_col_36 = "guardian_contact_no";
    public static final String E_col_37 = "guardian_category";
    public static final String E_col_38 = "guardian_caste";
    public static final String E_col_39 = "educational_qualification";
    public static final String E_col_40 = "exp_yrs";
    public static final String E_col_41 = "passed_year";
    public static final String E_col_42 = "current_employer";
    public static final String E_col_43 = "institution";
    public static final String E_col_44 = "employer_address";
    public static final String E_col_45 = "marks_obtained";
    public static final String E_col_46 = "bank_account_no";
    public static final String E_col_47 = "bank_name";
    public static final String E_col_48 = "bank_branch";
    public static final String E_col_49 = "ifsc_code";
    public static final String E_col_50 = "api_key";

    //Otp Screen table with its column
    public static final String OTP_TABLE = "OTP";
    public static final String otp_col_1 = "exam_id";
    public static final String otp_col_2 = "std_id";
    public static final String otp_col_3 = "student_image";
    public static final String otp_col_4 = "student_image_name";
    public static final String otp_col_5 = "ssc_id";
    public static final String otp_col_6 = "trade_id";
    public static final String otp_col_7 = "tb_id";
    public static final String otp_col_8 = "api_key";
    public static final String otp_col_9 = "assessor_image";
    public static final String otp_col_10 = "assessor_image_name";

    public static final String Answer_Table = "Answer_Submission";
    public static final String ans_col_1 = "api_key";
    public static final String ans_col_2 = "exam_id";
    public static final String ans_col_3 = "std_id";
    public static final String ans_col_4 = "std_name";
    public static final String ans_col_5 = "examdate";
    public static final String ans_col_6 = "ssc_id";
    public static final String ans_col_7 = "trade_id";
    public static final String ans_col_8 = "tb_id";
    public static final String ans_col_9 = "starttime";
    public static final String ans_col_10 = "endtime";
    public static final String ans_col_11 = "IP_address";
    public static final String ans_col_12 = "browser";
    public static final String ans_col_13 = "question_ids";
    public static final String ans_col_14 = "snapshot_image_name";
    public static final String ans_col_15 = "snapshot_image_date";
    public static final String ans_col_16 = "selected_ans";
    public static final String ans_col_17 = "encrypted_image";
    public static final String ans_col_18 = "Qid";
    public static final String ans_col_19 = "snaps";



    private Context mContext;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 23);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creating Question with Options table [Online Working]
        String createTable = "CREATE TABLE "+ TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
               +COLUMN_1 +" TEXT , " +COLUMN_2 +" TEXT , " +COLUMN_3 + " INTEGER , " +COLUMN_4 + " INTEGER , " +COLUMN_5 + " INTEGER , "
        +COLUMN_6 + " TEXT , " +COLUMN_7 +" TEXT , " +COLUMN_8 + " TEXT , " +COLUMN_9 + " TEXT , "
        +COLUMN_10 + " TEXT , " +COLUMN_11  + " INTEGER , "+COLUMN_12 + " TEXT , " +COLUMN_13 +" TEXT , " +COLUMN_14 + " TEXT , " +COLUMN_15 + " TEXT , "
                +COLUMN_16 + " TEXT  " + ");";
        db.execSQL(createTable);

        // Creating Student details table for Offline working
        String createTable2 = "CREATE TABLE "+ STD_DETAILS_TABLE +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                +s_col_1 +" TEXT , " +s_col_2 +" TEXT , " +s_col_3 + " TEXT , " +s_col_4 + " TEXT , " +s_col_5 + " TEXT , "
                +s_col_6 + " TEXT , " +s_col_7 +" TEXT , " +s_col_8 + " TEXT , " +s_col_9 + " TEXT , " +s_col_10 + " TEXT , "
                +s_col_11  + " TEXT , "+s_col_12 + " TEXT , " +s_col_13 + " TEXT , " +s_col_14 + " TEXT , " +s_col_15 + " TEXT , "
                +s_col_16 + " TEXT  ," +s_col_17 + " TEXT , " +s_col_18 + " TEXT , " +s_col_19 + " TEXT , " +s_col_20 + " TEXT , "
                +s_col_21 + " TEXT , " +s_col_22 + " TEXT , " +s_col_23 + " TEXT , " +s_col_24 + " TEXT , " +s_col_25 + " TEXT , "
                +s_col_26  + " TEXT,  " +s_col_27 + " TEXT , " +s_col_28 + " TEXT , " +s_col_29 + " TEXT , " +s_col_30 + " TEXT , "
                +s_col_31  + " TEXT,  " +s_col_32 + " TEXT  " + ");";
        db.execSQL(createTable2);

        //Creating Questions table with Options table[offline working]
        String createQTable = "CREATE TABLE "+ QUESTIONS_TABLE +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                +q_col_1 +" TEXT , " +q_col_2 +" TEXT , " +q_col_3 + " TEXT , " +q_col_4 + " TEXT , " +q_col_5 + " TEXT , "
                +q_col_6 + " TEXT , " +q_col_7 +" TEXT, " +q_col_8 +" TEXT , " +q_col_9 +" TEXT , " +q_col_10 + " TEXT , "
                +q_col_11 + " TEXT , " +q_col_12 + " TEXT , " +q_col_13 + " TEXT , " +q_col_14 + " TEXT, " + q_col_15 + " TEXT, "
                +q_col_16 + " TEXT , " +q_col_17 + " INTEGER , " +q_col_18 + " INTEGER , "
                +q_col_19  + " INTEGER,  " +q_col_20 + " INTEGER  " + ");";
        db.execSQL(createQTable);

        // Creating enrollment table
        String createEnrolTable = "CREATE TABLE "+ TABLE_NAME_ENROLMENT +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                +E_col_1 +" TEXT , " +E_col_2 +" TEXT , " +E_col_3 + " TEXT , " +E_col_4 + " TEXT , " +E_col_5 + " TEXT , "
                +E_col_6 + " TEXT , " +E_col_7 +" TEXT , " +E_col_8 + " TEXT , " +E_col_9 + " TEXT , " +E_col_10 + " TEXT , "
                +E_col_11  + " TEXT , "+E_col_12 + " TEXT , " +E_col_13 + " TEXT , " +E_col_14 + " TEXT , " +E_col_15 + " TEXT , "
                +E_col_16 + " TEXT  ," +E_col_17 + " TEXT , " +E_col_18 + " TEXT , " +E_col_19 + " TEXT , " +E_col_20 + " TEXT , "
                +E_col_21 + " TEXT , " +E_col_22 + " TEXT , " +E_col_23 + " TEXT , " +E_col_24 + " TEXT , " +E_col_25 + " TEXT , "
                +E_col_26  + " TEXT , "+E_col_27 + " TEXT , " +E_col_28 + " TEXT , " +E_col_29 + " TEXT , " +E_col_30 + " TEXT , "
                +E_col_31 + " TEXT , "+ E_col_32 + " TEXT , " +E_col_33 + " TEXT , " +E_col_34 + " TEXT , " +E_col_35 + " TEXT , "
                +E_col_36 + " TEXT , " +E_col_37 + " TEXT , " +E_col_38 + " TEXT , " +E_col_39 + " TEXT , " +E_col_40 + " TEXT , "
                +E_col_41 + " TEXT , " +E_col_42  + " TEXT ," +E_col_43 + " TEXT , " +E_col_44 +" TEXT , " +E_col_45 + " TEXT , "
                +E_col_46 + " TEXT , " +E_col_47 + " TEXT , " +E_col_48 + " TEXT , " +E_col_49 +" TEXT , " +E_col_50 +" TEXT  " + ");";
        db.execSQL(createEnrolTable);

        //Creating Question with Options table
        String createOTPTable = "CREATE TABLE "+ OTP_TABLE +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                +otp_col_1 +" TEXT , " +otp_col_2 +" TEXT , " +otp_col_3 + " TEXT , " +otp_col_4 + " TEXT , " +otp_col_5 + " TEXT , "
                +otp_col_6 + " TEXT , " +otp_col_7 +" TEXT , " +otp_col_8 + " TEXT , " +otp_col_9 + " TEXT , "+otp_col_10 + " TEXT  " + ");";
        db.execSQL(createOTPTable);

        //Creating Question with Options table [Online Working]
        String createAnswerTable = "CREATE TABLE "+ Answer_Table +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                +ans_col_1 +" TEXT , " +ans_col_2 +" TEXT , " +ans_col_3 + " TEXT , " +ans_col_4 + " TEXT , " +ans_col_5 + " TEXT , "
                +ans_col_6 + " TEXT , " +ans_col_7 +" TEXT , " +ans_col_8 + " TEXT , " +ans_col_9 + " TEXT , "
                +ans_col_10 + " TEXT , " +ans_col_11  + " TEXT , "+ans_col_12 + " TEXT , " +ans_col_13 +" TEXT , "
                +ans_col_14 + " TEXT , " +ans_col_15 + " TEXT , "
                +ans_col_16 + " TEXT , "  +ans_col_17 + " TEXT , " +ans_col_18 + " TEXT  ," +ans_col_19 + " TEXT  " + ");";
        db.execSQL(createAnswerTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        db.execSQL("DROP TABLE IF EXISTS " + STD_DETAILS_TABLE );
        db.execSQL("DROP TABLE IF EXISTS " + QUESTIONS_TABLE );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ENROLMENT );
        db.execSQL("DROP TABLE IF EXISTS " + OTP_TABLE );
        db.execSQL("DROP TABLE IF EXISTS " + Answer_Table );
        onCreate(db);
    }

    //Adding data to the Questions table
    public boolean addData(String item1, String item2, Integer item3, Integer item4, Integer item5,String item6,String item7,String item8,String item9, String item10, Integer item11,String item12,String item13,String item14,String item15, String item16){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_1, item1);
        contentValues.put(COLUMN_2, item2);
        contentValues.put(COLUMN_3, item3);
        contentValues.put(COLUMN_4, item4);
        contentValues.put(COLUMN_5, item5);
        contentValues.put(COLUMN_6, item6);
        contentValues.put(COLUMN_7, item7);
        contentValues.put(COLUMN_8, item8);
        contentValues.put(COLUMN_9, item9);
        contentValues.put(COLUMN_10, item10);
        contentValues.put(COLUMN_11, item11);
        contentValues.put(COLUMN_12, item12);
        contentValues.put(COLUMN_13, item13);
        contentValues.put(COLUMN_14, item14);
        contentValues.put(COLUMN_15, item15);
        contentValues.put(COLUMN_16, item16);

        long result = db.insert(TABLE_NAME, null,contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

//Adding data to the student details table
    public boolean student_details(String item1, String item2, String item3, String item4, String item5,
                                     String item6,String item7,String item8,String item9, String item10,
                                     String item11,String item12,String item13,String item14,String item15,
                                     String item16, String item17,String item18, String item19, String item20,
                                     String item21, String item22,String item23,String item24,String item25,
                                     String item26,String item27,String item28,String item29,String item30,
                                     String item31,String item32){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(s_col_1, item1);
        contentValues.put(s_col_2, item2);
        contentValues.put(s_col_3, item3);
        contentValues.put(s_col_4, item4);
        contentValues.put(s_col_5, item5);
        contentValues.put(s_col_6, item6);
        contentValues.put(s_col_7, item7);
        contentValues.put(s_col_8, item8);
        contentValues.put(s_col_9, item9);
        contentValues.put(s_col_10, item10);
        contentValues.put(s_col_11, item11);
        contentValues.put(s_col_12, item12);
        contentValues.put(s_col_13, item13);
        contentValues.put(s_col_14, item14);
        contentValues.put(s_col_15, item15);
        contentValues.put(s_col_16, item16);
        contentValues.put(s_col_17, item17);
        contentValues.put(s_col_18, item18);
        contentValues.put(s_col_19, item19);
        contentValues.put(s_col_20, item20);
        contentValues.put(s_col_21, item21);
        contentValues.put(s_col_22, item22);
        contentValues.put(s_col_23, item23);
        contentValues.put(s_col_24, item24);
        contentValues.put(s_col_25, item25);
        contentValues.put(s_col_26, item26);
        contentValues.put(s_col_27, item27);
        contentValues.put(s_col_28, item28);
        contentValues.put(s_col_29, item29);
        contentValues.put(s_col_30, item30);
        contentValues.put(s_col_31, item31);
        contentValues.put(s_col_32, item32);

        long result = db.insert(STD_DETAILS_TABLE, null,contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //Adding data to the Questions Table [Offline working,download info]
    public boolean add_ques(String item1, String item2, String item3, String item4, String item5,
                            String item6,String item7,String item8,String item9, String item10,
                            String item11,String item12,String item13,String item14,String item15,String item16,
                            Integer item17,Integer item18,Integer item19,Integer item20){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(q_col_1, item1);
        contentValues.put(q_col_2, item2);
        contentValues.put(q_col_3, item3);
        contentValues.put(q_col_4, item4);
        contentValues.put(q_col_5, item5);
        contentValues.put(q_col_6, item6);
        contentValues.put(q_col_7, item7);
        contentValues.put(q_col_8, item8);
        contentValues.put(q_col_9, item9);
        contentValues.put(q_col_10, item10);
        contentValues.put(q_col_11, item11);
        contentValues.put(q_col_12, item12);
        contentValues.put(q_col_13, item13);
        contentValues.put(q_col_14, item14);
        contentValues.put(q_col_15, item15);
        contentValues.put(q_col_16, item16);
        contentValues.put(q_col_17, item17);
        contentValues.put(q_col_18, item18);
        contentValues.put(q_col_19, item19);
        contentValues.put(q_col_20, item20);

        long result = db.insert(QUESTIONS_TABLE, null,contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //Adding data to the enrollment table
    public boolean addEnrollmentData(String item1, String item2, String item3, String item4, String item5,
                                     String item6,String item7,String item8,String item9, String item10,
                                     String item11,String item12,String item13,String item14,String item15,
                                     String item16, String item17,String item18, String item19, String item20,
                                     String item21, String item22,String item23,String item24,String item25,
                                     String item26, String item27, String item28,String item29,String item30,
                                     String item31,String item32, String item33, String item34,String item35,
                                     String item36, String item37, String item38, String item39,String item40,
                                     String item41,String item42,String item43, String item44, String item45,
                                     String item46,String item47,String item48,String item49,String item50){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(E_col_1, item1);
        contentValues.put(E_col_2, item2);
        contentValues.put(E_col_3, item3);
        contentValues.put(E_col_4, item4);
        contentValues.put(E_col_5, item5);
        contentValues.put(E_col_6, item6);
        contentValues.put(E_col_7, item7);
        contentValues.put(E_col_8, item8);
        contentValues.put(E_col_9, item9);
        contentValues.put(E_col_10, item10);
        contentValues.put(E_col_11, item11);
        contentValues.put(E_col_12, item12);
        contentValues.put(E_col_13, item13);
        contentValues.put(E_col_14, item14);
        contentValues.put(E_col_15, item15);
        contentValues.put(E_col_16, item16);
        contentValues.put(E_col_17, item17);
        contentValues.put(E_col_18, item18);
        contentValues.put(E_col_19, item19);
        contentValues.put(E_col_20, item20);
        contentValues.put(E_col_21, item21);
        contentValues.put(E_col_22, item22);
        contentValues.put(E_col_23, item23);
        contentValues.put(E_col_24, item24);
        contentValues.put(E_col_25, item25);
        contentValues.put(E_col_26, item26);
        contentValues.put(E_col_27, item27);
        contentValues.put(E_col_28, item28);
        contentValues.put(E_col_29, item29);
        contentValues.put(E_col_30, item30);
        contentValues.put(E_col_31, item31);
        contentValues.put(E_col_32, item32);
        contentValues.put(E_col_33, item33);
        contentValues.put(E_col_34, item34);
        contentValues.put(E_col_35, item35);
        contentValues.put(E_col_36, item36);
        contentValues.put(E_col_37, item37);
        contentValues.put(E_col_38, item38);
        contentValues.put(E_col_39, item39);
        contentValues.put(E_col_40, item40);
        contentValues.put(E_col_41, item41);
        contentValues.put(E_col_42, item42);
        contentValues.put(E_col_43, item43);
        contentValues.put(E_col_44, item44);
        contentValues.put(E_col_45, item45);
        contentValues.put(E_col_46, item46);
        contentValues.put(E_col_47, item47);
        contentValues.put(E_col_48, item48);
        contentValues.put(E_col_49, item49);
        contentValues.put(E_col_50, item50);

        long result = db.insert(TABLE_NAME_ENROLMENT, null,contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //Adding data to the OTP table
    public boolean adddata_OTP(String item1, String item2, String item3, String item4, String item5,
                               String item6,String item7,String item8, String item9, String item10){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(otp_col_1, item1);
        contentValues.put(otp_col_2, item2);
        contentValues.put(otp_col_3, item3);
        contentValues.put(otp_col_4, item4);
        contentValues.put(otp_col_5, item5);
        contentValues.put(otp_col_6, item6);
        contentValues.put(otp_col_7, item7);
        contentValues.put(otp_col_8, item8);
        contentValues.put(otp_col_9, item9);
        contentValues.put(otp_col_10, item10);

        long result = db.insert(OTP_TABLE, null,contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //Adding data to the Answer table
    public boolean addAnswers(String item1, String item2, String item3, String item4, String item5,
                              String item6,String item7,String item8,String item9, String item10,
                              String item11,String item12,String item13,String item14,String item15,
                              String item16,String item17,String item18,String item19){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ans_col_1, item1);
        contentValues.put(ans_col_2, item2);
        contentValues.put(ans_col_3, item3);
        contentValues.put(ans_col_4, item4);
        contentValues.put(ans_col_5, item5);
        contentValues.put(ans_col_6, item6);
        contentValues.put(ans_col_7, item7);
        contentValues.put(ans_col_8, item8);
        contentValues.put(ans_col_9, item9);
        contentValues.put(ans_col_10, item10);
        contentValues.put(ans_col_11, item11);
        contentValues.put(ans_col_12, item12);
        contentValues.put(ans_col_13, item13);
        contentValues.put(ans_col_14, item14);
        contentValues.put(ans_col_15, item15);
        contentValues.put(ans_col_16, item16);
        contentValues.put(ans_col_17, item17);
        contentValues.put(ans_col_18, item18);
        contentValues.put(ans_col_19, item19);

        long result = db.insert(Answer_Table, null,contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }



//Delete row for list of question with options and other details [Online Ques Table]
    public void deleteRecord() {

        SQLiteDatabase db = this.getWritableDatabase();

//    Deleting all records from database table
        db.delete(TABLE_NAME, null, null);
        db.execSQL("delete from "+ TABLE_NAME);
    }

//Deleting record from the student details table
    public void deleteRecordStdDetails() {

        SQLiteDatabase db = this.getWritableDatabase();

//    Deleting all records from student details table
        db.delete(STD_DETAILS_TABLE, null, null);
        db.execSQL("delete from "+ STD_DETAILS_TABLE);
    }

    //Delete record from questions table [Offline Ques Table]
    public void deleteRecordQues() {

        SQLiteDatabase db = this.getWritableDatabase();

//    Deleting all records from database table
        db.delete(QUESTIONS_TABLE, null, null);
        db.execSQL("delete from "+ QUESTIONS_TABLE);
    }

    //Deleting record from the enrollment table
    public void deleteRecordEnrollment() {

        SQLiteDatabase db = this.getWritableDatabase();

//    Deleting all records from database table
        db.delete(TABLE_NAME_ENROLMENT, null, null);
        db.execSQL("delete from "+ TABLE_NAME_ENROLMENT);
    }

    //Delete rows from otp table
    public void deleteRecordfromOTP() {

        SQLiteDatabase db = this.getWritableDatabase();

//    Deleting all records from database table
        db.delete(OTP_TABLE, null, null);
        db.execSQL("delete from "+ OTP_TABLE);
    }

    //Delete rows from answer table
    public void delRecfromAnswerTable() {

        SQLiteDatabase db = this.getWritableDatabase();

//    Deleting all records from database table
        db.delete(Answer_Table, null, null);
        db.execSQL("delete from "+ Answer_Table);
    }

    //Deleting only single row by its value
    public void deleteSingleEnrollData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_ENROLMENT + " WHERE " + E_col_1 + "= '" + item + "'");
        db.close();
    }


    //Deleting only single row by its value
    public void deleteSingleOTPData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + OTP_TABLE + " WHERE " + otp_col_2 + "= '" + item + "'");
        db.close();
    }

    //Deleting only single row by its value
    public void deleteSingleAnswerData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Answer_Table + " WHERE " + ans_col_3 + "= '" + item + "'");
        db.close();
    }

    //Get all the values of particular row(enrollment table)
    public Cursor getEnrollSingleVal(DBHelper dbHelper,String item1){

        if(dbHelper != null){

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //Select all Query
            Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME_ENROLMENT+" WHERE "+E_col_1+"='"+item1+"'", null);
            return  data;

        }else{
            return null;
        }

    }

    //Get all the values of particular row(Otp table)
    public Cursor getOTPSingleVal(DBHelper dbHelper,String item1){

        if(dbHelper != null){

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //Select all Query
            Cursor data = db.rawQuery("SELECT * FROM " + OTP_TABLE+" WHERE "+otp_col_2+"='"+item1+"'", null);
            return  data;

        }else{
            return null;
        }

    }

    //Get all the values of particular row(Otp table)
    public Cursor getAnswerSingleVal(DBHelper dbHelper,String item1){

        if(dbHelper != null){

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //Select all Query
            Cursor data = db.rawQuery("SELECT * FROM " + Answer_Table+" WHERE "+ans_col_3+"='"+item1+"'", null);
            return  data;

        }else{
            return null;
        }

    }


//Get all the details from Questions Table[Questions Table,Online]
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        //Select all Query
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return  data;
    }

    //Get all the details from [Student Details table]
    public Cursor getStudentData(){
        SQLiteDatabase db = this.getWritableDatabase();
        //Select all Query
        Cursor std_table = db.rawQuery("SELECT * FROM " +STD_DETAILS_TABLE, null);
        return  std_table;
    }

    //Get all the details from the table[Offline Questions with Options Table]
    public Cursor getQuestionsData(){
        SQLiteDatabase db = this.getWritableDatabase();
        //Select all Query
        Cursor qnstable = db.rawQuery("SELECT * FROM " + QUESTIONS_TABLE, null);
        return  qnstable;
    }

    //Get all the details from the Enrollment Table
    public Cursor getEnrollmentData(DBHelper mDB){
        Log.e("HUma", "getEnrollmentData: 1" );
        if (mDB != null) {
            Log.e("HUma", "getEnrollmentData: hai" );

// Code goes here.
        SQLiteDatabase db = mDB.getWritableDatabase();
        //Select all Query
        Cursor enrollmentdata = db.rawQuery("SELECT * FROM " +TABLE_NAME_ENROLMENT, null);
        return  enrollmentdata;
        } else {
            Log.e("HUma", "getEnrollmentData: null" );

            return null;
        }
    }

    //Get all the details from the OTP Table
    public Cursor getOTPData(DBHelper dbHelper){
        if(dbHelper != null){

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //Select all Query
            Cursor OTPdata = db.rawQuery("SELECT * FROM " + OTP_TABLE, null);
            return  OTPdata;

        }else{
            return null;
        }

    }

    //Get all the details from the answer table
    public Cursor getAnswerData(DBHelper dbHelper){

        if(dbHelper != null){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //Select all Query
            Cursor ansData = db.rawQuery("SELECT * FROM " + Answer_Table, null);
            return  ansData;

        }else {

            return null;
        }
    }


    public Cursor getSingleContents(String item2){
        SQLiteDatabase db = this.getWritableDatabase();
        //Select all Query
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE "+COLUMN_11+"='"+item2+"'", null);
        return  data;
    }

    //For counting the number of rows(questions) which is not visited my the student
    public int notVisitedCount() {
        String countQuery = "SELECT  * FROM StudentData WHERE not_visited='0'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //For counting the number of rows(questions) which is not answered by the student
    public int notAnsweredCount() {
        String countQuery = "SELECT  * FROM StudentData WHERE not_ans='0'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //For counting the number of rows(questions) which is answered by the student
    public int answeredCount() {
        String countQuery = "SELECT  * FROM StudentData WHERE not_ans='1'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //For counting the number of rows(questions) which is marked as review by the student
    public int reviewCount() {
        String countQuery = "SELECT  * FROM StudentData WHERE mf_review='1'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    //For checking the value question number exists in table
    public boolean checkIfRecordExist(String TABLE_NAME,String COLUMN_1,String item2)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor=db.rawQuery("SELECT "+COLUMN_1+" FROM "+TABLE_NAME+" WHERE "+COLUMN_1+"='"+item2+"'",null);
            if (cursor.moveToFirst())
            {
                db.close();
                Log.d("Record  Already Exists", "Table is:"+TABLE_NAME+" ColumnName:"+COLUMN_1);
                return true;//record Exists

            }
            Log.d("New Record  ", "Table is:"+TABLE_NAME+" ColumnName:"+COLUMN_1+" Column Value:"+item2);
            db.close();
        }
        catch(Exception errorException)
        {

        }
        return false;
    }

//check username and pwd exists in DB or not
    public boolean checkExist(String uname, String pwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+STD_DETAILS_TABLE+" WHERE "+s_col_2+"=?" +" AND " + s_col_5+"=?", new String[] {uname,pwd});
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }

    //get complete row data based on sdms number
    public Cursor getSingleRowofStudent(String item2){
        SQLiteDatabase db = this.getWritableDatabase();
        //Select all Query
        Cursor data = db.rawQuery("SELECT * FROM " + STD_DETAILS_TABLE+" WHERE "+s_col_2+"='"+item2+"'", null);
        return  data;
    }


    //check stdid exists in db or not
    public boolean checkStdIDExist(String std_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+STD_DETAILS_TABLE+" WHERE "+s_col_1+"=?" , new String[] {std_id});
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }

    //check tb_id exists in db or not
    public boolean checkTBIDExist(String tb_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+STD_DETAILS_TABLE+" WHERE "+s_col_31+"=?" , new String[] {tb_id});
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }

    //get complete row based on student id
    public Cursor getrowByStdID(String item1){
        SQLiteDatabase db = this.getWritableDatabase();
        //Select all Query
        Cursor data = db.rawQuery("SELECT * FROM " + STD_DETAILS_TABLE+" WHERE "+s_col_1+"='"+item1+"'", null);
        return  data;
    }


    //check stdid exists in db or not
    public boolean checkStdIDExistinQuesTB(String std_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+QUESTIONS_TABLE+" WHERE "+q_col_1+"=?" , new String[] {std_id});
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }



    public Cursor getSingleStudentQns(String item2){
        SQLiteDatabase db = this.getWritableDatabase();
        //Select all Query
        Cursor data = db.rawQuery("SELECT * FROM " + QUESTIONS_TABLE +" WHERE "+q_col_1+"='"+item2+"'", null);
        return  data;
    }

    //Getting question by using student id and Qnum
    public Cursor getQnsbyStdQnum(String std, String qnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            data = db.rawQuery("SELECT * FROM "+ QUESTIONS_TABLE+" WHERE "+q_col_1+"='"+std+"'" +" AND " +q_col_20+"='"+qnum+"'", null, null);
        }
        return data;
    }

    //Getting selected option of student by using its student id
    public Cursor getOptbyStdID(String std) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            data = db.rawQuery("SELECT * FROM "+ QUESTIONS_TABLE+" WHERE "+q_col_1+"='"+std+"'", null);
        }
        return data;
    }

    //For counting the number of rows(questions) which is not visited my the student
    public int notVisitedCountOffline(String std) {
        String countQuery = "SELECT  * FROM Questions WHERE nv='0' AND std_id = '"+std+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //For counting the number of rows(questions) which is not answered by the student
    public int notAnsweredCountOffline(String std) {
        String countQuery = "SELECT  * FROM Questions WHERE na='0' AND std_id = '"+std+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //For counting the number of rows(questions) which is answered by the student
    public int answeredCountOffline(String std) {
        String countQuery = "SELECT  * FROM Questions WHERE na='1' AND std_id = '"+std+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //For counting the number of rows(questions) which is marked as review by the student
    public int reviewCountOffline(String std) {
        String countQuery = "SELECT  * FROM Questions WHERE review='1' AND std_id = '"+std+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //For checking the value question number exists in table
    public boolean checkIfRecordExistQns(String QUESTIONS_TABLE,String q_col_5,String item2)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor=db.rawQuery("SELECT "+q_col_5+" FROM "+QUESTIONS_TABLE+" WHERE "+q_col_5+"='"+item2+"'",null);
            if (cursor.moveToFirst())
            {
                db.close();
                Log.d("Record  Already Exists", "Table is:"+QUESTIONS_TABLE+" ColumnName:"+q_col_5);
                return true;//record Exists

            }
            Log.d("New Record  ", "Table is:"+QUESTIONS_TABLE+" ColumnName:"+q_col_5+" Column Value:"+item2);
            db.close();
        }
        catch(Exception errorException)
        {

        }
        return false;
    }

    //For checking the student id exists in enrollment table
    public boolean checkStdIDExistEnrol(String TABLE_NAME_ENROLMENT,String E_col_1,String item1)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor=db.rawQuery("SELECT "+E_col_1+" FROM "+TABLE_NAME_ENROLMENT+" WHERE "+E_col_1+"='"+item1+"'",null);
            if (cursor.moveToFirst())
            {
                db.close();
                Log.d("Record  Already Exists", "Table is:"+TABLE_NAME_ENROLMENT+" ColumnName:"+E_col_1);
                return true;//record Exists

            }
            Log.d("New Record  ", "Table is:"+TABLE_NAME_ENROLMENT+" ColumnName:"+E_col_1+" Column Value:"+item1);
            db.close();
        }
        catch(Exception errorException)
        {

        }
        return false;
    }

    //For checking the student id exists in answers table
    public boolean checkStdIDExistAnswer(String Answer_Table,String ans_col_3,String item1)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor=db.rawQuery("SELECT "+ans_col_3+" FROM "+Answer_Table+" WHERE "+ans_col_3+"='"+item1+"'",null);
            if (cursor.moveToFirst())
            {
                db.close();
                Log.d("Record  Already Exists", "Table is:"+Answer_Table+" ColumnName:"+ans_col_3);
                return true;//record Exists

            }
            Log.d("New Record  ", "Table is:"+Answer_Table+" ColumnName:"+ans_col_3+" Column Value:"+item1);
            db.close();
        }
        catch(Exception errorException)
        {

        }
        return false;
    }

}
