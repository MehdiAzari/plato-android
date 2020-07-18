package com.plato;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkHandlerThread extends Thread {

    private ObjectOutputStream oos;
    NetworkHandlerThread( ){
        super();

    }

    @Override
    public void run() {
        super.run();
        try {
            Socket socket = new Socket("192.168.1.56", 3000);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            while (true) {
                String message = ois.readUTF();
            }
//                    Log.i("SOCKET", dis.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        final String finalMessage = message;

        Thread senderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    oos.writeUTF(finalMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        senderThread.start();

    }
}