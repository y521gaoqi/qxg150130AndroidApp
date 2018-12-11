/******************************************************************************
 * Breakout Program
 *
 * This program enable user to look the whole rank of scores. There are two
 * acitivity, one of them is list page, the another one is enty page,In addition,
 * user is able to input their own name, score, and date. Moreover, after they
 * enter their score an person info, the scores will be sorted automatically.
 *
 * If user do not input anything on entry screen, the page would not be back to
 * list page, and a pop up message will be pop out to remind user input info.
 *
 * There are three buttons on entry page, which are save button, clear button, and
 * back button. THe save button is to save information that user input. The back
 * button is to go back to previous page when user press it. The clear button is
 * to clear everything in the file and then go back to previous page, and the list
 * would be clear as well.
 *
 * Written by qxg150130 (qxg150130) at The University of Texas at Dallas
 * starting Nov. 24, 2018
 ******************************************************************************/



package com.example.ygaoq.qxg150130androidapp;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import ReadWrite;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * THis activity is friendly input interface. There are two EditTexts, one TextView,
 * three buttons.
 *
 * There are three buttons on entry page, which are save button, clear button, and
 * back button. THe save button is to save information that user input. The back
 * button is to go back to previous page when user press it. The clear button is
 * to clear everything in the file and then go back to previous page, and the list
 * would be clear as well.
 */
public class AddScore extends AppCompatActivity {

    private static final String TAG = "AddScore";

    private EditText name;          //instance of edittext
    private EditText score;         //instance of edittext
    private String date;            //string of date
    private TextView userDate;      //instance of edittext

    private DatePickerDialog.OnDateSetListener dateListener;

    private Button btn_save;        //instance of save button
    private Button btn_clear;       //instance of clear button
    private Button btn_back;        //instance of back edittext
    private ReadWrite rw;           //instance of I/O object
    @Override

    /**
     * This method is to initialize datePicker.
     * And to initializatize three buttons'
     * OnClick function
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);

        userDate=(TextView)findViewById(R.id.userDate);
        Calendar cal = Calendar.getInstance();
        int y=cal.get(Calendar.YEAR);
        int m=cal.get(Calendar.MONTH);
        int d=cal.get(Calendar.DAY_OF_MONTH);
        m=m+1;
        date=m+"/"+d+"/"+y;
        userDate.setText(date);

        userDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int y=cal.get(Calendar.YEAR);
                int m=cal.get(Calendar.MONTH);
                int d=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(AddScore.this, android.R.style.Theme_Holo_Dialog_MinWidth,dateListener,y,m,d);
                dialog.show();
            }
        });

        dateListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                date=month+"/"+dayOfMonth+"/"+year;
                userDate.setText(date);
            }
        };

        name=(EditText)findViewById(R.id.userName);
        score=(EditText)findViewById(R.id.userScore);
        score.setEnabled(false);

        Intent intent = this.getIntent();
        String score_game = intent.getStringExtra("score");
        score.setText(score_game);


        btn_clear=(Button)findViewById(R.id.bt_clear);

        btn_save=(Button)findViewById(R.id.btn_save);

//        if(name.getText().toString().isEmpty()&&score.getText().toString().isEmpty()){
////            Toast.makeText(AddScore.this, "Please Enter Your Name and Score", Toast.LENGTH_LONG).show();
////        }
////        else if(name.getText().toString().isEmpty()||score.getText().toString().isEmpty()){
////            if(name.getText().toString().isEmpty()){
////                Toast.makeText(AddScore.this, "Please Enter Your Name", Toast.LENGTH_LONG).show();
////            }else{
////                Toast.makeText(AddScore.this, "Please Enter Your Score", Toast.LENGTH_LONG).show();
////            }
////        }
////        else {
////            btn.setOnClickListener(new ReadWrite(getApplicationContext(),name,score,userDate));
////        }
        //Save button which will save everything into file and sort them same time.
        //btn_save.setOnClickListener(new ReadWrite(getApplicationContext(),name,score,userDate));
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()){
                    Toast.makeText(AddScore.this, "Please Enter Your Name", Toast.LENGTH_LONG).show();
                }
                else{
                    File logFile = new File(AddScore.this.getFilesDir(),"listScore.txt");
                    //logFile=sort(logFile);
                    ReadWrite write=new ReadWrite(name,score,userDate);
                    write.sort(logFile);
                    Intent intent = new Intent(AddScore.this, ScoreList.class);
                    startActivity(intent);
                }
            }
        });

        /*
        This is clear button. When user press this button, they will be redirected to
        listView page, and everything in the file will be cleared as well.
         */
        final File logFile = new File(this.getFilesDir(),"listScore.txt");
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadWrite c=new ReadWrite(logFile);
                c.clearFile();
                Intent intent = new Intent(AddScore.this, ScoreList.class);
                startActivity(intent);
            }
        });


        /*
        back button OnClick funciton. WHen user press this button,
        they are redireced to previous page, which is listView Page.
         */
        btn_back=(Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddScore.this, ScoreList.class);
                startActivity(intent);
            }
        });

    }
}
