module aq.koptev.ichatclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens aq.koptev.i to javafx.fxml;
    exports aq.koptev.i;
    exports aq.koptev.i.connect;
    opens aq.koptev.i.connect to javafx.fxml;
    exports aq.koptev.i.controllers;
    opens aq.koptev.i.controllers to javafx.fxml;
}