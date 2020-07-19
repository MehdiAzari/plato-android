package com.plato;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkHandlerThread extends Thread {

    private static NetworkHandlerThread instance = null;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String serverStringMessage;
    private Socket socket;
    private int serverIntMessage;

    private NetworkHandlerThread() throws IOException {
        super();
        Log.i("Socket","connecting to socket...");
        socket = new Socket("172.20.10.3", 4000);
        Log.i("Socket","connected to socket");

        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }


    public static NetworkHandlerThread getInstance() throws IOException {
        if (instance == null)
            instance = new NetworkHandlerThread();

        return instance;
    }



    @Override
    public void run() {
        super.run();
        try {
            while (true) {
                 this.serverStringMessage = ois.readUTF();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public String getServerMessage() {
        return serverStringMessage;
    }

    public void sendString(String message) {
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

    public void sendInt(int message){
        final int finalMessage = message;
        Thread senderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    oos.writeInt(finalMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        senderThread.start();
    }
}