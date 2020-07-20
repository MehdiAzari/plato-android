//package com.plato.TicTacToe;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
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
//            networkHandlerThread = new NetworkHandlerThread();
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
//            networkHandlerThread.oos.reset();
//            networkHandlerThread.oos.writeUTF("make_room");
//            networkHandlerThread.oos.flush();
//            networkHandlerThread.oos.writeUTF("casual");
//            networkHandlerThread.oos.flush();
//            networkHandlerThread.oos.writeUTF("guessWord");
//            networkHandlerThread.oos.flush();
//            networkHandlerThread.oos.writeInt(2);
//            networkHandlerThread.oos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String role = null;
//        for (int i = 0; i < 4; i++) {
//            try {
//                 role = networkHandlerThread.ois.readUTF();
//            } catch (IOException e) {
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
//                    networkHandlerThread.oos.writeUTF(answer);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//             if(role.equals("guess")){
//                 guessText.setText("Guess The word : ");
//                try {
//                    answer = networkHandlerThread.ois.readUTF();
//                } catch (IOException e) {
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
//                     networkHandlerThread.oos.reset();
//                     networkHandlerThread.oos.writeUTF(guessText.getText().toString());
//                     networkHandlerThread.oos.flush();
//                 } catch (IOException e) {
//                     e.printStackTrace();
//                 }
//
//                 String result = null;
//                 try {
//                     result = networkHandlerThread.ois.readUTF();
//                 } catch (IOException e) {
//                     e.printStackTrace();
//                 }
//
//             }
//
//        }
//
//    }
//}
