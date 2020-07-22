package com.plato;

import android.util.Log;

import com.plato.server.User;

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
    private volatile Object serverObject = null;
    private volatile int serverIntMessage;
    private User user = null;
    private Thread IOHandler;

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
        try {
                Log.i("NetworkHandler","running thread");
            if (this.socket == null) {
                Log.i("svNew Socket", "making new Socket");
                socket = new Socket("10.0.2.2", 3838);

                Log.i("svNew Socket", "New Socket");
            }
            Log.i("svSocket", "Connected to socket");

            if (oos == null)
                this.oos = new ObjectOutputStream(socket.getOutputStream());
            if (ois == null)
                this.ois = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Socket getSocket() {
        return socket;
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

    public Object getServerObject() {
        return serverObject;
    }

    public Thread getIOHandler() {
        return IOHandler;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void sendUTF(final String messages) {

         IOHandler = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        oos.writeUTF(messages);
                        oos.flush();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        IOHandler.start();

    }

    public void readUTF(){
         IOHandler = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        Log.i("svRead","reading UTF");
                        serverStringMessage = ois.readUTF();
                        Log.i("svRead","gotURF");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        IOHandler.start();
    }

    public void readObject(){
         IOHandler = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("svRead","reading Object");

                    Log.i("svOisAvai" , String.valueOf(ois.available()));
                    serverObject = ois.readObject();
                    Log.i("svRead","got Obj");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        IOHandler.start();
    }

    public void readInt(){
         IOHandler = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("svRead","reading int");
                    serverIntMessage = ois.readInt();
                    Log.i("svRead","got int");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        IOHandler.start();
    }

    public void sendInt(int message) {
        final int finalMessage = message;
         IOHandler = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    oos.writeInt(finalMessage);
                    oos.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        IOHandler.start();
    }

}