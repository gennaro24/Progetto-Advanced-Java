module it.unisa.diem.javaadv24.group01.mysimpleirtool.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports it.unisa.diem.javaadv24.group01.mysimpleirtool.gui;
    opens it.unisa.diem.javaadv24.group01.mysimpleirtool.gui to javafx.fxml;
}