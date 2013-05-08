package traypass.tools;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.syntax.Interpreter;

public class ToolTimer {

	private Timer timer;
	private int seconds = 60;
	private String action;
	private String title;
	private String icon;
	private Interpreter interpreter;

	private static final Logger logger = LoggerFactory.getLogger(ToolClipboard.class);

	public ToolTimer(String title, String icon, String seconds, String action) {
		super();
		try {
			this.seconds = Integer.valueOf(seconds).intValue();
		} catch (Exception e) {
			logger.error("Error", e);
		}
		this.icon = icon;
		this.action = action;
		this.title = title;
	}

	public void start() {
		timer = new Timer();
		timer.schedule(new ActionTask(), seconds * 1000, seconds * 1000);
		logger.info("Task " + getTitle() + " started");
	}

	public void stop() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if (interpreter != null) {
			interpreter.setStop(true);
		}
		logger.info("Task " + getTitle() + " stopped");
	}

	public boolean isStop() {
		return timer == null;
	}

	public int getSeconds() {
		return seconds;
	}

	public String getTitle() {
		return title;
	}

	public String getIcon() {
		return icon;
	}

	class ActionTask extends TimerTask {
		public void run() {
			logger.debug("Task " + title + " executing " + action);
			interpreter = new Interpreter(action);
			interpreter.setInvisible(true);
			interpreter.start();
		}
	}

}
