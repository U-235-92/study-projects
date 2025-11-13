package aq.koptev.i.controllers;

import aq.koptev.i.ClientApplication;
import aq.koptev.i.connect.DataPool;
import aq.koptev.i.connect.Observable;
import aq.koptev.i.models.*;
import aq.koptev.i.util.ParameterNetObject;
import aq.koptev.i.util.TypeNetObject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class IdentificationController implements Observer {
    private Observable connector;
    private DataPool dataPool;
    private ClientApplication clientApplication;
    @FXML
    private TextField authLoginField;
    @FXML
    private Label authMessageLabel;
    @FXML
    private PasswordField authPasswordField;
    @FXML
    private Button enterBtn;
    @FXML
    private Label regMessageLabel;
    @FXML
    private Button registerBtn;
    @FXML
    private TextField registerLoginField;
    @FXML
    private PasswordField registerPasswordField;
    @FXML
    private BorderPane rootComponen;
    @FXML
    private Tab tabEnter;
    @FXML
    private Tab tabRegister;

    @FXML
    void initialize() {
        addActionListeners();
    }

    private void addActionListeners() {
        enterBtn.setOnAction((event) -> {
            String login = authLoginField.getText();
            String password = authPasswordField.getText();
            if(login.length() == 0) {
                authMessageLabel.setText("Поле логин не может быть пустым");
            } else {
                NetObject netObject = getIdentificationNetObject(TypeNetObject.REQUEST_AUTHENTICATION, login, password);
                connector.send(netObject);
            }
        });

        registerBtn.setOnAction((event) -> {
            String login = registerLoginField.getText();
            String password = registerPasswordField.getText();
            if(login.length() == 0) {
                regMessageLabel.setText("Поле логин не может быть пустым");
            } else {
                NetObject netObject = getIdentificationNetObject(TypeNetObject.REQUEST_REGISTRATION, login, password);
                connector.send(netObject);
            }
        });
    }

    private NetObject getIdentificationNetObject(TypeNetObject type, String login, String password) {
        NetObject netObject = new NetObject(type);
        Client client = new Client(login, password);
        netObject.putData(ParameterNetObject.CLIENT, NetObject.getBytes(client));
        return netObject;
    }

    @Override
    public void update(NetObject netObject) {
        switch (netObject.getType()) {
            case ERROR_AUTHENTICATION:
                processErrorAuthentication(netObject);
                break;
            case SUCCESS_AUTHENTICATION:
                processSuccessAuthentication(netObject);
                break;
            case ERROR_REGISTRATION:
                processErrorRegistration(netObject);
                break;
            case SUCCESS_REGISTRATION:
                processSuccessRegistration(netObject);
                break;
        }
    }

    private void processErrorAuthentication(NetObject netObject) {
        Message message = NetObject.getObject(netObject.getData(ParameterNetObject.MESSAGE));
        authMessageLabel.setTextFill(Color.RED);
        authMessageLabel.setText(message.getText());
    }

    private void processSuccessAuthentication(NetObject netObject) {
        ChatHistory chatHistory = NetObject.getObject(netObject.getData(ParameterNetObject.CHAT_HISTORY));
        Client client = NetObject.getObject(netObject.getData(ParameterNetObject.CLIENT));
        ClientPool clientPool = NetObject.getObject(netObject.getData(ParameterNetObject.CLIENT_POOL));
        dataPool.setChatHistory(chatHistory);
        dataPool.setClient(client);
        dataPool.setClientPool(clientPool);
        clientApplication.showChatView();
    }

    private void processErrorRegistration(NetObject netObject) {
        Message message = NetObject.getObject(netObject.getData(ParameterNetObject.MESSAGE));
        regMessageLabel.setTextFill(Color.RED);
        regMessageLabel.setText(message.getText());
    }

    private void processSuccessRegistration(NetObject netObject) {
        String text = NetObject.getObject(netObject.getData(ParameterNetObject.MESSAGE));
        regMessageLabel.setTextFill(Color.GREEN);
        regMessageLabel.setText(text);
    }

    public ClientApplication getClientApplication() {
        return clientApplication;
    }

    public Observable getConnector() {
        return connector;
    }

    public DataPool getDataPool() {
        return dataPool;
    }

    public void setClientApplication(ClientApplication clientApplication) {
        this.clientApplication = clientApplication;
    }

    public void setConnector(Observable connector) {
        this.connector = connector;
    }

    public void setDataPool(DataPool dataPool) {
        this.dataPool = dataPool;
    }
}