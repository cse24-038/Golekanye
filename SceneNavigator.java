import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneNavigator {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void goTo(String fxmlName) {
        try {
            Parent root = FXMLLoader.load(SceneNavigator.class.getResource(fxmlName));
            Scene scene = new Scene(root);
            try {
                scene.getStylesheets().add(SceneNavigator.class.getResource("theme.css").toExternalForm());
            } catch (Exception ignored) {}
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load FXML: " + fxmlName, e);
        }
    }
}
