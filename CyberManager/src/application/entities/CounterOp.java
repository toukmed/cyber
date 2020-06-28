package application.entities;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class CounterOp implements ICounterOp{

	private Timer countingTimer;
	private TimerTask countingTimerTask;
	private Counter counter;
	private TextField counterFlield;
	private TextField priceField;
	private Label stateLabel;


	public CounterOp(TextField counterFlield, TextField priceField, Label stateLabel) {
		super();
		this.counterFlield = counterFlield;
		this.priceField = priceField;
		this.stateLabel = stateLabel;

	}

	@Override
	public void playCounter() {

		try {
			if(counter == null){
				counter = new Counter();
				if(!counter.isStarted()){
					countTime();
					counter.setStarted(true);
				}
			}else{
				if(!counter.isStarted()){
					countTime();
					counter.setStarted(true);
				}
			}

        	stateLabel.setText("Occupied");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void pauseCounter() {
		if(counter == null ){
    		System.out.println("nothing to pause");
    	}else{
    		counter.setStarted(false);
    		countingTimer.cancel();
        	countingTimerTask.cancel();
    	}

	}

	@Override
	public void setCounterToZero() {
		if(counter == null ){
    		System.out.println("nothing to set to zero");
    	}else{
    		countingTimer.cancel();
    		countingTimerTask.cancel();
        	counter = null;
        	counterFlield.setText(Presenter.initialCounter);
        	priceField.setText(""+0.0);
    	}
    	stateLabel.setText("");

	}

	private synchronized void countTime() {

		countingTimer = new Timer();
		countingTimerTask = new TimerTask() {

			@Override
			public void run() {
				checkSecondsAndMinutes();
				afficherResult(priceField, counterFlield);
			}
		};
		countingTimer.scheduleAtFixedRate(countingTimerTask, 0, 1000);

	}

    public synchronized void afficherResult(TextField priceField, TextField counterFlield){

    		Platform.runLater(new Runnable() {

    			@Override
    			public void run() {
    				try {
    	    			counter.setPrice(calculatePrice());
    					priceField.setText(""+DecimalUtils.round(counter.getPrice(), 2));
    					counterFlield.setText(Presenter.formatCounter(counter));
    					counter.setSeconds(counter.getSeconds() + 1);
    				} catch (Exception e) {
    					System.out.println(e.getMessage());
    				}
    			}
    		});
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}


    }

    private double calculatePrice(){
    	double price = counter.getHours()*Counter.getTarif() + counter.getMinutes()*(Counter.getTarif()/60)
		+ counter.getSeconds()*(Counter.getTarif()/3600);
    	return price;
    }

    private void checkSecondsAndMinutes(){

    	if (counter.getSeconds() == 59) {
			counter.setSeconds(0);
			counter.setMinutes(counter.getMinutes() + 1);

			if (counter.getMinutes() == 59) {
				counter.setSeconds(0);
				counter.setMinutes(0);
				counter.setHours(counter.getHours() + 1);
			}
		}
    }


}


