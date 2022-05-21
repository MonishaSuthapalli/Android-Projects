package com.example.thegophergame;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class contActivity extends AppCompatActivity {

    private Handler runnableHandler;
    private Handler messageHandler;
    private Handler runnableHandler2;
    private Button startButton;
    private TextView p1stats;
    private TextView p2stats;
    private Button stopB;
    private TextView result;
    final int gopher = 3;
    final int player1 = 1;
    final int player2 = 2;
    private Random r;
    //cellpId is for player 2 table, cellId is for player1
    //https://stackoverflow.com/questions/55411752/how-to-get-tablelayout-cell-values-in-android
    final int[] cellpId = {R.id.cellp1, R.id.cellp1, R.id.cellp2, R.id.cellp3, R.id.cellp4, R.id.cellp5, R.id.cellp6, R.id.cellp7, R.id.cellp8, R.id.cellp9, R.id.cellp10, R.id.cellp11, R.id.cellp12, R.id.cellp13, R.id.cellp14, R.id.cellp15, R.id.cellp16, R.id.cellp17, R.id.cellp18, R.id.cellp19, R.id.cellp20, R.id.cellp21, R.id.cellp22, R.id.cellp23, R.id.cellp24, R.id.cellp25, R.id.cellp26, R.id.cellp27, R.id.cellp28, R.id.cellp29, R.id.cellp30, R.id.cellp31, R.id.cellp32, R.id.cellp33, R.id.cellp34, R.id.cellp35, R.id.cellp36, R.id.cellp37, R.id.cellp38, R.id.cellp39, R.id.cellp40, R.id.cellp41, R.id.cellp42, R.id.cellp43, R.id.cellp44, R.id.cellp45, R.id.cellp46, R.id.cellp47, R.id.cellp48, R.id.cellp49, R.id.cellp50, R.id.cellp51, R.id.cellp52, R.id.cellp53, R.id.cellp54, R.id.cellp55, R.id.cellp56, R.id.cellp57, R.id.cellp58, R.id.cellp59, R.id.cellp60, R.id.cellp61, R.id.cellp62, R.id.cellp63, R.id.cellp64, R.id.cellp65, R.id.cellp66, R.id.cellp67, R.id.cellp68, R.id.cellp69, R.id.cellp70, R.id.cellp71, R.id.cellp72, R.id.cellp73, R.id.cellp74, R.id.cellp75, R.id.cellp76, R.id.cellp77, R.id.cellp78, R.id.cellp79, R.id.cellp80, R.id.cellp81, R.id.cellp82, R.id.cellp83, R.id.cellp84, R.id.cellp85, R.id.cellp86, R.id.cellp87, R.id.cellp88, R.id.cellp89, R.id.cellp90, R.id.cellp91, R.id.cellp92, R.id.cellp93, R.id.cellp94, R.id.cellp95, R.id.cellp96, R.id.cellp97, R.id.cellp98, R.id.cellp99, R.id.cellp100};
    final int[] cellId = {R.id.cell1, R.id.cell1, R.id.cell2, R.id.cell3, R.id.cell4, R.id.cell5, R.id.cell6, R.id.cell7, R.id.cell8, R.id.cell9, R.id.cell10, R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell14, R.id.cell15, R.id.cell16, R.id.cell17, R.id.cell18, R.id.cell19, R.id.cell20, R.id.cell21, R.id.cell22, R.id.cell23, R.id.cell24, R.id.cell25, R.id.cell26, R.id.cell27, R.id.cell28, R.id.cell29, R.id.cell30, R.id.cell31, R.id.cell32, R.id.cell33, R.id.cell34, R.id.cell35, R.id.cell36, R.id.cell37, R.id.cell38, R.id.cell39, R.id.cell40, R.id.cell41, R.id.cell42, R.id.cell43, R.id.cell44, R.id.cell45, R.id.cell46, R.id.cell47, R.id.cell48, R.id.cell49, R.id.cell50, R.id.cell51, R.id.cell52, R.id.cell53, R.id.cell54, R.id.cell55, R.id.cell56, R.id.cell57, R.id.cell58, R.id.cell59, R.id.cell60, R.id.cell61, R.id.cell62, R.id.cell63, R.id.cell64, R.id.cell65, R.id.cell66, R.id.cell67, R.id.cell68, R.id.cell69, R.id.cell70, R.id.cell71, R.id.cell72, R.id.cell73, R.id.cell74, R.id.cell75, R.id.cell76, R.id.cell77, R.id.cell78, R.id.cell79, R.id.cell80, R.id.cell81, R.id.cell82, R.id.cell83, R.id.cell84, R.id.cell85, R.id.cell86, R.id.cell87, R.id.cell88, R.id.cell89, R.id.cell90, R.id.cell91, R.id.cell92, R.id.cell93, R.id.cell94, R.id.cell95, R.id.cell96, R.id.cell97, R.id.cell98, R.id.cell99, R.id.cell100};
    final int[] positionHoles = new int[101];
    final int SUCCESS = 1;
    final int NEARMISS = 2;
    final int CLOSEGUESS = 3;
    final int COMPLETEMISS = 4;
    final int failFlag = 5;
    private boolean gotTheGopher = false;
    private int gopherPosition;
    final String[] resultSet = {"", "Success", "Near Miss", "Close Guess", "Complete Miss","COMPLETE MISS"};
    int p1guess=1;
    int p2guess=1;
    private Handler player1Handler;
    private Handler player2Handler;


    /*
    Player 1 makes random guesses
    Player 2 makes guess starting from end of the gopher table
    Player1 and Player2, worker thread handlers set the winners text : Who won
    messages from worker threads are passed to UI message handler and they set the guess text
    Runnables from both the work threads post on runnableHandler and they are used to fix the guess position on corresponding tables.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runnableHandler = new Handler(Looper.getMainLooper());
        //handlers for the worker threads
        player1Handler=new Handler();
        player2Handler=new Handler();
        stopB = findViewById(R.id.stopbutton);

        p1stats = findViewById(R.id.p_status);
        p2stats = findViewById(R.id.p2_status);

        result = findViewById(R.id.result);
        begin();

    }

    private void begin() {


        stopB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread[] threads = new Thread[Thread.activeCount()];
                Thread.enumerate(threads);
                for (Thread t : threads) {

                    t.interrupt();
                    setContentView(R.layout.stopgame);

                }
            }
        });

        r = new Random();
        //gets a random integer with an upper bouund of 101
        gopherPosition = r.nextInt(101);
        //setGopherposition
        placeTheGuess(gopher, gopherPosition);


        //The UI handler handles messages between two worker threads
        messageHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg) {
                int what = msg.what;

                if(what==player1)
                {
                    p1stats.setText("Guessed Position" + msg.arg1 + ": " + resultSet[msg.arg2]);
                    player1Handler.sendMessage(player1Handler.obtainMessage());
                }
                else if(what==player2)
                {

                     p2stats.setText("Guessed Position " + msg.arg1 + ": " + resultSet[msg.arg2]);
                      player2Handler.sendMessage(player2Handler.obtainMessage());

                }

            }
        };
        //starts both the worker threads
        startThreads();
    }


    void startThreads()
    {
        Thread player1Thread = new Thread(new Player1Impl());
        player1Thread.start();
        Thread player2Thread = new Thread(new Player2Impl());
        player2Thread.start();
    }

    private void placeTheGuess(int actor, int position) {

        if (actor == gopher) {

            TextView currCell = (TextView) findViewById(cellId[position]);
            currCell.setText("G");
            positionHoles[position] = gopher;

            TextView pcurrCell = (TextView) findViewById(cellpId[position]);
            pcurrCell.setText("G");
            positionHoles[position] = gopher;

        } else if (actor == player1) {

            TextView currCell = (TextView) findViewById(cellId[position]);
            currCell.setText(""+p1guess++);
            positionHoles[position] = player1;


        } else if (actor == player2) {

            TextView pcurrCell = (TextView) findViewById(cellpId[position]);
            pcurrCell.setText(""+p2guess++);
            positionHoles[position] = player2;

        }


    }

    //for each guess, this result populates
    private int calcResult(int playerPosition) {

        int prow = playerPosition / 10, pcol = playerPosition % 10, gRow = gopherPosition / 10, gcol = gopherPosition % 10;

        if (positionHoles[playerPosition] != 0 && positionHoles[playerPosition] != 3) {

            return failFlag;
        } else if (gopherPosition == playerPosition) {

            return SUCCESS;
        }
        //diagonal on any side
        else if ((prow == gRow && Math.abs(pcol - gcol) == 2) || (pcol == gcol && Math.abs(prow - gRow) == 2) || (Math.abs(pcol - gcol) == 2 && Math.abs(prow - gRow) == 2)) {

            return CLOSEGUESS;

        } else if (playerPosition >= gopherPosition - 8 && playerPosition <= gopherPosition + 8) {

            return NEARMISS;
        } else {

            return COMPLETEMISS;
        }
    }

    //Player 1 uses random approach, it just keeps on making random guesses
    class Player1Impl implements Runnable {


        @Override
        public void run() {
            //execute only if gopher is not found
            while (!gotTheGopher) {

                r = new Random();
                //generates a random integer between 1 and 100
                final int player1Position = r.nextInt(100);
                final int guessResult = calcResult(player1Position);
                if (guessResult != failFlag) {
                    Message msg = messageHandler.obtainMessage(player1);
                    msg.arg1 = player1Position;
                    msg.arg2 = guessResult;
                    messageHandler.sendMessage(msg);
                    runnableHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (positionHoles[player1Position] == 0) {
                                placeTheGuess(player1, player1Position);
//
                            }
                        }
                    });

                    if (guessResult == SUCCESS) {
                        gotTheGopher =true;
                        //player1 handle sets the result text
                        Looper.prepare();
                        // Handle messages receiving from the UI Thread
                        player1Handler = new Handler(Looper.myLooper()) {
                            public void handleMessage(Message msg) {
                                if(guessResult==SUCCESS)
                                    result.setText("Player 1 wins");
                                Log.i("success","this");

                            }
                        };
                        Looper.loop();



                        TextView currCell = (TextView) findViewById(cellId[player1Position]);

                        p1stats.setText("Guessed " + player1Position + ": Success");

                        break;
                    }
                } else {
                    p1stats.setText("Guessed " + player1Position + ": " + resultSet[5]);
                }
                try {
                    int sleep = 2000;
                    Thread.sleep(sleep);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Player 2 comes from end of the loop
    class Player2Impl implements Runnable {

        @Override
        public void run() {

            for (int i = 100; i >= 1 && !gotTheGopher; i--) {

                final int player2Position = i;
                final int guessResult = calcResult(player2Position);
                if (guessResult != failFlag) {

                    Message msg = messageHandler.obtainMessage(player2);
                    msg.arg1 = player2Position;
                    msg.arg2 = guessResult;
                    messageHandler.sendMessage(msg);
                    runnableHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (positionHoles[player2Position] == 0) {

                                placeTheGuess(player2, player2Position);
//
                            }
                        }

                    });
                    if (guessResult == SUCCESS) {
//                        result.setText("P2 winner yay");
                        gotTheGopher = true;
                        Looper.prepare();
                        //player2 handle sets the result text
                        player2Handler = new Handler(Looper.myLooper()) {
                            public void handleMessage(Message msg) {
                                if(guessResult==SUCCESS)
                                    result.setText("P2 winner yayy");
                                    Log.i("success","this");
                            }
                        };
                        Looper.loop();
                       


                        p2stats.setText("Guessed position number" + player2Position + ": Success");

                        break;
                    }
                } else {
                    p2stats.setText("Guessed position number" + player2Position + ": " + resultSet[5]);
                }
                try {
                    int sleep = 2000;
                    Thread.sleep(sleep);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}




