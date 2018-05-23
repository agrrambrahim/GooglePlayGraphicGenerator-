package sample;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class QueryWindowController  implements Initializable {



    @FXML
    private TextField maxResultsTextField;

    @FXML
    private TextField queryTextField;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Map<String,String> pictures= new HashMap<String, String>();
        String query = queryTextField.getText();
        int maxResults=Integer.valueOf(maxResultsTextField.getText());
        FxCustomGoogleSearch google = new FxCustomGoogleSearch(query,maxResults);
        pictures = google.doSearch(1);
        Stage  mainWindow = (Stage)  ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/largeWindow.fxml"));
        Parent root = loader.load(); // FXMLLoader.load(getClass().getResource("/largeWindow.fxml"));
        mainWindow.setScene(new Scene(root));
        LargeWindowController controller =
            loader.<LargeWindowController>getController();
        controller.initData(pictures,query,maxResults);
        mainWindow.show();
    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}
