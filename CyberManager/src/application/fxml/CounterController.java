package application.fxml;

import java.net.URL;
import java.util.ResourceBundle;

import application.entities.Counter;
import application.entities.CounterOp;
import application.entities.ICounterOp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CounterController implements Initializable{

	@FXML
    private AnchorPane rootPane;

    @FXML
    private Button start;

    @FXML
    private Button pause;

    @FXML
    private Button setToZero;

    @FXML
    private TextField counterFlield;

    @FXML
    private TextField priceField;

    @FXML
    private Label stateLabel;

    @FXML
    private Label posteIdLabel;

    private ICounterOp counterOp;


	@FXML
    void onPause(ActionEvent event) throws InterruptedException {
		createCounterOp();
    	counterOp.pauseCounter();

    }

    @FXML
    void onSetToZero(ActionEvent event) {
    	createCounterOp();
    	counterOp.setCounterToZero();

    }

    @FXML
    void onStart(ActionEvent event) {
    	createCounterOp();
    	counterOp.playCounter();
    }

    private void createCounterOp() {
		if(counterOp == null){
			counterOp = new CounterOp(counterFlield, priceField, stateLabel);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Counter.setIdCounter(Counter.getIdCounter()+1);
		posteIdLabel.setText("Poste "+Counter.getIdCounter());

	}

}

