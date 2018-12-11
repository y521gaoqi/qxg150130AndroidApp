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
 * This class is object of bricks. All attributes and features
 * will be programmed on this class.
 * ****************************************************************/

package com.example.ygaoq.qxg150130androidapp;

import android.graphics.RectF;

public class Bricks {

    private RectF rectangle;        //every bricks are a rectangle

    private boolean visible;        //every bricks has a attribute of visable

    //contractor that is to initialize every rectangle
    public Bricks(int rows, int columns, int brickWidth, int brickHeight){

        visible = true;
        int gap = 1;
        rectangle = new RectF(columns * brickWidth + gap,
                rows * brickHeight + gap,
                columns * brickWidth + brickWidth - gap,
                rows * brickHeight + brickHeight - gap);
    }

    //getter method to return rectangle object
    public RectF getRectangle(){
        return this.rectangle;
    }

    //set its attribute
    public void setDisappear(){
        visible = false;
    }

    //getter method of return visible attribure.
    public boolean getVisible(){
        return visible;
    }
}