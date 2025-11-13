package aq.koptev.i.connect;

import aq.koptev.i.controllers.Observer;
import aq.koptev.i.models.ChatHistory;
import aq.koptev.i.models.Client;
import aq.koptev.i.models.ClientPool;
import aq.koptev.i.models.NetObject;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Connector implements Observable {

    private static final int DEFAULT_PORT = 5082;
    private static final String DEFAULT_HOST = "localhost";
    private List<Observer> observers;
    private NetObject netObject;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Socket socket;
    private int port;
    private String host;
    private DataPool chatData;

    public Connector() throws IOException {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public Connector(String host, int port) throws IOException {
        this.port = port;
        this.host = host;
        chatData = new ChatData();
        observers = new ArrayList<>();
        socket = new Socket(host, port);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        waitNetObject();
    }

    private void waitNetObject() {
        Thread thread = new Thread(() -> {
           while(true) {
               try {
                   netObject = (NetObject) objectInputStream.readObject();
                   notifyObservers();
               } catch (IOException e) {
                   e.printStackTrace();
               } catch (ClassNotFoundException e) {
                   e.printStackTrace();
               }
           }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void notifyObservers() {
        for(Observer observer : observers) {
            Platform.runLater(() -> observer.update(netObject));
        }
    }

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void send(NetObject netObject) {
        try {
            objectOutputStream.writeObject(netObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DataPool getDataPool() {
        return chatData;
    }

    private class ChatData implements DataPool {
        private Client client;
        private ClientPool clientPool;
        private ChatHistory chatHistory;
        @Override
        public void setChatHistory(ChatHistory chatHistory) {
            this.chatHistory = chatHistory;
        }

        @Override
        public void setClient(Client client) {
            this.client = client;
        }

        @Override
        public void setClientPool(ClientPool clientPool) {
            this.clientPool = clientPool;
        }

        @Override
        public Client getClient() {
            return client;
        }

        @Override
        public ChatHistory getChatHistory() {
            return chatHistory;
        }

        @Override
        public ClientPool getClientPool() {
            return clientPool;
        }
    }
}
