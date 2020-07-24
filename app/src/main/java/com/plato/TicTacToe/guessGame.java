//package com.plato.TicTacToe;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.util.Log;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.plato.NetworkHandlerThread;
//import com.plato.R;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//
//public class guessGame extends AppCompatActivity {
//    private NetworkHandlerThread networkHandlerThread;
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.guess_game);
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//        try {
//            networkHandlerThread =  NetworkHandlerThread.getInstance();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        networkHandlerThread.start();
//
//        TextView guessText = findViewById(R.id.guess_text);
//        TextView wordText = findViewById(R.id.word_text);
//        EditText editText = findViewById(R.id.editText);
//
//        try {
//
//            networkHandlerThread.sendString("make_room");
//            networkHandlerThread.getWorker().join();
//            Log.i("log1", "read1");
//            networkHandlerThread.sendString("guessWord");
//            networkHandlerThread.getWorker().join();
//            Log.i("log2", "read2");
//            networkHandlerThread.sendString("casual");
//            networkHandlerThread.getWorker().join();
//            Log.i("log3", "read3");
//            networkHandlerThread.sendInt(2);
//            networkHandlerThread.getWorker().join();
//            Log.i("log4", "read4");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        String role = null;
//        for (int i = 0; i < 4; i++) {
//            try {
//                networkHandlerThread.readUTF();
//                networkHandlerThread.getWorker().join();
//                role = networkHandlerThread.getServerMessage();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            String answer = null;
//            int length = 0;
//
//            if(role.equals("word")){
//                guessText.setText("Choose A Word : ");
//                 answer = editText.getText().toString();
//                try {
//                    networkHandlerThread.sendString(answer);
//                    networkHandlerThread.getWorker().join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//             if(role.equals("guess")){
//                 guessText.setText("Guess The word : ");
//                try {
//                    networkHandlerThread.readUTF();
//                    networkHandlerThread.getWorker().join();
//                    answer = networkHandlerThread.getServerMessage();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                 length = answer.length();
//                String hiddenAnswer = null;
//                for (int j = 0; j < length; j++) {
//                        hiddenAnswer += "*";
//                }
//                 for (int j = 0; j < length; j++) {
//                     String guessedChar = editText.getText().toString();
//                     if(answer.contains(guessedChar)){
//                         int index = answer.indexOf(guessedChar);
//                         hiddenAnswer = hiddenAnswer.replace(hiddenAnswer.charAt(index)+"",guessedChar);
//                         guessText.setText(hiddenAnswer);
//                     }
//                 }
//                 try {
//                     networkHandlerThread.sendString(guessText.getText().toString());
//                     networkHandlerThread.getWorker().join();
//                 } catch (InterruptedException e) {
//                     e.printStackTrace();
//                 }
//
//                 String result = null;
//                 try {
//                     networkHandlerThread.readUTF();
//                     networkHandlerThread.getWorker().join();
//                     result = networkHandlerThread.getServerMessage();
//                 } catch (InterruptedException e) {
//                     e.printStackTrace();
//                 }
//
//             }
//
//        }
//
//    }
//}
