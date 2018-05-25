package sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

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
        if(SelectedPicturesHbox.getChildren().size()==selectedImage.size())
        JavaImageResizer.resizeUsingJavaAlgo(selectedImage,choosedResolution.width,choosedResolution.height);
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("This is an example of JavaFX 8 Dialogs... ");
            alert.setHeaderText("Information Alert");
            alert.setContentText("ezrer");
            alert.show();

        }

        }

    public void initData(Map<String, String> pictures, String query, final int nextQueryShouldStartFrom) throws IOException {
        this.googleSearchQuery=query;
        this.nextQueryShouldStartFrom=nextQueryShouldStartFrom;
        maxResultsTextField.setText(String.valueOf(nextQueryShouldStartFrom));
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
}
