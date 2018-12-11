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
 * This class is object of paddle. All attributes and features
 * will be programmed on this class.
 * ****************************************************************/

package com.example.ygaoq.qxg150130androidapp;

import android.graphics.RectF;

public class Paddle {

    //four side of the paddle
    private float leftSide;
    private float topSide;
    private float width= 150;
    private float height= 25;
    private RectF rectangle;            //rectangle object initiazliztion
    private float SPEED = 400;                //speed of movement of paddle
    //three state of movement of paddle
    public final int STOP = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    private int MOVING;
    int Screen_Width;                   //screen size


    //constractor that is to initialize all attributes.
    public Paddle(int Screen_Width, int Screen_Hight){

        this.Screen_Width=Screen_Width;
        MOVING=STOP;

        leftSide = Screen_Width / 2-60;
        topSide = Screen_Hight - 20;

        rectangle = new RectF();
        rectangle.left=leftSide;
        rectangle.top=topSide;
        rectangle.bottom=topSide+height;
        rectangle.right=leftSide+width;

        //rectangle = new RectF(leftSide, topSide, leftSide + width, topSide + height);
    }

    //method that is to change state of movement
    public void changeMovementState(int STATE){
        MOVING = STATE;
    }

    //getter that is to return rectangle
    public RectF getRectangle(){
        return rectangle;
    }

    //update method that is to set the position on screen
    public void update(long fps){
        if(MOVING == LEFT){
            if(!(leftSide<=0)){
                leftSide = leftSide - SPEED / fps;
            }
        }

        if(MOVING == RIGHT){
            if(!((leftSide+130)>=Screen_Width)){
                leftSide = leftSide + SPEED / fps;
            }
        }

        rectangle.left = leftSide;
        rectangle.right = leftSide + width;
    }
}