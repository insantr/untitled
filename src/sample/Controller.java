package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, ChangeListener, SerialPortEventListener {

    private static SerialPort serialPort;

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
        t_ail_slider.setValue(0x94);
        t_ele_slider.setValue(0x44);
        t_rud_slider.setValue(0x21);
        //0 7F 7F 7F 94 44 21
    }


    public void click_reconnect(ActionEvent actionEvent) {
        if(serialPort != null && serialPort.isOpened()) {
            try {
                serialPort.closePort();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
        serialPort = new SerialPort("COM3");
        try {
            //Открываем порт
            serialPort.openPort();
            //Выставляем параметры
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            //Включаем аппаратное управление потоком
            //serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
            //        SerialPort.FLOWCONTROL_RTSCTS_OUT);
            //Устанавливаем ивент лисенер и маску
            serialPort.addEventListener(this::serialEvent, SerialPort.MASK_RXCHAR);
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    public void refresh(){
        String res = "";
        res += String.format("THR: 0x%02X;     ", ((int) thr_slider.getValue()));
        res += String.format("AIL: 0x%02X;     ", ((int) ail_slider.getValue()));
        res += String.format("ELE: 0x%02X;     ", ((int) ele_slider.getValue()));
        res += String.format("RUD: 0x%02X;     ", ((int) rud_slider.getValue()));
        res += String.format("T_AIL: 0x%02X;     ", ((int) t_ail_slider.getValue()));
        res += String.format("T_ELE: 0x%02X;     ", ((int) t_ele_slider.getValue()));
        res += String.format("T_RUD: 0x%02X;\t", ((int) t_rud_slider.getValue()));
        res += String.format("\r\n");

        sum_label.setText(res);

        if(serialPort == null || !serialPort.isOpened())
            return;

        byte[] array = new byte[8];
        array[0] = (byte)((int) thr_slider.getValue());
        array[1] = (byte)((int) ail_slider.getValue());
        array[2] = (byte)((int) ele_slider.getValue());
        array[3] = (byte)((int) rud_slider.getValue());
        array[4] = (byte)((int) t_ail_slider.getValue());
        array[5] = (byte)((int) t_ele_slider.getValue());
        array[6] = (byte)((int) t_rud_slider.getValue());
        array[7] = 0x00;
        try {
            serialPort.writeBytes(array);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        refresh();
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if(serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue() > 0){
            try {
                String data = serialPort.readString(serialPortEvent.getEventValue());
                //Platform.runLater(() -> txt2.appendText(data));
            }
            catch (SerialPortException ex) {
                System.out.println(ex);
            }
        }
    }
}
