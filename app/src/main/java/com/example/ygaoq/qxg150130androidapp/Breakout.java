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
 * This activity is the main page of game. Users play game on this activity.
 * There is a inner class in the On create method because all the picture need
 * to be rendered on iniliatized step. There are three more objects, which are
 * bricks, paddle, and bullet. Every object represents a object on the
 * activity. All the functionality like touch, bounce are presented in the
 * inner function.
 * ****************************************************************/

package com.example.ygaoq.qxg150130androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

public class Breakout extends Activity {


    GameView gameView;      //inner class object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(gameView = new GameView(this)); //set view
    }

    //All the functionality like touch, bounce are presented in the
    //inner function. For example, When user move the paddle first, the bullet
    //will leave the paddle. When the bullet touch the bricks, the
    //the bricks will be disappeared.
    class GameView extends SurfaceView implements Runnable {

        Thread gameThread = null;           //thread of starting the the thread
        SurfaceHolder holder;               //initialize surfaceview
        volatile boolean isPlaying;         //the game is playing or not
        boolean isPause = true;             //the game is paused or not
        Canvas canvas;                      //initialize canvas
        Paint paint;                        //paint object
        int screenWidth=getScreenSize().x;; //width of screen
        int screenHeight=getScreenSize().y; //height of screen
        long FPS;                           //fps
        long frameDuration;                 //duration of every frame
        int numOfBricks = 0;                //total number of bricks
        int lives=3;                        //total number of lives of user
        int currentScore=0;                 //total current scores of users
        Paddle paddle;                      //object paddle
        Bullet bullet;                      //object bullet
        Bricks[] bricks= new Bricks[500];   //initialize bricks

        /*
        This is contractor of this object. this method is to initilize
        all the attribute that are necessary to use in other method.
         */
        public GameView(Context context) {
            super(context);
            holder = getHolder();       //get instance of holder
            paint = new Paint();
            paddle = new Paddle(screenWidth, screenHeight);     //initialize paddle
            bullet = new Bullet(screenWidth, screenHeight);     //initialize bullet
            int brick_Width = screenWidth / 12;                 //calculate width of every brick
            int brick_Height = screenHeight / 15;               //calculate height of every brick
            numOfBricks = 0;                                    //number of bricks will be presented on screen

            //This for loop is to initialize all the bricks
            for(int column = 0; column < 12; column ++ ){
                for(int row = 0; row < 5; row ++ ){
                    bricks[numOfBricks] = new Bricks(row, column, brick_Width, brick_Height);
                    numOfBricks ++;
                }
            }
        }

        //This method is to get width and height of the screen
        private Point getScreenSize(){
            Display display = getWindowManager().getDefaultDisplay();
            Point ScreenSize = new Point();
            display.getSize(ScreenSize);
            return ScreenSize;
        }

        //this run method is overrided that implements Runnable interface.
        //this method will be implemented while the game is playing
        //three method will be implemented in this method, which are
        //update method, draw method.
        @Override
        public void run() {
            while (isPlaying) {
                long startTime_Frame = System.currentTimeMillis();
                if(!isPause){
                    update();
                }
                draw();
                FPS=calFPS(startTime_Frame);
            }
        }


        //This method is to calculate fps
        public long calFPS(long startTime_Frame){
            frameDuration = System.currentTimeMillis() - startTime_Frame;
            if (frameDuration >= 1) {
                FPS = 1000 / frameDuration;
            }
            return FPS;
        }


        //this method is to update the picture for very frame that
        //are rendered on screen.
        public void update() {
            paddle.update(FPS);     //update paddle's picture on screen
            bullet.update(FPS);     //update bullet's picture on screen


            //This for loop is to update bricks' picture, if bullet is touch brick,
            //the brick will be invisable on screen and the direction of movement of
            //bullet will be changed to another way. keep calculating scores
            for(int i = 0; i < numOfBricks; i++){
                if (bricks[i].getVisible()){
                    if(RectF.intersects(bricks[i].getRectangle(),bullet.getRectangle())) {
                        bricks[i].setDisappear();
                        bullet.reverseVertical();
                        currentScore = currentScore + 10;
                    }
                }
            }

            //if paddle and bullet are collided, the direction of movement of
            //bullet will be changed to another way.
            if(RectF.intersects(paddle.getRectangle(),bullet.getRectangle())) {
                bullet.reverseVertical();
                bullet.clearVerticalObstacle(paddle.getRectangle().top - 2);
            }

            //if paddle and left side of screen are collided, the direction of movement of
            //bullet will be changed to another way.
            if(bullet.getRectangle().left < 0){
                bullet.reverseHorizontal();
                bullet.clearHorizontalObstacle(2);
            }

            //if paddle and right side of screen are collided, the direction of movement of
            //bullet will be changed to another way.
            if(bullet.getRectangle().right > screenWidth - 10){
                bullet.reverseHorizontal();
                bullet.clearHorizontalObstacle(screenWidth - 22);
            }

            //if paddle and top side of screen are collided, the direction of movement of
            //bullet will be changed to another way.
            if(bullet.getRectangle().top < 0){
                bullet.reverseVertical();
                bullet.clearVerticalObstacle(12);
            }


            //if all bricks are invisable, the activity will be over and
            // transfer to another acitivty to save its record
            if(currentScore == numOfBricks * 10){
                isPause = true;
                Intent intent = new Intent(Breakout.this, AddScore.class);
                intent.putExtra("score", Integer.toString(currentScore));
                startActivity(intent);
            }


            //if paddle and bottom side of screen are collided, the direction of movement of
            //bullet will be changed to another way. If lives is down to 0, this game is over and
            //the activity will be over and transfer to another acitivty to save its record
            if(bullet.getRectangle().bottom > screenHeight){
                bullet.reverseVertical();
                bullet.clearVerticalObstacle(screenHeight - 2);
                lives =lives-1;
                if(lives == 0){
                    isPause = true;
                    Intent intent = new Intent(Breakout.this, AddScore.class);
                    intent.putExtra("score", Integer.toString(currentScore));
                    startActivity(intent);
                }
            }

        }

        //This method is very important because all objects need to be rendered in
        //this method. All three objects, like bullet, paddle, and bricks are drawn
        //in this method.
        public void draw() {
            if (holder.getSurface().isValid()) {
                canvas = holder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                paint.setColor(Color.BLUE);
                canvas.drawRect(paddle.getRectangle(), paint);
                canvas.drawRect(bullet.getRectangle(), paint);
                paint.setColor(Color.RED);
                for(int i = 0; i < numOfBricks; i++){
                    if(bricks[i].getVisible()) {
                        canvas.drawRect(bricks[i].getRectangle(), paint);
                    }
                }
                paint.setColor(Color.GREEN);
                paint.setTextSize(20);
                canvas.drawText("Current Score: " + currentScore + "   Lives: " + lives, 10,700, paint);
                holder.unlockCanvasAndPost(canvas);
            }

        }


        //when this activity is paused, this game is paused
        public void pause() {
            isPlaying = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                System.out.print("Error");
            }

        }

        //if this activity is resume, this game is resumed.
        public void resume() {
            isPlaying = true;
            gameThread = new Thread(this);
            gameThread.start();
        }


        //this touchevern is override when user operate on screen.
        //When user touch left side of the screen, paddle move to
        //left and right if user touch right side of screen.
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    isPause = false;
                    if(motionEvent.getX() > screenWidth / 2){
                        paddle.changeMovementState(paddle.RIGHT);
                    }
                    else{
                        paddle.changeMovementState(paddle.LEFT);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    paddle.changeMovementState(paddle.STOP);
                    break;
            }
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

}