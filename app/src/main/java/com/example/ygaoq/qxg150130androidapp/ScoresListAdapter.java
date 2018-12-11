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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
This class is to customize adapter.
 */
public class ScoresListAdapter extends ArrayAdapter {

    private Context thisContext;
    int thisResource;
    int i=0;

    public ScoresListAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        thisContext=context;
        thisResource=resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String str=getItem(position).toString();
        String[]strings=str.split("\t");
        LayoutInflater inflater=LayoutInflater.from(thisContext);
        convertView=inflater.inflate(thisResource,parent,false);

        TextView name=(TextView)convertView.findViewById(R.id.textView2);
        TextView score=(TextView)convertView.findViewById(R.id.textView3);
        TextView date=(TextView)convertView.findViewById(R.id.textView4);
        TextView rank=(TextView)convertView.findViewById(R.id.textView5);

        rank.setText(Integer.toString(position+1));
        name.setText(strings[0]);
        score.setText(strings[1]);
        date.setText(strings[2]);


        return convertView;


    }
}
