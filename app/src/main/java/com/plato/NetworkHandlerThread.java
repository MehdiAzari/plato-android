package com.plato;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkHandlerThread extends Thread {

    private static NetworkHandlerThread instance = null;
    private volatile ObjectOutputStream oos = null;
    private volatile ObjectInputStream ois = null;
    private String serverStringMessage = "s";
    private volatile Socket socket = null;
    private int serverIntMessage;

    private NetworkHandlerThread() throws IOException {
        super();
        Log.i("Socket", "connecting to socket...");
    }


    public static NetworkHandlerThread getInstance() throws IOException {
        if (instance == null) {
            instance = new NetworkHandlerThread();

        }
        return instance;
    }


    @Override
    public void run() {
        super.run();
        try {

            if (socket == null) {
                socket = new Socket("10.0.2.2", 3535);
                Log.i("svNew Socket", "New Socket");
            }
            Log.i("svSocket", "Connected to socket");
            if (oos == null)
                this.oos = new ObjectOutputStream(socket.getOutputStream());
            if (ois == null)
                this.ois = new ObjectInputStream(socket.getInputStream());


            while (true){
                Log.i("svRead","reading UTF");
                serverStringMessage = ois.readUTF();
                Log.i("svRead","gotURF");
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
                    oos.reset();
                    oos.writeUTF(finalMessage);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        senderThread.start();

    }

//    public void startReadingMessage(){
//        Thread senderThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (true){
//                        Log.i("svRead","reading UTF");
//                        serverStringMessage = ois.readUTF();
//                        Log.i("svRead","gotURF");
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        senderThread.start();
//    }

    public void sendInt(int message) {
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