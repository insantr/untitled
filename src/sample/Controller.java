package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, ChangeListener {

    @FXML
    private TextArea txt2;

    @FXML
    private Slider thr_slider;
    @FXML
    private Slider ail_slider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        thr_slider.valueProperty().addListener(this::changed);
        ail_slider.valueProperty().addListener(this::changed);

        thr_slider.setValue(255/2);
        ail_slider.setValue(255/2);
    }


    public void click_reconnect(ActionEvent actionEvent) {
        txt2.appendText("ddd");
    }

    public void refresh(){
        txt2.appendText("THR: " + (int)(thr_slider.getValue()) + "; AIL: " + (int)(ail_slider.getValue()) + "\r\n");
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//        Object obj = ((DoublePropertyBase) observable).getBean();
//        if(obj.equals(thr_slider)) {
//            thr_text.setText(((Number)newValue).intValue() + "");
//        } else if (obj.equals(thr_text)){
//            thr_slider.setValue( Integer.parseInt(thr_text.getText()));
//        }

        refresh();
    }
}
