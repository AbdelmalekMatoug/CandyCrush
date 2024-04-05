module be.kuleuven.candycrush {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens be.kuleuven.candycrush to javafx.fxml;
    exports be.kuleuven.candycrush;

    // Add this if be.kuleuven.candycrush.model is part of your module

}