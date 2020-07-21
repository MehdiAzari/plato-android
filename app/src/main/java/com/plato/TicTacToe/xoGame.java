package com.plato.TicTacToe;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.plato.NetworkHandlerThread;
import com.plato.R;

import java.io.IOException;

public class xoGame extends AppCompatActivity {
    private NetworkHandlerThread networkHandlerThread;
    Button[] buttons = new Button[9];
    String typeString;
    String opponentString;

    //@SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xo_game);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Thread networkThread = new Thread(new Runnable() {
            @Override
            public void run() {


        try {
            networkHandlerThread = NetworkHandlerThread.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //networkHandlerThread.setDaemon(true);
        networkHandlerThread.start();
        try {
            networkHandlerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        buttons[0] = findViewById(R.id.button1);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);
        buttons[4] = findViewById(R.id.button5);
        buttons[5] = findViewById(R.id.button6);
        buttons[6] = findViewById(R.id.button7);
        buttons[7] = findViewById(R.id.button8);
        buttons[8] = findViewById(R.id.button9);

        TextView typeText = findViewById(R.id.type_text);
        TextView turnText = findViewById(R.id.turn_text);
        TextView resultText = findViewById(R.id.result_text);

        try {

            networkHandlerThread.sendUTF("make_room");
            networkHandlerThread.getIOHandler().join();
            Log.i("log1", "read1");
            networkHandlerThread.sendUTF("xo");
            networkHandlerThread.getIOHandler().join();
            Log.i("log2", "read2");
            networkHandlerThread.sendUTF("casual");
            networkHandlerThread.getIOHandler().join();
            Log.i("log3", "read3");
            networkHandlerThread.sendInt(2);
            networkHandlerThread.getIOHandler().join();
            Log.i("log4", "read4");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String typeAndTurn = null;



        try {
            networkHandlerThread.readUTF();
            networkHandlerThread.getIOHandler().join();
            typeAndTurn = networkHandlerThread.getServerMessage();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        char type = typeAndTurn.charAt(0);
        typeString = type + "";
        if(type=='X') opponentString = "O";
        else opponentString = "X";
        boolean showType = false;

        if(!showType) {
            if (type == 'X') {
                typeText.setText("You Are X");
                showType = true;
            }
            else {
                typeText.setText("You Are O");
                showType = true;
            }
        }
        while(true) {

            char turn = typeAndTurn.charAt(1);
            if (turn == 'X') {
                turnText.setText("It's X's Turn");
            } else {
                turnText.setText("It's O's Turn");
            }

            if (type == turn) {
                for (int i = 0; i < 9; i++) {
                    buttons[i].setEnabled(true);
                    buttons[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                buttonClicked(v);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    String result = null;
                    try {
                        networkHandlerThread.readUTF();
                        networkHandlerThread.getIOHandler().join();
                        result = networkHandlerThread.getServerMessage();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (result.startsWith(("winner")) || result.startsWith("draw")) {
                        resultText.setText(result);
                        break;
                    } else {
                        resultText.setText(result);
                    }

                }
            }
            else{
                String move = null;
                try {
                    //added to room.java
                    networkHandlerThread.readUTF();
                    networkHandlerThread.getIOHandler().join();
                     move = networkHandlerThread.getServerMessage();
                     opponentMove(move);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String result = null;
                try {
                    networkHandlerThread.readUTF();
                    networkHandlerThread.getIOHandler().join();
                    result = networkHandlerThread.getServerMessage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (result.startsWith(("winner")) || result.startsWith("draw")) {
                    resultText.setText(result);
                    break;
                } else {
                    resultText.setText(result);
                }

            }
        }
            }
        });
        networkThread.start();
    }
    public void buttonClicked(View v) throws IOException {
        boolean clicked = false;
        switch (v.getId()){
            case R.id.button1:{
                buttons[0].setText(typeString);
                networkHandlerThread.sendUTF("11");
                clicked = true;
                break;
            }
            case R.id.button2:{
                buttons[1].setText(typeString);
                networkHandlerThread.sendUTF("12");
                clicked = true;
                break;
            }
            case R.id.button3:{
                buttons[2].setText(typeString);
                networkHandlerThread.sendUTF("13");
                clicked = true;
                break;
            }
            case R.id.button4:{
                buttons[3].setText(typeString);
                networkHandlerThread.sendUTF("21");
                clicked = true;
                break;
            }
            case R.id.button5:{
                buttons[4].setText(typeString);
                networkHandlerThread.sendUTF("22");
                clicked = true;
                break;
            }
            case R.id.button6:{
                buttons[5].setText(typeString);
               networkHandlerThread.sendUTF("23");
                clicked = true;
                break;
            }
            case R.id.button7:{
                buttons[6].setText(typeString);
                networkHandlerThread.sendUTF("31");
                clicked = true;
                break;
            }
            case R.id.button8:{
                buttons[7].setText(typeString);
                networkHandlerThread.sendUTF("32");
                clicked = true;
                break;
            }
            case R.id.button9:{
                buttons[8].setText(typeString);
                networkHandlerThread.sendUTF("33");
                clicked = true;
                break;
            }
        }
        if(clicked) {
            for (int i = 0; i < 9; i++) {
                buttons[i].setEnabled(false);
            }
        }
    }

    public void opponentMove(String move){
        switch(move){
            case "11":
                buttons[0].setText(opponentString);
                break;
            case "12":
                buttons[1].setText(opponentString);
                break;
            case "13":
                buttons[2].setText(opponentString);
                break;
            case "21":
                buttons[3].setText(opponentString);
                break;
            case "22":
                buttons[4].setText(opponentString);
                break;
            case "23":
                buttons[5].setText(opponentString);
                break;
            case "31":
                buttons[6].setText(opponentString);
                break;
            case "32":
                buttons[7].setText(opponentString);
                break;
            case "33":
                buttons[8].setText(opponentString);
                break;
        }
    }

}