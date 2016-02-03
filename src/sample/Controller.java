package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, ChangeListener {

    @FXML
    private TextArea txt2;
    @FXML
    private Label sum_label;

    @FXML
    private Slider thr_slider;
    @FXML
    private Slider ail_slider;
    @FXML
    private Slider ele_slider;
    @FXML
    private Slider rud_slider;
    @FXML
    private Slider t_ail_slider;
    @FXML
    private Slider t_ele_slider;
    @FXML
    private Slider t_rud_slider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        thr_slider.valueProperty().addListener(this::changed);
        ail_slider.valueProperty().addListener(this::changed);
        ele_slider.valueProperty().addListener(this::changed);
        rud_slider.valueProperty().addListener(this::changed);
        t_ail_slider.valueProperty().addListener(this::changed);
        t_ele_slider.valueProperty().addListener(this::changed);
        t_rud_slider.valueProperty().addListener(this::changed);

        thr_slider.setValue(1);
        ail_slider.setValue(255/2);
        ele_slider.setValue(255/2);
        rud_slider.setValue(255/2);
        t_ail_slider.setValue(255/2);
        t_ele_slider.setValue(255/2);
        t_rud_slider.setValue(255/2);
    }


    public void click_reconnect(ActionEvent actionEvent) {
        txt2.appendText("ddd");
    }

    public void refresh(){
        String res = "";
        res += String.format("THR: 0x%02X;\t", ((int) thr_slider.getValue()));
        res += String.format("AIL: 0x%02X;\t", ((int) ail_slider.getValue()));
        res += String.format("ELE: 0x%02X;\t", ((int) ele_slider.getValue()));
        res += String.format("RUD: 0x%02X;\t", ((int) rud_slider.getValue()));
        res += String.format("T_AIL: 0x%02X;\t", ((int) t_ail_slider.getValue()));
        res += String.format("T_ELE: 0x%02X;\t", ((int) t_ele_slider.getValue()));
        res += String.format("T_RUD: 0x%02X;\t", ((int) t_rud_slider.getValue()));
        res += String.format("\r\n");

        sum_label.setText(res);
        //txt2.appendText(res);
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
