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
 * This class is object of bullet. All attributes and features
 * will be programmed on this class.
 * ****************************************************************/

package com.example.ygaoq.qxg150130androidapp;

import android.graphics.RectF;

import java.util.Random;

public class Bullet {
    RectF rectangle;            //bullet is shape of retangle
    float horizontal_speed;     //horizontal speed of movement of bullet
    float vertical_speed;       //vertical speed of movement of bullet
    float bulletWidth = 8;      //width of bullet
    float bulletHeight = 8;     //height of bullet

    //contractor that is to initialize rectangle position which is locate
    //ate middle of screen and on top of paddle
    public Bullet(int screenWidth, int screenHeight){
        rectangle = new RectF();
        rectangle.left = screenWidth / 2+10;
        rectangle.top = screenHeight - 20;
        rectangle.right = screenWidth / 2+10 + bulletWidth;
        rectangle.bottom = screenHeight - 20 - bulletHeight;
        horizontal_speed = 250;
        vertical_speed = -450;
    }

    //getter method that is to return rectangle
    public RectF getRectangle(){
        return rectangle;
    }

    //update method that is to update the position of retangle on the screen
    public void update(long fps){
        rectangle.left = rectangle.left + (horizontal_speed / fps);
        rectangle.top = rectangle.top + (vertical_speed / fps);
        rectangle.right = rectangle.left + bulletWidth;
        rectangle.bottom = rectangle.top - bulletHeight;
    }

    //this method is to reverse vertical direction of movement of bullet
    public void reverseVertical(){
        vertical_speed = -vertical_speed;
    }

    //this method is to reverse horizontal direction of movement of bullet
    public void reverseHorizontal(){
        horizontal_speed = - horizontal_speed;
    }

    public void clearVerticalObstacle(float a){
        rectangle.bottom = a;
        rectangle.top = a - bulletHeight;
    }

    public void clearHorizontalObstacle(float a){
        rectangle.left = a;
        rectangle.right = a + bulletWidth;
    }

}