// module-info.java
// Goes in: src/main/java/  (same level as com/ folder, NOT inside it)
//
// This file tells Java what your module needs and exports.
// Since we removed all FXML, we don't need javafx.fxml anymore.

module com.example.demo2 {
    requires javafx.controls;  // Gives us: Stage, Scene, Button, Label, etc.

    exports com.example.demo2; // Makes our package usable
}
