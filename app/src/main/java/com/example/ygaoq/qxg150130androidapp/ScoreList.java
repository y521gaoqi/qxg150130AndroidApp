/******************************************************************************
 * Breakout Program
 *
 * This program enable user to look the whole rank of scores. There are three
 * acitivity, one of them is list page, the another one is entry page,In addition,
 * user is able to input their own name, score, and date. Moreover, after they
 * enter their score an person info, the scores will be sorted automatically.
 * The main activity of this program is a the page of game. This game is a breakout
 * game, user can move the paddle to bounce the bullet. Every user has three lives,
 * if user cannot get the bullet by paddle which also means bullet touch down the bottom
 * of screen, the number of lives minus one. If number of lives becomes to zero, the game is
 * over, user need to input their name to save the record. In addition, if user get ride of
 * all bricks, then the user input their name to save the record.
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

/****************************************************************
 * This is first activity users would see when they open app. Tis page includes
 * a button and a listView. All the records will be shown in the listView and they
 * are all be ranked.
 ****************************************************************/
package com.example.ygaoq.qxg150130androidapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreList extends AppCompatActivity {

    private ListView listview;
    private ArrayList<String> dataList = null;

    /**********************************
     * Rewrite override method onCreat. This method is to
     * load data from file and initialize all the listView.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);
        File logFile = new File(this.getFilesDir(),"listScore.txt");

        ReadWrite r=new ReadWrite(logFile);
        dataList=r.readFile();
        ScoresListAdapter adapter = new ScoresListAdapter(this,
                R.layout.adapter_layout,dataList);
        listview = (ListView) findViewById(R.id.listScores);
        listview.setAdapter(adapter);
    }

    /**********************************
     * Rewrite override method onResume. This method is to
     * load data from file and initialize all the listView
     * when user back to this page from other activities.
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        File logFile = new File(this.getFilesDir(),"listScore.txt");
        ReadWrite r=new ReadWrite(logFile);
        dataList=r.readFile();
        ScoresListAdapter adapter = new ScoresListAdapter(this,
                R.layout.adapter_layout,dataList);
        listview = (ListView) findViewById(R.id.listScores);
        listview.setAdapter(adapter);
    }

    /*****
     * These two methods are to set up action bar and the button
     * on the action bar. When users press the button, they will
     * be redirected to entry page.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_scores_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.bt_add_users:
                Intent intent = new Intent (this, Breakout.class);
                startActivity(intent);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }


    }
}
