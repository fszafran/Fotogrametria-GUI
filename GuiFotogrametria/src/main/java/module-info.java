module main.guifotogrametria {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens main.guifotogrametria to javafx.fxml;
    exports main.guifotogrametria;
}