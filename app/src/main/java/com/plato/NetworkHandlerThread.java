package com.plato;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkHandlerThread extends Thread {

    private static NetworkHandlerThread instance = null;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private NetworkHandlerThread( ){
        super();
        instance = new NetworkHandlerThread();
    }

    public static NetworkHandlerThread getInstance(){
        if (instance == null)
            instance =  new NetworkHandlerThread();

        return instance;
    }

    @Override
    public void run() {
        super.run();
        try {
            Socket socket = new Socket("172.20.10.3", 3000);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            while (true) {
                String message = ois.readUTF();

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
}