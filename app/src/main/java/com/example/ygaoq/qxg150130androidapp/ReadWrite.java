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

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/*
THis is a important class, which is to read, write, clear file and
sort all records then write them to the file.
 */
//public class ReadWrite implements View.OnClickListener{
public class ReadWrite{
    private Context Activity;   //COntext
    private EditText name;      //EditText instance
    private EditText score;     //EditText instance
    private TextView userDate;  //EditText instance
    private File ff;            //file instance

    /*
    Three Constructors
     */
    public ReadWrite(){
        super();
    }

    public ReadWrite(File f){
        ff=f;
    }

    public ReadWrite( EditText n, EditText s, TextView user_Date){
        name=n;
        score=s;
        userDate=user_Date;
    }

    /*
    THis file is to clear everything in the file, When
    user press clear button, this method will be called.
     */
    public void clearFile(){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(ff);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Read File method. WHne the app opened by user or
    //OnResume method of ScoreList activity is called,
    //this method will be called. This method is to
    //read everything to datalist and return the datalist.
    public ArrayList<String> readFile(){
        ArrayList<String> dataList = new ArrayList<String>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(ff));
            String line = bfr.readLine();
            StringBuilder sb = new StringBuilder();
            int num_line=1;
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                num_line++;
                dataList.add(line);
                line = bfr.readLine();

            }
            bfr.close();

            //Log.d("buffer", "bufferRead: " + sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    //When suer press save button, this method will be called.
    /*
    THis method is also check the validatity of name field and score field.
    In addition, this method call sort method to sort records and write the
    sorted record to file.
     */
    /*
    @Override
    public void onClick(View v) {
        //Toast.makeText(Activity.getApplicationContext(), name, Toast.LENGTH_LONG).show();

        if(name.getText().toString().isEmpty()&&score.getText().toString().isEmpty()){
            Toast.makeText(Activity.getApplicationContext(), "Please Enter Your Name and Score", Toast.LENGTH_LONG).show();
        }
        else if(name.getText().toString().isEmpty()||score.getText().toString().isEmpty()){
            if(name.getText().toString().isEmpty()){
                Toast.makeText(Activity.getApplicationContext(), "Please Enter Your Name", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(Activity.getApplicationContext(), "Please Enter Your Score", Toast.LENGTH_LONG).show();
            }
        }
        else{
            File logFile = new File(Activity.getApplicationContext().getFilesDir(),"listScore.txt");
            //logFile=sort(logFile);
            Intent intent = new Intent(Activity.getApplicationContext(), ScoreList.class);
            Activity.getApplicationContext().startActivity(intent);
        }



    }
    */

    /*
    This is sort file to sort the records from file in terms of
    scores. If the soores are same, then sort date.
     */
    public void sort(File file){
        ArrayList dataList = new ArrayList<String>();
        boolean if_insert=false;
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(file));
            if(file.length()==0){
                dataList.add(name.getText().toString()+"\t"+score.getText().toString()+"\t"+userDate.getText().toString());
            }
            else {
                String line = bfr.readLine();
                StringBuilder sb = new StringBuilder();
                while (line != null) {
                    sb.append(line);
                    sb.append("\n");
                    String[] strings=line.split("\t");
                    if(Integer.valueOf(score.getText().toString())>Integer.valueOf(strings[1])&&!if_insert){
                        dataList.add(name.getText().toString()+"\t"+score.getText().toString()+"\t"+userDate.getText().toString());
                        dataList.add(line);
                        line = bfr.readLine();
                        if_insert=true;
                    }else if((Integer.valueOf(score.getText().toString())==Integer.valueOf(strings[1]))&&!if_insert){
                        String[] ori=strings[2].split("/");
                        String[] in=userDate.getText().toString().split("/");
                        if(Integer.valueOf(ori[0])<10){
                            ori[0]="0"+ori[0];
                        }
                        if(Integer.valueOf(ori[1])<10){
                            ori[1]="0"+ori[1];
                        }
                        if(Integer.valueOf(in[0])<10){
                            in[0]="0"+in[0];
                        }
                        if(Integer.valueOf(in[1])<10){
                            in[1]="0"+in[1];
                        }
                        String date_iscompared=ori[2].toString()+ori[0].toString()+ori[1].toString();
                        String date_compare=in[2].toString()+in[0].toString()+in[1].toString();

                        if(Integer.valueOf(date_compare)>=Integer.valueOf(date_iscompared)){
                            dataList.add(name.getText().toString()+"\t"+score.getText().toString()+"\t"+userDate.getText().toString());
                            dataList.add(line);
                            line = bfr.readLine();
                            if_insert=true;
                        }
                        else {
                            dataList.add(line);
                            line = bfr.readLine();
                            if(line==null&&!if_insert){
                                dataList.add(name.getText().toString()+"\t"+score.getText().toString()+"\t"+userDate.getText().toString());
                            }
                        }

//                            System.out.println(date_compare);
//                            System.out.println(date_iscompared);
                    }else{
                        dataList.add(line);
                        line = bfr.readLine();
                        if(line==null&&!if_insert){
                            dataList.add(name.getText().toString()+"\t"+score.getText().toString()+"\t"+userDate.getText().toString());
                        }
                    }
                }
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(file, false));
            System.out.println(dataList.size());
            for(int i=0;i<dataList.size();i++){
                bfw.write(dataList.get(i).toString());
                bfw.newLine();
            }
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
