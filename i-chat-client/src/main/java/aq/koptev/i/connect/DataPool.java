package aq.koptev.i.connect;

import aq.koptev.i.models.ChatHistory;
import aq.koptev.i.models.Client;
import aq.koptev.i.models.ClientPool;

public interface DataPool {
    void setChatHistory(ChatHistory chatHistory);
    void setClient(Client client);
    void setClientPool(ClientPool clientPool);
    Client getClient();
    ChatHistory getChatHistory();
    ClientPool getClientPool();
}
