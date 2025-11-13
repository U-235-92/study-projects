package aq.koptev.i.controllers;

import aq.koptev.i.ClientApplication;
import aq.koptev.i.connect.DataPool;
import aq.koptev.i.connect.Observable;
import aq.koptev.i.models.*;
import aq.koptev.i.util.ParameterNetObject;
import aq.koptev.i.util.TypeNetObject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatController implements Observer {
    private final String COMMON_CHAT = "Общий чат";
    private final String FORMAT_DATE_MESSAGE = "dd-MM-yyyy HH:mm";
    private Observable connector;
    private DataPool dataPool;
    private ClientApplication clientApplication;
    private String selectedReceiver;
    @FXML
    private ListView<String> chatHistory;
    @FXML
    private Label loginLabel;
    @FXML
    private TextField messageField;
    @FXML
    private BorderPane rootComponent;
    @FXML
    private Button sendButton;
    @FXML
    private Button settingsButton;
    @FXML
    private ListView<String> users;

    @FXML
    void initialize() {
        setWrapListViewTextMessage();
        setUserListViewSelectActivity();
        addActionListeners();
    }

    private void setWrapListViewTextMessage() {
        chatHistory.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            public ListCell<String> call(ListView<String> list) {
                final ListCell cell = new ListCell() {
                    private Text text;
                    @Override
                    public void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            text = new Text(item.toString());
                            text.setWrappingWidth(chatHistory.getWidth());
                            setGraphic(text);
                        }
                    }
                };
                return cell;
            }
        });
    }

    private void setUserListViewSelectActivity() {
        users.setCellFactory(lv -> {
            MultipleSelectionModel<String> selectionModel = users.getSelectionModel();
            ListCell<String> cells = new ListCell<>();
            cells.textProperty().bind(cells.itemProperty());
            cells.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                users.requestFocus();
                if(!cells.isEmpty()) {
                    int index = cells.getIndex();
                    if(selectionModel.getSelectedIndices().contains(index)) {
                        selectionModel.clearSelection(index);
                        selectedReceiver = null;
                    } else {
                        selectionModel.select(index);
                        selectedReceiver = cells.getItem();
                    }
                    event.consume();
                }
            });
            return cells;
        });
    }

    private void addActionListeners() {
        sendButton.setOnAction((event) -> writeMessage());
        rootComponent.setOnKeyPressed((event) -> {
            if(event.getCode().equals(KeyCode.ENTER)) {
                writeMessage();
            }
        });
    }

    private void writeMessage() {
        if(isEmptyMessageField()) {
            return;
        }
        String text = messageField.getText();
        String sender = loginLabel.getText();
        String date = dateMessage();
        String receiver = selectedReceiver;
        Message message = new Message(sender, receiver, date, text);
        NetObject netObject = new NetObject(TypeNetObject.MESSAGE);
        netObject.putData(ParameterNetObject.MESSAGE, NetObject.getBytes(message));
        connector.send(netObject);
        clearMessageField();
    }

    private boolean isEmptyMessageField() {
        return messageField.getText().equals("");
    }

    private String dateMessage() {
        return new SimpleDateFormat(FORMAT_DATE_MESSAGE).format(new Date());
    }

    private void clearMessageField() {
        messageField.setText("");
    }

    @Override
    public void update(NetObject netObject) {
        switch (netObject.getType()) {
            case SUCCESS_AUTHENTICATION:
            case DISCONNECTION_NOTIFY:
                processClientConnectedOrDisconnect(netObject);
                break;
            case MESSAGE:
                processMessage(netObject);
                break;
        }
    }

    private void processClientConnectedOrDisconnect(NetObject netObject) {
        ClientPool clientPool = NetObject.getObject(netObject.getData(ParameterNetObject.CLIENT_POOL));
        dataPool.getClientPool().update(clientPool);
        updateConnectedClients(clientPool);
        processMessage(netObject);
    }

    private void updateConnectedClients(ClientPool clientPool) {
        if(!users.getItems().isEmpty()) {
            users.getItems().clear();
        }
        setConnectedClients(clientPool);
    }

    private void processMessage(NetObject netObject) {
        Message message = NetObject.getObject(netObject.getData(ParameterNetObject.MESSAGE));
        chatHistory.getItems().add(message.toString());
        chatHistory.scrollTo(chatHistory.getItems().size() - 1);
        dataPool.getChatHistory().add(message);
    }

    public void setDataFromDataPool() {
        ChatHistory chatHistory = dataPool.getChatHistory();
        ClientPool clientPool = dataPool.getClientPool();
        Client client = dataPool.getClient();
        setLogin(client);
        setConnectedClients(clientPool);
        setChatHistory(chatHistory);
    }

    private void setLogin(Client client) {
        loginLabel.setText(client.getLogin());
    }

    private void setConnectedClients(ClientPool clientPool) {
        users.getItems().add(COMMON_CHAT);
        for(Client c : clientPool.getClients()) {
            users.getItems().add(c.getLogin());
        }
    }

    private void setChatHistory(ChatHistory chatHistory) {
        for(Message m : chatHistory.getMessages()) {
            this.chatHistory.getItems().add(m.toString());
        }
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
