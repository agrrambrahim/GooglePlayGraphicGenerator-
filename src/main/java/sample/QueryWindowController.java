package sample;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.javafx.tk.FileChooserType;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import javax.swing.*;

public class QueryWindowController  implements Initializable {

    public static final String JAVA="JAVA";
    public static final String PYTHON="PYTHON";
    public String languageChoosed;

    @FXML
    private TextField maxResultsTextField;

    @FXML
    private TextField queryTextField;
    @FXML
    private TextField iconPath;
    @FXML
    private TextField distinationDirectory;
    @FXML
    private RadioButton java;
    @FXML
    private RadioButton python;
    @FXML
    private TextArea outputMessage;



    private File iconPathFile;
    private File destinationPathFile;
    private String pythonOutPut;

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
        LargeWindowController.centerStage((Stage)mainWindow,mainWindow.getWidth(),mainWindow.getHeight());
        mainWindow.show();
    }
    @FXML
    private void handleIconResizeButtonAction(ActionEvent event) throws IOException {
        boolean isSuccess;
        if(resizingLanguageIsSelected()) {
            if (shouldGenerateWithJava())
                isSuccess = new IconResizer(destinationPathFile, iconPathFile).processResizing();
            else{
                RunPythonScript pythonScript =  new RunPythonScript();
                pythonOutPut= pythonScript.RunAndroidIconsGeneratorScripts(iconPathFile.getCanonicalPath(), destinationPathFile.getCanonicalPath());
                isSuccess=pythonScript.getStatus();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Result");
            if(!isSuccess) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setHeaderText("Error occured");
            }
            else
            alert.setHeaderText("Resizing is done successfully");
            alert.setContentText(pythonOutPut);
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.NEXT);
            alert.getButtonTypes().add(ButtonType.FINISH);
            LargeWindowController.centerStage((Stage) alert.getDialogPane().getScene().getWindow(), 300, 200);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.FINISH)
                Platform.exit();
            else {
                iconPath.clear();
                distinationDirectory.clear();
            }
        }


     }



    private boolean resizingLanguageIsSelected(){
        if((!java.isSelected())&&(!python.isSelected())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("You didn't choose the resolution");
            LargeWindowController.centerStage((Stage)alert.getDialogPane().getScene().getWindow(),100,100);
            alert.showAndWait();
            return  false;
        }
        return true;
    }
    private boolean shouldGenerateWithJava(){
        if (languageChoosed.equals(JAVA))
            return true;
            return false;

    }

    @FXML
    private void handleIconChoosePath(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Icon  :");
        Stage mainWindow = (Stage)  ((Node)event.getSource()).getScene().getWindow();
        //fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
        iconPathFile = fileChooser.showOpenDialog(mainWindow.getScene().getWindow());
        iconPath.setText(iconPathFile.getCanonicalPath());
        fillRadioButton();

    }
    @FXML
    private void handleDistinationFile(ActionEvent event) throws IOException {
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle("Save result on :");
        Stage mainWindow = (Stage)  ((Node)event.getSource()).getScene().getWindow();
        destinationPathFile = fileChooser.showDialog(mainWindow);
        distinationDirectory.setText(destinationPathFile.getCanonicalPath());



    }

    public void fillRadioButton() {
        final ToggleGroup group = new ToggleGroup();
        java.setToggleGroup(group);
        python.setToggleGroup(group);
        java.setUserData(JAVA);
        python.setUserData(PYTHON);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                                                       public void changed(ObservableValue<? extends Toggle> ov,
                                                                           Toggle old_toggle, Toggle new_toggle) {
                                                           if (group.getSelectedToggle() != null) {
                                                            languageChoosed = (String) group.getSelectedToggle().getUserData();
                                                           }
                                                       }
                                                   }
        );
    }

        public void initialize(URL location, ResourceBundle resources) {

    }

}
