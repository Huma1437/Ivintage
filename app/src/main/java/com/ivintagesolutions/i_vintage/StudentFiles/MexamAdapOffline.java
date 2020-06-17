package com.ivintagesolutions.i_vintage.StudentFiles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ivintagesolutions.i_vintage.AnimationUtil;
import com.ivintagesolutions.i_vintage.Database.DBHelper;
import com.ivintagesolutions.i_vintage.ItemClickListener;
import com.ivintagesolutions.i_vintage.R;

import java.util.ArrayList;

public class MexamAdapOffline extends RecyclerView.Adapter<MexamAdapOffline.ViewHolder>  {

    ArrayList<MexampojoOffline> eIdOffline;
    private ItemClickListener clickListener;
    private Context mContext;
    int previousPosition = 0;
    String enrollment_status;
    DBHelper mydb;
    String get_stdID;
    // Pass in the country array into the constructor
    public MexamAdapOffline(ArrayList<MexampojoOffline> eIdOffline, Context context) {
        this.eIdOffline = eIdOffline;
        this.mContext = context;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public MexamAdapOffline.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_card, parent, false);
        mydb = new DBHelper(mContext);
        get_stdID = Splash_Screen.sh.getString("student_id", null);
        return new MexamAdapOffline.ViewHolder(view);
    }

    // Involves populating data into the item through holder
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MexamAdapOffline.ViewHolder holder, int position) {

        holder.exmID.setText(eIdOffline.get(position).getExam_name());


        if(eIdOffline.get(position).getExam_status().contains("Pending")){


            boolean recordExists= mydb.checkStdIDExistAnswer(mydb.Answer_Table ,mydb.ans_col_3 ,get_stdID);
            if(recordExists)
            {

                holder.taken.setVisibility(View.VISIBLE);
                holder.taken.setText("Exam Already Taken");
                holder.taken.setTextColor(R.color.green);
                holder.take_exam.setVisibility(View.GONE);

            }else{

                holder.taken.setVisibility(View.INVISIBLE);
                holder.take_exam.setVisibility(View.VISIBLE);

            }


        }else if(eIdOffline.get(position).getExam_status().contains("Viva")){

            holder.taken.setVisibility(View.VISIBLE);
            holder.taken.setText("Exam Already Taken");
            holder.taken.setTextColor(R.color.green);
            holder.take_exam.setVisibility(View.GONE);
        }else if(eIdOffline.get(position).getExam_status().contains("Completed")){

            holder.taken.setVisibility(View.VISIBLE);
            holder.taken.setText("Exam Already Taken");
            holder.taken.setTextColor(R.color.green);
            holder.take_exam.setVisibility(View.GONE);
        }


        //Getting the position of items in recyclerview
        if(position > previousPosition){ //we are scrolling DOWN

            AnimationUtil.animate(holder, true);

        }else{  //we are scrolling UP

            AnimationUtil.animate(holder, false);
        }

        previousPosition = position;
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return eIdOffline == null ? 0 : eIdOffline.size();
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    public MexampojoOffline mexampojoOffline(int position) {
        return eIdOffline.get(position);
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /* Your holder should contain a member variable
     for any view that will be set as you render a row */
        private TextView exmID,taken;
        Button take_exam;


        /*constructor that accepts the entire item row
             and does the view lookups to find each subview */
        public ViewHolder(View itemView) {

            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            exmID = (TextView) itemView.findViewById(R.id.exam_id);
            taken = (TextView) itemView.findViewById(R.id.taken);
            take_exam = (Button) itemView.findViewById(R.id.exam);
            itemView.setTag(itemView);

            take_exam.setOnClickListener(this);// bind the listener

        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
            // call the onClick in the OnItemClickListener

            enrollment_status = mexampojoOffline(getAdapterPosition()).getEnrollment_form_status();
            Splash_Screen.editor.putString("Exam_name", mexampojoOffline(getAdapterPosition()).exam_name);
            Splash_Screen.editor.commit();

            if(enrollment_status.contains("Pending")){

                Intent intent = new Intent(mContext, Enrollment_Form.class);
                mContext.startActivity(intent);
            }else if(enrollment_status.contains("Completed")){

                Intent intent = new Intent(mContext, OtpScreen.class);
                mContext.startActivity(intent);
            }
        }

    }
}

