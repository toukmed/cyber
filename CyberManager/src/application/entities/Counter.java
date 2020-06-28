package application.entities;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Counter {

	private int seconds;
	private int minutes;
	private int hours;
	private static int idCounter;

	private boolean started = false;
	private boolean paused = false;

	private static double tarif;
	private double price;

	private Timer timer;
	private TimerTask timerTask;

	{
		Path file;
		try {
			file = Paths.get("config.txt");
			List<String> list = Files.readAllLines(file, Charset.defaultCharset() );
			tarif = Presenter.readPrixTarif(list.get(1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public Timer getTimer() {
		return timer;
	}

	public TimerTask getTimerTask() {
		return timerTask;
	}


	public static double getTarif() {
		return tarif;
	}

	public static void setTarif(double tarif) {
		Counter.tarif = tarif;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Counter.idCounter = idCounter;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

}


