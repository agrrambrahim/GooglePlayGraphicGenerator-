package sample;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by bagrram on 23/05/2018.
 */
public class LargeWindowController  {
    private List<String> selectedImage = new ArrayList<String>();
    private Resolution choosedResolution;
    private int maxResults;
    private int nextQueryShouldStartFrom;
    private String googleSearchQuery;

    @FXML
    private TextField maxResultsTextField;
    @FXML
    private TextField queryTextField;
    @FXML
    private FlowPane imagesFlowPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TitledPane SelectedPicturesPane;
    @FXML
    private HBox SelectedPicturesHbox;
    @FXML
    private Label selectedPicturesNumber;
    @FXML
    private RadioButton radio1280;
    @FXML
    private RadioButton radio1024;
    @FXML
    private Button searchAgainGoogle;
    @FXML
    private Button resizeButton;
    @FXML
    private void handleSearchAgainButtonAction(ActionEvent event) throws IOException {
        Map<String,String> pictures= new HashMap<String, String>();
        String query = queryTextField.getText();
        int maxResults=Integer.valueOf(maxResultsTextField.getText());
        FxCustomGoogleSearch google = new FxCustomGoogleSearch(query,maxResults);
        pictures = google.doSearch(nextQueryShouldStartFrom);
        nextQueryShouldStartFrom+=maxResults;
        loadSearchResults(pictures);
     }

    @FXML
    private void handleResizeButtonAction(ActionEvent event) throws IOException {

        if(isResolutionSelected())
        if(SelectedPicturesHbox.getChildren().size()==selectedImage.size()){
            DirectoryChooser fileChooser = new DirectoryChooser();
            fileChooser.setTitle("Open Resource File");
            Stage mainWindow = (Stage)  ((Node)event.getSource()).getScene().getWindow();
            File file = fileChooser.showDialog(mainWindow);
            int isDoneWithSuccess = new JavaImageResizer().resizeUsingJavaAlgo(file,selectedImage,choosedResolution.getWidth(),choosedResolution.getHeight());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Result");
                alert.setHeaderText("Resizing is done successfully");
                alert.setContentText("number of unresized pictures : "+isDoneWithSuccess);


            //else{
              //  alert.setAlertType(Alert.AlertType.ERROR);
                //alert.setHeaderText("an Error was faced during Resizing");
            //}
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.NEXT);
            alert.getButtonTypes().add(ButtonType.FINISH);
            centerStage((Stage)alert.getDialogPane().getScene().getWindow(),100,100);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.FINISH){
                Platform.exit();
            } else {

            }



        }

        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("EROOR");
            alert.setHeaderText("is number of Selected images is greater than the original links filled");
            centerStage((Stage)alert.getDialogPane().getScene().getWindow(),100,100);
            alert.show();

        }

        }

    private boolean isResolutionSelected() {
        if(radio1024.isSelected()==false && radio1280.isSelected()==false){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("You didn't choose the resolution");
            centerStage((Stage)alert.getDialogPane().getScene().getWindow(),100,100);
            alert.show();
            return false;
        }
        else return true;
    }

    public void initData(Map<String, String> pictures, String query, final int nextQueryShouldStart) throws IOException {
        this.googleSearchQuery=query;
        this.nextQueryShouldStartFrom=nextQueryShouldStart;
        maxResultsTextField.setText(String.valueOf(nextQueryShouldStart));
        queryTextField.setText(query);
        maxResultsTextField.setOnInputMethodTextChanged(new EventHandler<InputMethodEvent>() {
            public void handle(InputMethodEvent event) {
                maxResults=Integer.valueOf(maxResultsTextField.getText());
            }
        });
        queryTextField.setOnInputMethodTextChanged(new EventHandler<InputMethodEvent>() {
            public void handle(InputMethodEvent event) {
                googleSearchQuery=queryTextField.getText();
            }
        });
        fillRadioButton();
        loadSearchResults(pictures);
        queryTextField.setOnInputMethodTextChanged(new EventHandler<InputMethodEvent>() {
            @Override
            public void handle(InputMethodEvent event) {
                nextQueryShouldStartFrom=0;
            }
        });
    }

    private void loadSearchResults(Map<String, String> pictures) {
        List<ImageView> imageViewsList = new ArrayList<ImageView>();

        for (Map.Entry<String,String> entry: pictures.entrySet()) {
            String imageThumbnailLink = entry.getKey();
            String originalImage = entry.getValue();
             final MyImageView thumbnail = new MyImageView(imageThumbnailLink,originalImage);
            thumbnail.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    if(SelectedPicturesHbox.getChildren().contains(thumbnail)){
                        SelectedPicturesHbox.getChildren().remove(thumbnail);
                        selectedImage.remove(thumbnail.getOriginalImageLink());
                        imagesFlowPane.getChildren().add(thumbnail);
                        if(!selectedPicturesNumber.getText().equals("0"))
                        selectedPicturesNumber.setText(String.valueOf(Integer.valueOf(selectedPicturesNumber.getText())-1));
                    }
                    else{
                        selectedImage.add(thumbnail.getOriginalImageLink());
                        SelectedPicturesHbox.getChildren().add(thumbnail);
                        selectedPicturesNumber.setText(String.valueOf(1+Integer.valueOf(selectedPicturesNumber.getText())));
                    }

                }
                });
            imageViewsList.add(thumbnail);
        }
        imagesFlowPane.getChildren().addAll(imageViewsList);
    }

    public void fillRadioButton(){
        final ToggleGroup group = new ToggleGroup();
        radio1024.setToggleGroup(group);
        radio1280.setToggleGroup(group);
        radio1024.setUserData(Resolution.FEATURED);
        radio1280.setUserData(Resolution.SCREENSHOT);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    choosedResolution = (Resolution) group.getSelectedToggle().getUserData();
                }
            }
        });

    }
    public static void centerStage(Stage stage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
    }
}
