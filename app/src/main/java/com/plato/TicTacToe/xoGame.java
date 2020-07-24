package com.plato.TicTacToe;

import android.annotation.SuppressLint;
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class xoGame extends AppCompatActivity {
    private NetworkHandlerThread networkHandlerThread;
    Button[] buttons = new Button[9];
    String typeString;
    String opponentString;

    @SuppressLint("SetTextI18n")
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
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
            Log.i("log1", "read0");
            networkHandlerThread.sendString("login");
            networkHandlerThread.getWorker().join();
            Log.i("log1", "read00");
            networkHandlerThread.sendString("amir");
            networkHandlerThread.getWorker().join();
            networkHandlerThread.sendString("1234");
            networkHandlerThread.getWorker().join();
            networkHandlerThread.readObject();
            networkHandlerThread.getWorker().join();
            networkHandlerThread.sendString("make_room");
            networkHandlerThread.getWorker().join();
            Log.i("log1", "read1");
            networkHandlerThread.sendString("xo");
            networkHandlerThread.getWorker().join();
            Log.i("log2", "read2");
            networkHandlerThread.sendString("casual");
            networkHandlerThread.getWorker().join();
            Log.i("log3", "read3");
            networkHandlerThread.sendInt(2);
            networkHandlerThread.getWorker().join();
            Log.i("log4", "read4");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(true) {

            String typeAndTurn = null;
            try {
                networkHandlerThread.readUTF();
                networkHandlerThread.getWorker().join();
                typeAndTurn = networkHandlerThread.getServerMessage();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

            char type = typeAndTurn.charAt(0);
            typeString = type + "";
            if(type=='X') opponentString = "O";
            else opponentString = "X";

                if (type == 'X') {
                    //typeText.setText("You Are X");
                }
                else {
                    //typeText.setText("You Are O");
                }

            char turn = typeAndTurn.charAt(1);
            if (turn == 'X') {
                //turnText.setText("It's X's Turn");
            } else {
                //turnText.setText("It's O's Turn");
            }

            if (type == turn) {
                for (int i = 0; i < 9; i++) {
//                    if (buttons[i].getText().toString().equals(null))
//                        buttons[i].setEnabled(true);
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
                }
                    String result = null;
                    try {
                        networkHandlerThread.readUTF();
                        networkHandlerThread.getWorker().join();
                        result = networkHandlerThread.getServerMessage();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (result.startsWith(("winner")) || result.startsWith("draw")) {
                        //resultText.setText(result);
                        break;
                    } else {
                        //resultText.setText(result);
                    }
            }
            else{
                String move = null;
                try {
                    networkHandlerThread.readUTF();
                    networkHandlerThread.getWorker().join();
                     move = networkHandlerThread.getServerMessage();
                     //opponentMove(move);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String result = null;
                try {
                    networkHandlerThread.readUTF();
                    networkHandlerThread.getWorker().join();
                    result = networkHandlerThread.getServerMessage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (result.startsWith(("winner")) || result.startsWith("draw")) {
                    //resultText.setText(result);
                    break;
                } else {
                    //resultText.setText(result);
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
                //buttons[0].setText(typeString);
                networkHandlerThread.sendString("00");
                clicked = true;
                break;
            }
            case R.id.button2:{
                //buttons[1].setText(typeString);
                networkHandlerThread.sendString("01");
                clicked = true;
                break;
            }
            case R.id.button3:{
                //buttons[2].setText(typeString);
                networkHandlerThread.sendString("02");
                clicked = true;
                break;
            }
            case R.id.button4:{
                //buttons[3].setText(typeString);
                networkHandlerThread.sendString("10");
                clicked = true;
                break;
            }
            case R.id.button5:{
                //buttons[4].setText(typeString);
                networkHandlerThread.sendString("11");
                clicked = true;
                break;
            }
            case R.id.button6:{
                //buttons[5].setText(typeString);
               networkHandlerThread.sendString("12");
                clicked = true;
                break;
            }
            case R.id.button7:{
                //buttons[6].setText(typeString);
                networkHandlerThread.sendString("20");
                clicked = true;
                break;
            }
            case R.id.button8:{
                //buttons[7].setText(typeString);
                networkHandlerThread.sendString("21");
                clicked = true;
                break;
            }
            case R.id.button9:{
                //buttons[8].setText(typeString);
                networkHandlerThread.sendString("22");
                clicked = true;
                break;
            }
        }
//        if(clicked) {
//            for (int i = 0; i < 9; i++) {
//                buttons[i].setEnabled(false);
//            }
//        }
    }

    public void opponentMove(String move){
        switch(move){
            case "00":
                buttons[0].setText(opponentString);
                break;
            case "01":
                buttons[1].setText(opponentString);
                break;
            case "02":
                buttons[2].setText(opponentString);
                break;
            case "10":
                buttons[3].setText(opponentString);
                break;
            case "11":
                buttons[4].setText(opponentString);
                break;
            case "12":
                buttons[5].setText(opponentString);
                break;
            case "20":
                buttons[6].setText(opponentString);
                break;
            case "21":
                buttons[7].setText(opponentString);
                break;
            case "22":
                buttons[8].setText(opponentString);
                break;
        }
    }

}