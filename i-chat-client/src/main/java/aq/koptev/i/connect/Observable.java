package aq.koptev.i.connect;

import aq.koptev.i.controllers.Observer;
import aq.koptev.i.models.NetObject;

public interface Observable {
    void register(Observer observer);
    void remove(Observer observer);
    void send(NetObject netObject);
    DataPool getDataPool();
}
